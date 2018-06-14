package therealtwitter.Client;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import therealtwitter.CORBA.TwitterServiceApp.TwitterService;
import therealtwitter.CORBA.TwitterServiceApp.TwitterServiceHelper;
import therealtwitter.Credential;

import java.util.Properties;


public class ClientORB {

    private static ClientUI userInterface;
    public static final String SERVICE_NAME = "TwitterService";

    public static void main(String[] argv) throws InvalidName, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName, NotFound {
        System.out.println("Hey i'm a client");
        // Paramétrage pour la création de la couche ORB :
        // localisation de l'annuaire d'objet (service nommage)
        Properties props = new Properties();
        props.put("org.omg.CORBA.ORBInitialPort", "1337");
        props.put("org.omg.CORBA.ORBInitialHost", "127.0.0.1");
        // Création de la couche ORB
        // pour communiquer via un bus TwitterService
        ORB orb = ORB.init((String[]) null, props);
        // Recherche d'une référence sur un service de nommage
        org.omg.CORBA.Object serviceNommageRef;
        serviceNommageRef = orb.resolve_initial_references("NameService");
        // Instance du service de nommage à partir de sa référence
        // ("cast" façon TwitterService)
        NamingContextExt serviceNommage = NamingContextExtHelper.narrow(serviceNommageRef);
        // Recherche d'une référence sur le service météo
        org.omg.CORBA.Object monServiceRef;
        monServiceRef = serviceNommage.resolve_str(SERVICE_NAME);
        TwitterService monService = TwitterServiceHelper.narrow(monServiceRef);

        ClientORB.userInterface = new CommandLineUI();
        Credential credential = userInterface.getCredentials();
        System.out.println(monService.ping());
        System.out.println("Identifier "+credential.getIdentifier());
    }
}
