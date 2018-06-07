package therealtwitter.Serveur;

import javax.rmi.CORBA.Util;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class TwitterDBServiceImpl extends UnicastRemoteObject implements TwitterDBService {

    private List<Utilisateur> utilisateurs;
    private List<String> nomUtilisateurs;
    private Utilisateur utilisateur;


    public TwitterDBServiceImpl() throws RemoteException {

        utilisateurs = new ArrayList<Utilisateur>();

    }



    @Override
    public Utilisateur connect(String username, String password) throws RemoteException {
        return (loggedUser(username, password));
    }

    @Override
    public boolean existsTarget(String targetName) throws RemoteException {
        return existsUser(targetName);
    }

    private Utilisateur findUser(String username){
        for (Utilisateur u : utilisateurs)
            if (u.getUsername().equals(username))
                return u;
        return null;
    }

    @Override
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
        if(this.nomUtilisateurs.)
        this.nomUtilisateurs.remove(username);
    }
    @Override
    public boolean isUserPasswordCorrect(String username, String password) throws RemoteException{
       if(utilisateur.getUsername() == username && utilisateur.getPassword() == password){
           return true;
       }
       else{
           return false;
       }



    }


}
