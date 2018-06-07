/*package therealtwitter;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class TwitterDBServiceImpl extends UnicastRemoteObject implements TwitterDBService {

    private List<Utilisateur> utilisateurs;
    private List<String> nomUtilisateurs;
    private Utilisateur utilisateur;


    public TwitterDBServiceImpl() throws RemoteException {

        // créé le stub du service d'information
        TwitterDBServiceImpl TwitterDBService = new TwitterDBServiceImpl();
        TwitterDBService stub = (TwitterDBService) UnicastRemoteObject.exportObject(TwitterDBService, 0);

        // récupere un registre
        Registry registry = LocateRegistry.getRegistry("86.76.4.24",3200);
        try {
            registry.bind("TwitterDBService", stub);
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
        utilisateurs = new ArrayList<Utilisateur>();

    }



    public Utilisateur connect(String username, String password) throws RemoteException {
        return (loggedUser(username, password));
    }


    public boolean existsTarget(String targetName) throws RemoteException {
        return existsUser(targetName);
    }

    private Utilisateur findUser(String username){
        for (Utilisateur u : utilisateurs)
            if (u.getUsername().equals(username))
                return u;
        return null;
    }


    public boolean existsUser(String username){
        return findUser(username)!=null;
    }


    private Utilisateur loggedUser(String username, String password) {
        for (Utilisateur u : utilisateurs)
            if (u.getUsername().equals(username) && u.getPassword().equals(password))
                return u;
        return null;
    }

    @Override
    public String toString(){
        return "Je suis un twitter de "+utilisateurs.size()+" users ";
    }



    @Override
    public List<String> getAllUsers() throws RemoteException{
        return nomUtilisateurs;
    }
    @Override
    public void createNewUser(String username, String password) throws RemoteException{
        if (existsUser(username)) {
            Utilisateur uti = new Utilisateur(username, password);
        }
    }
    @Override
    public void removeUser(String username) throws RemoteException{
        if(existsUser(username)) {

            this.nomUtilisateurs.remove(username);
        }
    }
    @Override
    public boolean isUserPasswordCorrect(String username, String password) throws RemoteException {
        if (utilisateur.getUsername() == username && utilisateur.getPassword() == password) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public List<String> getUsersFollowing(String username) throws RemoteException{


        Utilisateur u = Utilisateur.getUtilisateur(username);
        return null;

    }

     @Override
     public List<String> getUsersFollowedBy(String username) throws RemoteException{


    }

     @Override
     public void startFollowing(String followerUsername, String followedUsername) throws RemoteException{

    }

     @Override
     public void stopFollowing(String followerUsername, String followedUsername) throws RemoteException{

     }

     @Override
     public void createNewTweet(String username, String tweet) throws RemoteException {

     }

     @Override
     public List<String> getTweetsOfUser(String username) throws RemoteException{

     }

    }


}
*/