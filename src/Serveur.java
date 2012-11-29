import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.*;
import java.util.Iterator;
import java.util.Set;
import java.io.*;

public class Serveur extends Main  {

	public static final int port = 5234;
	private String newStatus;
	public Interface ex;

	public Serveur(){}

	public void run(){
		ex = new Interface();
		ex.setVisible(true);
		listener();
	}

	public static void postStatus (String status){
		String data = "10" + status;
		try{
			for (int i = 0; i < nb; i++)
			{
				createSocket(address[i],data);
			}
		}catch (Exception e){}
	}

	public void listener (){

		try{
			ServerSocketChannel ssc = ServerSocketChannel.open();
			ServerSocket server = ssc.socket();
			Selector selector = Selector.open();

			server.bind(new InetSocketAddress(port));
			ssc.configureBlocking(false);
			ssc.register(selector, SelectionKey.OP_ACCEPT);

			while(true){
				selector.select();
				Set keys = selector.selectedKeys();
				Iterator it = keys.iterator();

				while (it.hasNext()){
					SelectionKey key = (SelectionKey) it.next();

					if (key.isAcceptable()){
						Socket listen = server.accept();
						SocketChannel sc = listen.getChannel();
						sc.configureBlocking(false);
						sc.register(selector, SelectionKey.OP_READ);
					}

					if(key.isReadable() && key.isValid()){
						ByteBuffer bb = ByteBuffer.allocate(512);
						SocketChannel st = (SocketChannel) key.channel();
						int byteRead = st.read(bb);
						bb.flip();
						if (byteRead == -1){
							key.cancel();
							st.close();

						}
						else {
							analyseData(bb);
							key.cancel();
						}
					}
				}
				keys.clear();
			}
		}catch (Exception e){}
	}


	public void analyseData(ByteBuffer bb){
		byte[] buff = new byte[bb.remaining()];
		bb.get(buff);
		String receiveData = new String(buff);

		String so1 = receiveData.substring(0, 1);
		int o1 = Integer.parseInt(so1);
		switch(o1){
		case 1 :
			newStatus = receiveData.substring(2);
			printStatus(newStatus);
			break;
		case 2 :
			String so2 = receiveData.substring(1, 2);
			int o2 = Integer.parseInt(so2);
			switch(o2){
			case 0 :
				//Demande d'amis
				break;
			case 1 :
				//Réponse amis + envoi liste d'amis + status
				break;
			case 2 :
				//Demande liste d'amis
				break;
			}
			break;
		case 3 :
			char o3 = receiveData.charAt(1);
			switch(o3){
			case 0 :
				//Demande status
				break;
			case 2 :
				//Envoi status
				break;
			}
			break;
		case 4 :
			//commentaire
			break;
		case 5 :
			//Image
			break;	
		}
	}

	private static void createSocket(InetAddress address, String data){
		try{
			Socket s = new Socket(address, port);
			OutputStream os = s.getOutputStream();
			PrintStream ps = new PrintStream(os, false, "utf-8");
			ps.println(data);
			ps.flush();
			ps.close();
			s.close();
		}catch (Exception e){}
	}
	
	private void friendsRequest(InetAddress address){
		String data = "20" + System.getProperty("user.name");
		createSocket(address, data);
	}
	
	private void friendsConfirm(InetAddress address){
		String data = "21" + System.getProperty("user.name");
		createSocket(address, data);
	}
	
	private void friendsListRequest(InetAddress address){
		createSocket(address, "22");
	}
	
	private void friendsStatusRequest(InetAddress address){
		createSocket(address, "30");
	}
	
	private void friendsStatusList(InetAddress address){
		createSocket(address, "31");
	}
	
	private void friendsCommentary(InetAddress address){
		createSocket(address, "40");
	}
	
	private void friendsImage(InetAddress address){
		createSocket(address, "50");
	}
	
	
	
	public void printStatus(String newStatus){
		ex.himStatus(newStatus);
	}
}
