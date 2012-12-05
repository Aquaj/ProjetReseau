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
		String data = "20" + System.getProperty("user.name") + ':' + InetAddress.getLocalHost().getHostName();
		Serveur.createSocket(address, data);
		}catch(Exception e){}
	}

	public static void friendsPositivAnswer(String host, String dataToSend){
		String data = "21" + dataToSend;
		try {
			Serveur.createSocket(InetAddress.getByName(host), data);
		} catch (Exception e) {}
	}
	
	public static void friendsNegativAnswer(InetAddress address){
		String data = "22" + System.getProperty("user.name") /* + donnée */;
		Serveur.createSocket(address, data);
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
