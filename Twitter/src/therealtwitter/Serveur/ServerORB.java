package therealtwitter.Serveur;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import therealtwitter.CORBA.TwitterServiceApp.TwitterService;
import therealtwitter.CORBA.TwitterServiceApp.TwitterServiceHelper;
import therealtwitter.CORBA.TwitterServiceApp.TwitterServicePOA;

import java.util.Properties;
// type "orbd -ORBInitialPort 1337 -ORBInitialHost 120.0.1"
class TwitterImpl extends TwitterServicePOA {
    private ORB orb;

    public void setORB(ORB orb_val) {
        orb = orb_val;
    }

    // implement sayHello() method
    public String sayHello() {
        return "\nHello world !!\n";
    }

    @Override
    public String ping() {
        return "Pong\n";
    }

    // implement shutdown() method
    public void shutdown() {
        orb.shutdown(false);
    }
}

public class ServerORB {

    public static final String SERVICE_NAME = "TwitterService";

    public static void main(String[] argv) throws InvalidName, AdapterInactive, ServantNotActive, WrongPolicy, org.omg.CosNaming.NamingContextPackage.InvalidName, CannotProceed, AlreadyBound, NotFound {
        System.out.println("Hey i'm a server !");
        // Paramétrage pour la création de la couche ORB :
        // localisation de l'annuaire d'objet (service nommage)
        Properties props = new Properties();
        props.put("org.omg.CORBA.ORBInitialHost", "127.0.0.1");
        props.put("org.omg.CORBA.ORBInitialPort", "1337");
        ORB orb = ORB.init((String[]) null, props);
        //rechercher rootPOA
        org.omg.CORBA.Object poaRef = orb.resolve_initial_references("RootPOA");
        //creer l'objet rootPOA
        POA rootPoa = POAHelper.narrow(poaRef);
        //Activation du service RootPOA
        rootPoa.the_POAManager().activate();
        //Recherche d'une référence sur un service de nommage
        org.omg.CORBA.Object serviceNommageRef;
        serviceNommageRef = orb.resolve_initial_references("NameService");
        // Instance du service de nommage à partir de sa référence
        // ("cast" façon TwitterService)
        NamingContextExt serviceNommage = NamingContextExtHelper.narrow(serviceNommageRef);
        TwitterImpl i = new TwitterImpl();
        org.omg.CORBA.Object monServiceRef;
        monServiceRef = rootPoa.servant_to_reference(i);
        TwitterService monService = TwitterServiceHelper.narrow(monServiceRef);
        NameComponent[] path = serviceNommage.to_name(SERVICE_NAME);
        serviceNommage.bind(path, monService);
        orb.run();
    }
}
