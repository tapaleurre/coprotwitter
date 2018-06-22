package therealtwitter.Client;

import simo.mi6.project.tier3.TwitterDBService;

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class ClientRMI  {


    private static TwitterDBService service;
    public static TwitterDBService getservice(){
        return service;
    }
    public ClientRMI() throws RemoteException, NotBoundException, MalformedURLException {
        try {
            this.service = (TwitterDBService) Naming.lookup("rmi://86.76.4.24:3200/TwitterDBService");
        } catch (ConnectException e) {
            System.out.println("Aucun service information trouvé");
            return;
        }
    }
    public static void main(String[] args) throws  Exception{
        ClientRMI clientRMI = new ClientRMI();
        List users = clientRMI.getservice().getAllUsers();
        for(int i=0; i<users.size(); i++)
            System.out.println(users.get(i));
    }
    }



