package therealtwitter.Client;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import therealtwitter.CORBA.TwitterServiceApp.TwitterService;
import therealtwitter.CORBA.TwitterServiceApp.TwitterServiceHelper;
import therealtwitter.Credential;
import therealtwitter.Tweet;
import therealtwitter.Utilisateur;

import java.util.Properties;


public class ClientORB {

    private static ClientUI userInterface;
    public static final String SERVICE_NAME = "TwitterService";
    private static Utilisateur loggedUser = null;

    public static void main(String[] argv) throws InvalidName, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName, NotFound {
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

        monService.ping();

        ClientORB.userInterface = new CommandLineUI();
        while(ClientORB.loggedUser == null){
            Credential credential = userInterface.getCredentials();
            //TODO: get user
        }
        System.out.println(monService.ping());
        //TODO try to actually connect
        while (true){
            switch (userInterface.promptMenu()){
                case CONNECT:
                    userInterface.getCredentials();
                    //TODO try to connect
                    break;
                case MY_FEED:
                    //TODO get all tweets
                    //userInterface.displayTweets(tweets);
                    break;
                case ABOUT_ME:
                    //TODO: get userinfo
                    userInterface.displayInfo("");
                    break;
                case SEND_TWEET:
                    try {
                        Tweet tweet = userInterface.getTweet(loggedUser);
                        //TODO: send the tweet
                    } catch (Tweet.TweetTooLongException e) {
                        e.printStackTrace();
                        userInterface.displayErrorMessage("Merci de réduire la taille de votre tweet");
                    }
                    break;
                case USER_FEED:
                    //TODO: get a user's tweets
                    //userInterface.displayTweets(tweets);
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
}
