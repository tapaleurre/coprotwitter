/*package therealtwitter.Client;

import therealtwitter.Credential;
import therealtwitter.Serveur.UserInfo;
import therealtwitter.Tweet;
import therealtwitter.Tweets;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;


public class ClientWS {
    private static ClientUI userInterface;
    private static UserInfo loggedUser = null;
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


    private static String postTweet(String tweetText, String username, double key){
        return serviceWS.path("postTweet/"+tweetText+"&"+username+"&"+String.valueOf(key)).get(String.class);
    }


    private static Tweets getFeed(String username) throws Exception{

        String reponse;
        StringBuffer xmlStr;
        JAXBContext context;
        JAXBElement<Tweets> tweets;
        Unmarshaller unmarshaller;

        //Instanciation du convertiseur XML => Objet Java
        context = JAXBContext.newInstance(Tweets.class);
        unmarshaller = context.createUnmarshaller();

        reponse = serviceWS.path("twitterFeed/"+username).get(String.class);

		//Traitement de la reponse XML : transformation en une instance de la classe Tweets
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


    private static void clientConnect(Credential credential, double key) {
        if(key == (double) -1) {
            userInterface.displayErrorMessage("Nom d'utilisateur ou mot de passe incorrecte");
        }else if(key == (double) -2){
            userInterface.displayErrorMessage("Erreur réseau");
        }else{
            loggedUser = new UserInfo(credential.getIdentifier(),key);
            userInterface.displayInfo("Connexion réussite");
        }
    }

    public static void main(String[] argv) throws org.omg.CosNaming.NamingContextPackage.InvalidName {

        System.out.println("Hey i'm a client");

        serviceWS = Client.create().resource("http://localhost:9000/Twitter_war_exploded/TwitterService/");
        ClientWS.userInterface = new CommandLineUI();
        while(ClientWS.loggedUser == null){
            Credential credential = userInterface.getCredentials();
            double key = Double.valueOf(ClientWS.connect(credential.getPassword(),credential.getIdentifier())) ;
            clientConnect(credential, key);
            System.out.println("key: "+key);
            loggedUser = new UserInfo(credential.getIdentifier(),key);
        }
        System.out.println(ClientWS.ping());

        while (true){
            switch (userInterface.promptMenu()){
                case FOLLOW_UNFOLLOW:
                    
                    break;
                case CONNECT:
                    Credential credential = userInterface.getCredentials();
                    String response = ClientWS.connect(credential.getPassword(),credential.getIdentifier());
                    userInterface.displayInfo(response);
                    break;

                case MY_FEED:
                    try{
                        Tweets list_tweets = ClientWS.getFeed(loggedUser.getUtilisateur());
                        if(list_tweets.liste.isEmpty()){
                            userInterface.displayInfo("Aucun tweets trouvés");
                        }else{
                            userInterface.displayTweets(list_tweets.liste);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;

                case ABOUT_ME:
                    String about_me = ClientWS.getUserInfo(loggedUser.getUtilisateur());
                    userInterface.displayInfo("Mes infos :\n"+about_me);
                    break;
                case SEND_TWEET:
                    try {
                        Tweet tweet = userInterface.getTweet(loggedUser);
                        response = ClientWS.postTweet(tweet.getText(), tweet.getAuthor(), loggedUser.getPrivateKey());
                        userInterface.displayInfo(response);
                    } catch (Tweet.TweetTooLongException e) {
                        e.printStackTrace();
                        userInterface.displayErrorMessage("Merci de réduire la taille de votre tweet");
                    }
                    break;
                case USER_FEED:
                    try{
                        String username = userInterface.promptUsername();
                        Tweets list_tweets = ClientWS.getFeed(username);
                        if(list_tweets.liste.isEmpty()){
                            userInterface.displayInfo("Aucun tweets trouvés");
                        }else{
                            userInterface.displayTweets(list_tweets.liste);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
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

}*/
