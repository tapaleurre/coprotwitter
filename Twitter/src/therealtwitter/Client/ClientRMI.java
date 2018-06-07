package therealtwitter.Client;

import therealtwitter.TwitterDBService;
import therealtwitter.Utilisateur;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class ClientRMI {

    public static void main(String[] args) throws  Exception{


        Registry registry = LocateRegistry.getRegistry("86.76.4.24",3200);
        System.out.println(1);
        TwitterDBService service;
        service = (TwitterDBService) registry.lookup("TwitterDBService");
        System.out.println(2);

        List users = service.getAllUsers();
        for(int i=0; i<users.size(); i++)
            System.out.println(users.get(i));

    }
    }



