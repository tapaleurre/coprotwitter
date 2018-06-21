package therealtwitter.Serveur;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import simo.mi6.project.tier3.TwitterDBService;
import therealtwitter.CORBA.TwitterServiceApp.TwitterService;
import therealtwitter.CORBA.TwitterServiceApp.TwitterServiceHelper;
import therealtwitter.CORBA.TwitterServiceApp.TwitterServicePOA;
import therealtwitter.Client.ClientRMI;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Properties;
// type "orbd -ORBInitialPort 2000 -ORBInitialHost 120.0.1"
class TwitterImpl extends TwitterServicePOA {
    private static TwitterDBService RMIService;
    private ORB orb;
    private static LinkedList<UserInfo> loggedList = new LinkedList<>();


    public void setORB(ORB orb_val) {
        orb = orb_val;
    }

    public TwitterImpl(){
        TwitterImpl.RMIService = ClientRMI.getservice();
    }

    // implement sayHello() method
    public String sayHello() {
        return "\nHello world !!\n";
    }

    @Override
    public String ping() {
        System.out.println("Ping\n");
        return "Pong\n";
    }

    @Override
    public String getUserInfo(String username) {
        String message="";
        try {
            message+="User "+username+"\nFollowing:\n"+RMIService.getUsersFollowedBy(username)+"\nFollowed by:\n"+RMIService.getUsersFollowing(username);
        } catch (RemoteException e) {
            e.printStackTrace();
            message+="Could not get user info";
        }
        return message;
    }

    @Override
    public String postTweet(String tweetText, String username, double privateKey) {
        if(loggedList.contains(new UserInfo(username, privateKey))){
            if(tweetText.length()<=240){
                try {
                    RMIService.createNewTweet(username, tweetText);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                return "Tweet posté";
            }
            return "Tweet trop long";
        }
        return "Utilisateur inconnu";
    }

    @Override
    public String getFeed(String username) {
        try {
            String res ="";
            for(String s : RMIService.getTweetsOfUser(username)){
                res+=s+"\n;";
            }
            return res;

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return "Could not get feed";
    }

    /**
     *
     * @param password
     * @param username
     * @return the private key, or -1 if the password and usename doesn't match or -2 if a remote exception happened
     */
    @Override
    public double connect(String password, String username) {
        try {
            if(RMIService.isUserPasswordCorrect(username,password)){
                double key = Math.random();
                loggedList.add(new UserInfo(username, key));
                return key;
            }if(!RMIService.getAllUsers().contains(username)){
                RMIService.createNewUser(username, password);
                double key = Math.random();
                loggedList.add(new UserInfo(username, key));
                return key;
            }
            return -1;
        } catch (RemoteException e) {
            return -2;
        }
    }

    @Override
    public String follow(String username, double privateKey, String secondUser) {
        if(loggedList.contains(new UserInfo(username, privateKey))){
            if(username!=secondUser){
                try {
                    RMIService.startFollowing(username, secondUser);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    return "Network error";
                }
                return "You can't follow yourself";
            }
            return "Please log in before doing that";

        }
        return "Could not follow user";
    }

    // implement shutdown() method
    public void shutdown() {
        orb.shutdown(false);
    }
}

public class ServerORB {

    public static final String SERVICE_NAME = "TwitterService";

    public static void main(String[] argv) throws InvalidName, AdapterInactive, ServantNotActive, WrongPolicy, org.omg.CosNaming.NamingContextPackage.InvalidName, CannotProceed, AlreadyBound, NotFound {
        System.out.println("Hey i'm a server !");
        // Paramétrage pour la création de la couche ORB :
        // localisation de l'annuaire d'objet (service nommage)
        Properties props = new Properties();
        props.put("org.omg.CORBA.ORBInitialHost", "127.0.0.1");
        props.put("org.omg.CORBA.ORBInitialPort", "1337");
        ORB orb = ORB.init((String[]) null, props);
        //rechercher rootPOA
        org.omg.CORBA.Object poaRef = orb.resolve_initial_references("RootPOA");
        //creer l'objet rootPOA
        POA rootPoa = POAHelper.narrow(poaRef);
        //Activation du service RootPOA
        rootPoa.the_POAManager().activate();
        //Recherche d'une référence sur un service de nommage
        org.omg.CORBA.Object serviceNommageRef;
        serviceNommageRef = orb.resolve_initial_references("NameService");
        // Instance du service de nommage à partir de sa référence
        // ("cast" façon TwitterService)
        NamingContextExt serviceNommage = NamingContextExtHelper.narrow(serviceNommageRef);
        TwitterImpl i = new TwitterImpl();
        org.omg.CORBA.Object monServiceRef;
        monServiceRef = rootPoa.servant_to_reference(i);
        TwitterService monService = TwitterServiceHelper.narrow(monServiceRef);
        NameComponent[] path = serviceNommage.to_name(SERVICE_NAME);
        serviceNommage.rebind(path, monService);
        orb.run();
    }
}
