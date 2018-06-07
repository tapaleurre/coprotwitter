package therealtwitter.Client;

import simo.mi6.project.tier3.TwitterDBService;

import java.rmi.ConnectException;
import java.rmi.Naming;
import java.util.List;

public class ClientRMI  {

    public static void main(String[] args) throws  Exception{


        //Registry registry = LocateRegistry.getRegistry("86.76.4.24",3200);
        System.out.println(1);
        TwitterDBService service;
        //service = (TwitterDBService) registry.lookup("TwitterDBService");


        try {
            service = (TwitterDBService) Naming.lookup("rmi://86.76.4.24:3200/TwitterDBService");
        } catch (ConnectException e) {
            System.out.println("Aucun service information trouvé");
            return;
        }
        System.out.println(2);
        List users = service.getAllUsers();
        for(int i=0; i<users.size(); i++)
            System.out.println(users.get(i));

    }
    }



