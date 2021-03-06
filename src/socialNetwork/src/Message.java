package socialNetwork.src;

import java.net.InetAddress;

public class Message {
	
	public static void postStatus(String status, InetAddress address){
		String data = "10" + status;
		Serveur.createSocket(address,data);
	}
		
	public static void postComment(String comment, InetAddress address){
		String data = "11" + comment;
		Serveur.createSocket(address,data);
	}	
	
	public static void friendsRequest(InetAddress address){
		try{
		String data = "20" + System.getProperty("user.name") + "_&§&_" + InetAddress.getLocalHost().getHostName();
		Serveur.createSocket(address, data);
		}catch(Exception e){}
	}

	public static void friendsAnswer(String host, String dataToSend, boolean friends){
		String data;
		if(friends)
			data = "21" + dataToSend;
		else
			data = "22" + dataToSend;
		try {
			Serveur.createSocket(InetAddress.getByName(host), data);
		} catch (Exception e) {}
	}

	public static void friendsListRequest(InetAddress address){
		String data = "23" + System.getProperty("user.name");
		Serveur.createSocket(address, data);
	}
	
	public static void friendsListGive(InetAddress address){
		String data = "24" + System.getProperty("user.name") /* + donnée */;
		Serveur.createSocket(address, data);
	}

	public static void friendsStatusRequest(InetAddress address){
		String data = "30" + System.getProperty("user.name");
		Serveur.createSocket(address, data);
	}

	public static void friendsStatusList(InetAddress address){
		String data = "31" + System.getProperty("user.name") /* + donnée */;
		Serveur.createSocket(address, data);
	}

	public static void friendsImage(InetAddress address){
		Serveur.createSocket(address, "40");
	}
}
