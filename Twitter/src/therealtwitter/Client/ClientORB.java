package therealtwitter.Client;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import therealtwitter.CORBA.TwitterServiceApp.TwitterService;
import therealtwitter.CORBA.TwitterServiceApp.TwitterServiceHelper;
import therealtwitter.Credential;
import therealtwitter.Serveur.UserInfo;
import therealtwitter.Tweet;
import java.util.LinkedList;
import java.util.Properties;


public class ClientORB {

    private static ClientUI userInterface;
    public static final String SERVICE_NAME = "TwitterService";
    static TwitterService monService;
    private static UserInfo loggedUser = null;

    public static synchronized void main(String[] argv) {
        try{
            Properties props = new Properties();
            props.put("org.omg.CORBA.ORBInitialHost", "127.0.0.1");
            props.put("org.omg.CORBA.ORBInitialPort", "2000");
            // create and initialize the ORB
            ORB orb = ORB.init(argv, props);

            // get the root naming context
            org.omg.CORBA.Object objRef =
                    orb.resolve_initial_references("NameService");
            // Use NamingContextExt instead of NamingContext. This is
            // part of the Interoperable naming Service.
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // resolve the Object Reference in Naming
            monService = TwitterServiceHelper.narrow(ncRef.resolve_str(SERVICE_NAME));

            System.out.println("Obtained a handle on server object: " + monService);
            System.out.println(monService.ping());
            //monService.shutdown();

        } catch (Exception e) {
            System.out.println("ERROR : " + e) ;
            e.printStackTrace(System.out);
        }

        ClientORB.userInterface = new CommandLineUI();
        while(ClientORB.loggedUser == null){
            Credential credential = userInterface.getCredentials();
            double key = monService.connect(credential.getPassword(),credential.getIdentifier());
            clientConnect(credential, key);
            System.out.println("key: "+key);
            if(key<=1 && key>=0){
                ClientORB.loggedUser = new UserInfo(credential.getIdentifier(),key);
            }
        }
        System.out.println(monService.ping());

        while (true){
            UserAction action = userInterface.promptMenu();
            userInterface.displayInfo(action.name());
            System.out.println("Choix: "+action.name());
            switch (action){
                case CONNECT:
                    Credential credential = userInterface.getCredentials();
                    double key = monService.connect(credential.getPassword(),credential.getIdentifier());
                    clientConnect(credential, key);
                    break;
                case MY_FEED:
                    userInterface.displayInfo("My feed:\n");
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
                        userInterface.displayInfo(monService.postTweet(tweet.getText(), tweet.getAuthor(), loggedUser.getPrivateKey()));
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
                case FOLLOW_UNFOLLOW:
                    username = userInterface.promptUsername();
                    String result = userInterface.followUnfollowUser(username, monService.isFollowing(loggedUser.getUtilisateur(), username));
                    if(result.equals("follow")){
                        userInterface.displayInfo(monService.follow(loggedUser.getUtilisateur(), loggedUser.getPrivateKey(), username));
                    }else if(result.equals("unfollow")){
                        userInterface.displayInfo(monService.unfollow(loggedUser.getUtilisateur(), loggedUser.getPrivateKey(), username));
                    }else {
                        userInterface.displayInfo("Annulation");
                    }
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
        }else if(key == (double)-3){
            userInterface.displayErrorMessage("Cannot check user password");
        }else if(key == (double)-4){
            userInterface.displayErrorMessage("Cannot search in list");
        }else if(key == (double)-5){
            userInterface.displayErrorMessage("Cannot remove user from list");
        }else if(key == (double)-6){
            userInterface.displayErrorMessage("User could not be added");
        }else if(key == (double)-7){
            userInterface.displayErrorMessage("User could not be added to the local list");
    }
         else   {
            loggedUser = new UserInfo(credential.getIdentifier(),key);
            userInterface.displayInfo("Connexion success");
        }
    }
}
