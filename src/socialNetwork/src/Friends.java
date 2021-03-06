package socialNetwork.src;

import java.util.ArrayList;
import java.util.StringTokenizer;

import socialNetwork.src.Status;

public class Friends{

	private String name;
	private String host;
	private boolean status;
	private ArrayList<Friends> ListOfFriend;
	
	public static ArrayList<Friends> friendList = new ArrayList<Friends>();

	public Friends(String name, String host, String status){
		this.name = name;
		this.host = host;
		if(status.compareTo("false") == 0)
			this.status = false;
		else
			this.status = true;
		this.ListOfFriend = new ArrayList<Friends>();
	}

	public Friends(){
		this.name = "";
		this.host = "";
		this.status = false;
		this.ListOfFriend = new ArrayList<Friends>();
	}

	public void setName(String name){
		this.name = name;
	}

	public void setHost(String host){
		this.host = host;
	}

	public String getName(){
		return this.name;
	}

	public String getHost(){
		return this.host;
	}

	public boolean getStatus(){	
		return this.status;
	}

	public void setToFriend(){
		this.status = true;
		//change dans xml
	}

	public void setToStranger(){
		this.status = false;
		//change dans xml
	}

	public String toString(){
		return "[" + this.name + "," + this.host +"]";
	}

	public static void AcceptFriend(String name){
		for(int i=0; i < friendList.size(); i++){
			if(friendList.get(i).getName().equals(name) && friendList.get(i).getStatus() == false){
				friendList.get(i).answerFriendRequest();
				break;
			}
		}
	}

	public static void analyseFriendsRequest(String request){
		Friends newFriend = new Friends();
		StringTokenizer stringT = new StringTokenizer(request, "_&§&_");		

		newFriend.setName(stringT.nextToken());
		newFriend.setHost(stringT.nextToken());
		newFriend.setToFriend();

		if(friendList.size() == 0)
			friendList.add(newFriend);
		else{
			for(int i=0; i < friendList.size(); i++){
				if(!friendList.get(i).getName().equals(newFriend.getName()))
					friendList.add(newFriend);
			}
		}

		//		try {
		//			XmlTreatment.addFriendXML(newFriends);
		//		} catch (Exception e) {}
	}

	public static void createFriendsList(){
		//friendList = XmlTreatment.getFriendsXML();
	}

	private static String contentFriendList(){
		String content = "";
		for(int i=0; i < friendList.size(); i ++)
			content = friendList.get(i).toString();
		return content;
	}

	private static String contentStatus(boolean type){
		String content = null;
		if(type)
			for(int i=0; i < Status.listStatus.size(); i ++)
				content = Status.listStatus.get(i).toString();
		else
			for(int i=0; i < Status.listStatus.size(); i ++){
				if(Status.listStatus.get(i).getType() == "public")
					content = Status.listStatus.get(i).toString();
			}
		return content;
	}

	private void answerFriendRequest(){
		this.setToFriend();
		//Change in xml
		String dataToSend;
		if(this.getStatus())
			dataToSend = System.getProperty("user.name") + contentFriendList() + contentStatus(true);
		else
			dataToSend = System.getProperty("user.name") + contentFriendList() + contentStatus(false);
		Message.friendsAnswer(this.getHost(), dataToSend, this.getStatus());
	}

	private static Friends findFriend(String name){
		for(int i =0; i < friendList.size();i++){
			if(friendList.get(i).getName().equals((String) name))
				return friendList.get(i);
		}
		return null;
	}

	public static void getFriendData(String dataSend){
		StringTokenizer st1 = new StringTokenizer(dataSend, "_&§&_");
		Friends friend = findFriend(st1.nextToken());
		
		StringTokenizer st2 = new StringTokenizer(st1.nextToken(), "_§§_");
		for(int j=0; j < st2.countTokens();j++){
			StringTokenizer st3 = new StringTokenizer(st2.nextToken(), "_o/");
			friend.ListOfFriend.add(new Friends(st3.nextToken(), st3.nextToken(), "false"));
		}
		
		StringTokenizer st3 = new StringTokenizer(st1.nextToken(),"{");
		for(int i =0; i < st3.countTokens();i++){
			//affiche Status friend.getName() et affiche commentaire associé.
		}
	}
}
