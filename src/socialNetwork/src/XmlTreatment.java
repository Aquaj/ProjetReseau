package socialNetwork.src;

import java.io.*;

import org.jdom2.*;
import org.jdom2.output.*;
import org.jdom2.input.*;
import org.jdom2.filter.*;

import socialNetwork.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class XmlTreatment {

	static Element racine = new Element("personnes");
	static org.jdom2.Document document = new Document(racine);

	static void lireFichierXML(String fichier) throws Exception{
		SAXBuilder sxb = new SAXBuilder(); 
		document = sxb.build(new File(fichier));
		racine = document.getRootElement();
	}

	public static void deleteFriendXML(String name){
		List listElement = racine.getChildren("name");
		Iterator i = listElement.iterator();
		while(i.hasNext()){

		}
	}

	public static void addFriendXML(Friends newFriend){
		/**/
	}

	/*Récupération liste d'amis*/
	
	/* La fonction ne marche pas, la liste retournée est vide*/
	public static ArrayList<Friends> getFriendsXML(){
		ArrayList<Friends> listFriends = new ArrayList<Friends>();
		try {
			lireFichierXML("friendList");
			racine.getChildren();
			List listTmp = racine.getChildren("friend");
			Iterator i = listTmp.iterator();
			while(i.hasNext()){
				Element courant = (Element)i.next();
				listFriends.add(new Friends(courant.getChild("name").getText(), courant.getChild("host").getText(), courant.getChild("friendStatus").toString()));
			}
		} catch (Exception e) {}
		System.out.println("a"+listFriends.size());
		return listFriends;
	}
}
