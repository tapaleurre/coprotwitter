package therealtwitter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Utilisateur implements Serializable {


    private String username;
    private String password;
    private List<Utilisateur> followedUsers;



    public Utilisateur(String username, String password) {
        this.username = username;
        this.password = password;
        this.followedUsers=new ArrayList<>();
        this.followedUsers.add(this);

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Permet de returner le nom de l'utilisateur
     * @return string : name
     */
    @Override
    public String toString() {
        String twittos = "Twittos : "+username+" \n";
        for (Utilisateur u : followedUsers)
            twittos+="\t@"+u.getUsername()+"\n";
        return twittos;
    }

    /*private boolean isFollowingUser(String user){
        System.out.println("\tisFollowingUser call answer : "+getUtilisateur(user)!=null);
        return getUtilisateur(user)!=null;
    }

    public void followUser(Utilisateur user){
        System.out.println("follow user :");
        if (!isFollowingUser(user.getUsername())){
            this.followedUsers.add(user);
            System.out.println("user added");
        }else System.out.println("user already added");
    }

    /*public static Utilisateur getUtilisateur(String user){
        System.out.println("SEARCHING : "+user);
        for (Utilisateur u : followedUsers)
            if (u.getUsername().equals(user)){
                System.out.println("\tgetUser call answer : "+u);
                return u;
            }
        System.out.println("\ton a pas trouvé "+user );
        return null;

    }*/

    public List<Utilisateur> getFollowedUsers() {
        return followedUsers;
    }
}
