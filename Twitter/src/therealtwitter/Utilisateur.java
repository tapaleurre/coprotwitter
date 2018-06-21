package therealtwitter;

import therealtwitter.Client.ClientRMI;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Utilisateur implements Serializable {


    private String username;
    private String password;
    private List<String> followedUsers;
    private List<String> followedBy;


    public Utilisateur(String username, String password) throws RemoteException, NotBoundException, MalformedURLException {

        this.username = username;
        this.password = password;
        new ClientRMI();
        ClientRMI.getservice().createNewUser(username,password);


    }
    public Utilisateur(String username,List<String> u,List<String> fb) {

        this.username = username;
        this.followedUsers = u;
        this.followedBy = fb;



    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Permet de returner le nom de l'utilisateur
     *
     * @return string : name
     */
    @Override
    public String toString() {
        String twittos = "Twittos : " + username + " \n";
        for (String u : followedUsers)
            twittos += "\t@" + u + "\n";
        for (String u : followedBy)
            twittos += "\t@2" + u + "\n";
        System.out.println(twittos);
        return twittos;
    }



    boolean isFollowingUser(Utilisateur user){

     return this.followedBy.contains(user.username);

    }

    public void createTweet(Tweet t) throws RemoteException, NotBoundException, MalformedURLException {
        new ClientRMI();
        if(this.equals(t.author)){

            ClientRMI.getservice().createNewTweet(t.getAuthor(),t.getText());
        }
    }

    public List<Tweet> getTweet() throws RemoteException, NotBoundException, MalformedURLException, Tweet.TweetTooLongException {
        new ClientRMI();
        List<String> tweets = ClientRMI.getservice().getTweetsOfUser(this.getUsername());
        List<Tweet> ts = new ArrayList<>();
        for (String tweet : tweets) {
            Tweet t = new Tweet(tweet,this);
            ts.add(t);
        }
        return ts;
    }



    public static List<Utilisateur> getUtilisateurs() throws RemoteException, NotBoundException, MalformedURLException {
        new ClientRMI();
        List<String> uti = ClientRMI.getservice().getAllUsers();
        List<Utilisateur> utilisateurs = new ArrayList<>();

        for (String username : uti) {
            List<String> suivi = ClientRMI.getservice().getUsersFollowing(username);
            List<String> suiviPar = ClientRMI.getservice().getUsersFollowedBy(username);
            Utilisateur utilisateur = new Utilisateur(username, suivi,suiviPar);
            utilisateurs.add(utilisateur);
        }
        return utilisateurs;
    }


        public static Utilisateur getUtilisateur(String name) throws RemoteException, NotBoundException, MalformedURLException {
            new ClientRMI();
            List<String> uti = ClientRMI.getservice().getAllUsers();
            for (String username : uti) {
                if (username.equals(name)) {
                    List<String> suivi = ClientRMI.getservice().getUsersFollowing(username);
                    List<String> suiviPar = ClientRMI.getservice().getUsersFollowedBy(username);
                    Utilisateur utilisateur = new Utilisateur(username, suivi,suiviPar);
                    return utilisateur;
                }

            }
            return null;
        }
        public boolean correctPassword(String identifiant,String password) throws RemoteException, NotBoundException, MalformedURLException {
        new ClientRMI();
        if(UserExist(identifiant)){
            boolean correct = ClientRMI.getservice().isUserPasswordCorrect(identifiant,password);
            return correct;
        }

        return false;
        }

        public boolean UserExist(String name) throws RemoteException, NotBoundException, MalformedURLException {
            new ClientRMI();
            List<String> uti = ClientRMI.getservice().getAllUsers();
            for (String username : uti) {
                if (username.equals(name)) {
                    return true;
                }
            }
            return false;
        }

        public void removeUser(Utilisateur user) throws RemoteException, NotBoundException, MalformedURLException {
            new ClientRMI();
            ClientRMI.getservice().removeUser(user.getUsername());

        }

        public void startFollowing(Utilisateur sabonne,Utilisateur user) throws RemoteException, NotBoundException, MalformedURLException {
            new ClientRMI();
            ClientRMI.getservice().startFollowing(sabonne.getUsername(),user.getUsername());
        }
    public void stopFollowing(Utilisateur desabonne,Utilisateur user) throws RemoteException, NotBoundException, MalformedURLException {
        new ClientRMI();
        ClientRMI.getservice().stopFollowing(desabonne.getUsername(),user.getUsername());
    }
    @Override
    public boolean equals(Object o) {
        if(((Utilisateur)o).getUsername().equals(this.getUsername())) return true;
        return false;
    }

    }

