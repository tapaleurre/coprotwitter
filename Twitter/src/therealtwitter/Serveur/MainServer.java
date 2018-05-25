package therealtwitter.Serveur;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;

import java.util.Properties;

public class MainServer {
    public static void main(String[] argv) throws InvalidName, AdapterInactive {
        System.out.println("Hey i'm a server !");
        // Paramétrage pour la création de la couche ORB :
        // localisation de l'annuaire d'objet (service nommage)
        Properties props = new Properties();
        props.put("org.omg.CORBA.ORBInitialPort", "1337");
        props.put("org.omg.CORBA.ORBInitialHost", "127.0.0.1");
        ORB orb = ORB.init((String[]) null, props);
        //rechercher rootPOA
        org.omg.CORBA.Object poaRef = orb.resolve_initial_references("RootPOA");
        //creer l'objet rootPOA
        POA rootpoa = POAHelper.narrow(poaRef);
        //Activation du service RootPOA
        rootpoa.the_POAManager().activate();
        //Recherche d'une référence sur un service de nommage
        org.omg.CORBA.Object serviceNommageRef;
        serviceNommageRef = orb.resolve_initial_references("NameService");
        // Instance du service de nommage à partir de sa référence
        // ("cast" façon CORBA)
        NamingContextExt serviceNommage = NamingContextExtHelper.narrow(serviceNommageRef);
        /** ETAPE 4 **/
        //TODO: Creation du service et son enveloppe par héritage
        //MonServiceImpl i = new MonServiceImpl();
        //TODO: Creation de la référence CORBA du service auprès du POA
        //org.omg.CORBA.Object monServiceRef
        //monServiceRef = rootPOA.servant_to_reference(MonServiceImpl);
        //TODO: Creation de l'objet CORBA du service
        //MonService monService = MonServiceHelper.narrow(monServiceRef);
        /** ETAPE 5 **/
        //TODO: Enregistrement du service (nom, objet)
        //NameComponent[] path = serviceNommage.to_name("MonService");
        //serviceNommage.bind(path, monService);
        //TODO:Démarrage de la couche ORB
        //orb.run()
    }
}
