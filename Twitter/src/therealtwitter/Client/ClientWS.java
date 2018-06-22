package therealtwitter.Client;

import therealtwitter.Credential;
import therealtwitter.Tweet;
import therealtwitter.Tweets;
import therealtwitter.Utilisateur;
import java.util.List;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;


public class ClientWS {
    private static ClientUI userInterface;
    private static Utilisateur loggedUser = null;
    private static WebResource serviceWS = null;

    private static String sayHello(){
        return serviceWS.path("helloWorld").get(String.class);
    }

    private static String ping(){
        return serviceWS.path("ping").get(String.class);
    }


    private static String getUserInfo(String username){
        return serviceWS.path("userInfo/"+username).get(String.class);
    }


    private static String postTweet(Tweet tweet){
        return serviceWS.path("postTweet").put(String.class,tweet);
    }


    private static Tweets getFeed() throws Exception{

        String reponse;
        StringBuffer xmlStr;
        JAXBContext context;
        JAXBElement<Tweets> tweets;
        Unmarshaller unmarshaller;


        /*
		 ** Instanciation du convertiseur XML => Objet Java
		 */
        context = JAXBContext.newInstance(Tweets.class);
        unmarshaller = context.createUnmarshaller();

        reponse = serviceWS.get(String.class);

		/*
		 ** Traitement de la reponse XML : transformation en une instance de la classe Villes
		 */
        xmlStr = new StringBuffer(reponse);
        tweets = unmarshaller.unmarshal(new StreamSource(new StringReader(xmlStr.toString())), Tweets.class);

        return tweets.getValue();
    }


    private static String connect(String password, String username){
        return serviceWS.path("connect/"+username+"&"+password).get(String.class);
    }

    private static String disconnect(String utilisateur){
        return serviceWS.path("disconnect/"+utilisateur).get(String.class);
    }

    public static void main(String[] argv) throws org.omg.CosNaming.NamingContextPackage.InvalidName {

        System.out.println("Hey i'm a client");

        serviceWS = Client.create().resource("http://localhost:9000/Twitter_war_exploded/TwitterService/");
        ClientWS.userInterface = new CommandLineUI();
        while(ClientWS.loggedUser == null){
            Credential credential = userInterface.getCredentials();
            //TODO: get user
        }
        System.out.println(ClientWS.ping());
        //TODO try to actually connect
        while (true){
            switch (userInterface.promptMenu()){
                case CONNECT:
                    Credential credential = userInterface.getCredentials();
                    String response = ClientWS.connect(credential.getPassword(),credential.getIdentifier());
                    userInterface.displayInfo(response);
                    break;

                case MY_FEED:
                    //TODO get all tweets
                    userInterface.displayTweets(null);
                    break;

                case ABOUT_ME:
                    credential = userInterface.getCredentials();
                    String about_me = ClientWS.getUserInfo(credential.getIdentifier());
                    userInterface.displayInfo("About yourself :\n"+about_me);
                    break;
                case SEND_TWEET:
                    try {
                        Tweet tweet = userInterface.getTweet(null);
                        ClientWS.postTweet(null);
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
                    String userInfo = ClientWS.getUserInfo(username);
                    userInterface.displayInfo(userInfo);
                    break;
                default:
                    System.err.println("Entrée non reconnue");
            }
        }
    }
}
