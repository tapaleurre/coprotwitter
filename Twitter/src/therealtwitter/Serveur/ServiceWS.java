package therealtwitter.Serveur;

import simo.mi6.project.tier3.TwitterDBService;
import therealtwitter.Client.ClientRMI;
import therealtwitter.Tweet;
import therealtwitter.Tweets;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;


@Path("TwitterService/")
public class ServiceWS {
    private static TwitterDBService serviceRMI;
    private static LinkedList<UserInfo> loggedList = new LinkedList<>();

    public ServiceWS() throws RemoteException, NotBoundException, MalformedURLException {
        try {
            new ClientRMI();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ServiceWS.serviceRMI = ClientRMI.getservice();
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


    @GET
    @Path("userInfo/{user}")
    @Consumes("text/plain")
    @Produces("text/plain")
    public String getUserInfo(@PathParam("user")String username) {
        String message="";
        try {
            message+="User "+username+"\nFollowing:\n"+serviceRMI.getUsersFollowedBy(username)+"\nFollowed by:\n"+serviceRMI.getUsersFollowing(username);
        } catch (RemoteException e) {
            e.printStackTrace();
            message+="Could not get user info";
        }
        return message;
    }

    @GET
    @Path("postTweet/{tweetText}&{user}&{key}")
    @Consumes({"text/plain","text/plain", "text/plain"})
    @Produces("text/plain")
    public String postTweet(@PathParam("tweetText")String tweetText, @PathParam("user")String username, @PathParam("key")String key) {
        double privateKey = Double.valueOf(key);
        if(loggedList.contains(new UserInfo(username, privateKey))){
            if(tweetText.length()<=240){
                try {
                    serviceRMI.createNewTweet(username, tweetText);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                return "Tweet posté";
            }
            return "Tweet trop long";
        }
        return "Utilisateur inconnu";
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
                if(loggedList.contains(username)) {
                    loggedList.remove(username);
                }
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
        if(loggedList.contains(utilisateur)){
            loggedList.remove(utilisateur);
            return "\nDéconnexion réussite\n";
        }else{
            return "\nIl faut être connecté pour se déconnecter ;)\n";
        }
    }

    @GET
    @Path("follow/{firstUser}&{privateKey}&{secondUser}")
    @Consumes({"text/plain","text/plain", "text/plain"})
    @Produces("text/plain")
    public String follow(@PathParam("firstUser")String username, @PathParam("privateKey")String privateKey, @PathParam("secondUser")String secondUser){
        double key = Double.valueOf(privateKey);

        if(loggedList.contains(new UserInfo(username, key))){
            if(username!=secondUser){
                try {
                    serviceRMI.startFollowing(username, secondUser);
                    return "Abonnement réussit";
                } catch (RemoteException e) {
                    e.printStackTrace();
                    return "Erreur réseau";
                }
            }
            return "Vous ne pouvez pas vous suivre vous-même";

        }
        return "Veuillez vous connecter en premier";
    }

    @GET
    @Path("unfollow/{firstUser}&{privateKey}&{secondUser}")
    @Consumes({"text/plain","text/plain", "text/plain"})
    @Produces("text/plain")
    public String unfollow(@PathParam("firstUser")String username, @PathParam("privateKey")String privateKey, @PathParam("secondUser")String secondUser) {
        double key = Double.valueOf(privateKey);

        if(loggedList.contains(new UserInfo(username, key))){
            if(username!=secondUser){
                try {
                    serviceRMI.stopFollowing(username, secondUser);
                    return "Désabonnement réussit";
                } catch (RemoteException e) {
                    e.printStackTrace();
                    return "Erreur réseau";
                }
            }
            return "Vous ne pouvez pas arrêter de vous suivre vous-même";
        }
        return "Veuillez vous connecter en premier";
    }

    @GET
    @Path("isFollowing/{user}&{userToFollow}")
    @Consumes({"text/plain","text/plain"})
    @Produces("text/plain")
    public String isFollowing(@PathParam("user")String username,@PathParam("userToFollow")String userToFollow){
        try {
            boolean following = serviceRMI.getUsersFollowing(username).contains(userToFollow);
            String res;
            if(following){
                res = "oui";
            }else{
                res = "non";
            }
            return res;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return "erreur";
    }
}