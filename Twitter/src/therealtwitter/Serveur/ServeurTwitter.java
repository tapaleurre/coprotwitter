package therealtwitter.Serveur;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServeurTwitter {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {


        System.setProperty("java.rmi.server.hostname", "localhost");

        // créé le stub du service d'information
        TwitterDBServiceImpl TwitterDBService = new TwitterDBServiceImpl();
        TwitterDBService stub = (TwitterDBService) UnicastRemoteObject.exportObject(TwitterDBService, 0);

        // créé un registre
        Registry registry = LocateRegistry.createRegistry(2000);
        registry.bind("TwitterDBService", stub);

    }
}
