package therealtwitter.Serveur;

import simo.mi6.project.tier3.TwitterDBService;
import therealtwitter.Tweet;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;


@Path("/TwitterService/")
public class ServiceWS {
    private TwitterDBService serviceRMI;

    @GET
    @Path("getTweets/{user}")
    @Produces(MediaType.APPLICATION_XML)
    public JAXBElement<Tweet> getTweetsUtilisateur (@PathParam("user")String utilisateur){

        return null;
    }

    @GET
    @Path("getTweets/")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public JAXBElement<Tweet> getTweetsAbonnement (JAXBElement<Tweet> listUtilisateurs){

        return null;
    }

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


    public String getMyInfo() {
        return null;
    }

    @GET
    @Path("userInfo/{user}")
    @Consumes("text/plain")
    @Produces("text/plain")
    public String getUserInfo(String username) {
        return null;
    }

    @GET
    @Path("postTweet")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces("text/plain")
    public String postTweet(JAXBElement<Tweet> tweetText) {
        return null;
    }


    @GET
    @Path("twitterFeed")
    @Produces(MediaType.APPLICATION_XML)
    public String getFeed() {
        return null;
    }


    @GET
    @Path("connect/{user}&{password}")
    @Consumes("text/plain")
    @Produces("text/plain") //Private Key
    public String connect(@PathParam("password")String password, @PathParam("user")String username) {
        return null;
    }

    @GET
    @Path("disconnect/{user}")
    @Consumes("text/plain")
    @Produces("text/plain")
    public String disconnect(@PathParam("user")String utilisateur) {
        return null;
    }
}
