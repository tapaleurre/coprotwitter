/*package therealtwitter.Serveur;

import simo.mi6.project.tier3.TwitterDBService;
import therealtwitter.Tweet;
import therealtwitter.Tweets;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;
import java.rmi.RemoteException;
import java.util.LinkedList;


@Path("TwitterService/")
public class ServiceWS {
    private static TwitterDBService serviceRMI;
    private static LinkedList<UserInfo> loggedList = new LinkedList<>();

    @GET
    @Path("helloWorld")
    @Produces("text/plain")
    public String sayHello() {
        return "\nHello world !!\n";
    }


    @GET
    @Path("ping")
    @Produces("text/plain")
    public String ping() {
        return "Pong\n";
    }


    @GET
    @Path("userInfo/{user}")
    @Consumes("text/plain")
    @Produces("text/plain")
    public String getUserInfo(String username) {

        //TODO: retourner les infos d'un utilisateur

        return null;
    }

    @GET
    @Path("postTweet")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces("text/plain")
    public String postTweet(JAXBElement<Tweet> tweetText) {

        //TODO: le post d'un tweet

        return null;
    }


    @GET
    @Path("twitterFeed/{user}")
    @Consumes("text/plain")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Tweets getFeed(@PathParam("user")String username) {
        Tweet res;
        Tweets list_tweets = new Tweets();
        try {
            try{
                for (String s : serviceRMI.getTweetsOfUser(username)) {
                    res = new Tweet(s, username);
                    list_tweets.liste.add(res);
                }
            }catch (Tweet.TweetTooLongException ex){
                ex.printStackTrace();
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return list_tweets;
    }

    @GET
    @Path("connect/{user}&{password}")
    @Consumes("text/plain")
    @Produces("text/plain") //Private Key
    public String connect(@PathParam("password")String password, @PathParam("user")String username) {
        try{
            if(serviceRMI.isUserPasswordCorrect(username,password)){
                Double key = Math.random();
                loggedList.add(new UserInfo(username, key));
                return String.valueOf(key);
            }if(!serviceRMI.getAllUsers().contains(username)){
                serviceRMI.createNewUser(username, password);
                double key = Math.random();
                loggedList.add(new UserInfo(username, key));
                return String.valueOf(key);
            }
            return "-1";
        } catch (RemoteException e) {
            return "-2";
        }
    }

    @GET
    @Path("disconnect/{user}")
    @Consumes("text/plain")
    @Produces("text/plain")
    public String disconnect(@PathParam("user")String utilisateur) {

        //TODO: déconnection du client "user"

        return null;
    }
}
*/