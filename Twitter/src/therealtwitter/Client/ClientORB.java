package therealtwitter.Client;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import therealtwitter.CORBA.TwitterServiceApp.TwitterService;
import therealtwitter.CORBA.TwitterServiceApp.TwitterServiceHelper;
import therealtwitter.Credential;
import therealtwitter.Serveur.UserInfo;
import therealtwitter.Tweet;
import therealtwitter.Utilisateur;

import java.rmi.server.RMIClassLoader;
import java.util.LinkedList;
import java.util.Properties;


public class ClientORB {

    private static ClientUI userInterface;
    public static final String SERVICE_NAME = "TwitterService";
    private static UserInfo loggedUser = null;

    public static void main(String[] argv) throws InvalidName, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName, NotFound, Tweet.TweetTooLongException {
        System.out.println("Hey i'm a client");
        // Paramétrage pour la création de la couche ORB :
        // localisation de l'annuaire d'objet (service nommage)
        Properties props = new Properties();
        props.put("org.omg.CORBA.ORBInitialPort", "1337");
        props.put("org.omg.CORBA.ORBInitialHost", "127.0.0.1");
        // Création de la couche ORB
        // pour communiquer via un bus TwitterService
        ORB orb = ORB.init((String[]) null, props);
        // Recherche d'une référence sur un service de nommage
        org.omg.CORBA.Object serviceNommageRef;
        serviceNommageRef = orb.resolve_initial_references("NameService");
        // Instance du service de nommage à partir de sa référence
        // ("cast" façon TwitterService)
        NamingContextExt serviceNommage = NamingContextExtHelper.narrow(serviceNommageRef);
        // Recherche d'une référence sur le service météo
        org.omg.CORBA.Object monServiceRef;
        monServiceRef = serviceNommage.resolve_str(SERVICE_NAME);
        TwitterService monService = TwitterServiceHelper.narrow(monServiceRef);
        userInterface.displayInfo(monService.ping());

        ClientORB.userInterface = new CommandLineUI();
        while(ClientORB.loggedUser == null){
            Credential credential = userInterface.getCredentials();
            double key = monService.connect(credential.getPassword(),credential.getIdentifier());
            clientConnect(credential, key);
        }
        System.out.println(monService.ping());

        while (true){
            switch (userInterface.promptMenu()){
                case CONNECT:
                    Credential credential = userInterface.getCredentials();
                    double key = monService.connect(credential.getPassword(),credential.getIdentifier());
                    clientConnect(credential, key);
                    break;
                case MY_FEED:
                    String tweets = monService.getFeed(loggedUser.getUtilisateur());
                    LinkedList<Tweet> converted;
                    try{
                    converted = Tweet.toTweets(tweets,loggedUser.getUtilisateur());
                    userInterface.displayTweets(converted);
                    }catch (Tweet.TweetTooLongException e){
                        userInterface.displayErrorMessage("One of the fetched tweets is too long");
                    }
                    break;
                case ABOUT_ME:
                    String info = monService.getUserInfo(loggedUser.getUtilisateur());
                    userInterface.displayInfo(info);
                    break;
                case SEND_TWEET:
                    try {
                        Tweet tweet = userInterface.getTweet(loggedUser);
                        monService.postTweet(tweet.getText(), tweet.getAuthor(), loggedUser.getPrivateKey());
                    } catch (Tweet.TweetTooLongException e) {
                        e.printStackTrace();
                        userInterface.displayErrorMessage("Merci de réduire la taille de votre tweet");
                    }
                    break;
                case USER_FEED:
                    String user = userInterface.promptUsername();
                    String feed = monService.getFeed(user);
                    try {
                    LinkedList<Tweet> convertedFeed = Tweet.toTweets(feed, user);
                    userInterface.displayTweets(convertedFeed);
                    }catch (Tweet.TweetTooLongException e){
                        userInterface.displayErrorMessage("One of the fetched tweets is too long");
                    }
                    break;
                case ABOUT_USER:
                    String username = userInterface.promptUsername();
                    String userInfo = monService.getUserInfo(username);
                    userInterface.displayInfo(userInfo);
                    break;
                default:
                    System.err.println("Entrée non reconnue");
            }
        }
    }

    private static void clientConnect(Credential credential, double key) {
        if(key == (double) -1) {
            userInterface.displayErrorMessage("Username or password doesn't match");
        }else if(key == (double) -2){
            userInterface.displayErrorMessage("Network error");
        }else{
            loggedUser = new UserInfo(credential.getIdentifier(),key);
            userInterface.displayInfo("Connexion success");
        }
    }
}
