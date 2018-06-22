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
import simo.mi6.project.tier3.TwitterDBService;
import therealtwitter.CORBA.TwitterServiceApp.TwitterService;
import therealtwitter.CORBA.TwitterServiceApp.TwitterServiceHelper;
import therealtwitter.CORBA.TwitterServiceApp.TwitterServicePOA;
import therealtwitter.Client.ClientRMI;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ExportException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
// type "orbd -ORBInitialPort 2000 -ORBInitialHost 120.0.1"
class TwitterImpl extends TwitterServicePOA {
    private static TwitterDBService RMIService;
    private ORB orb;
    private static LinkedList<UserInfo> loggedList = new LinkedList<>();


    public void setORB(ORB orb_val) {
        orb = orb_val;
    }

    public TwitterImpl(){
        try {
            new ClientRMI();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        TwitterImpl.RMIService = ClientRMI.getservice();
    }


    @Override
    public String ping() {
        System.out.println("Ping\n");
        return "Pong\n";
    }

    @Override
    public String getUserInfo(String username) {
        String message="";
        try {
            message+="User "+username+"\nFollowing:\n"+RMIService.getUsersFollowedBy(username)+"\nFollowed by:\n"+RMIService.getUsersFollowing(username);
        } catch (RemoteException e) {
            e.printStackTrace();
            message+="Could not get user info";
        }
        return message;
    }

    @Override
    public String postTweet(String tweetText, String username, double privateKey) {

        for (UserInfo i : loggedList){
            System.out.println("@"+i.getUtilisateur()+" key: "+i.getPrivateKey()+"\n");
        }
        System.out.println(username+" key: "+privateKey+"\n");
        System.out.println(loggedList.get(0).equals(new UserInfo(username, privateKey)));
        if(loggedList.contains(new UserInfo(username, privateKey))){
            if(tweetText.length()<=240){
                try {
                    RMIService.createNewTweet(username, tweetText);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    return "Remote exception";
                }
                return "Tweet posté";
            }
            return "Tweet trop long";
        }
        return "Utilisateur inconnu";
    }

    @Override
    public String getFeed(String username) {
        try {
            String res ="";
            for(String s : RMIService.getTweetsOfUser(username)){
                res+=s+"\n;";
            }
            return res;

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return "Could not get feed";
    }

    /**
     *
     * @param password
     * @param username
     * @return the private key, or -1 if the password and usename doesn't match or -2 if a remote exception happened
     */
    @Override
    public double connect(String password, String username) {
        List l = new LinkedList();
        try {
            l= RMIService.getAllUsers();}catch (Exception e) {
            return -9;
        }try{
            l.contains(username);
        } catch (Exception e) {
            return -8;
        }
        try {
            if(RMIService.getAllUsers().contains(username)){
                try{
                    if(RMIService.isUserPasswordCorrect(username,password)){
                        double key = Math.random();
                        UserInfo validUser = new UserInfo(username, key);
                        try{
                            if(loggedList.contains(validUser.getUtilisateur())){
                                try {
                                    loggedList.remove(validUser.getUtilisateur());
                                }catch (Exception e){
                                    return (double)-5;//cannot remove user from list
                                }
                            }
                        }catch (Exception e){
                            return (double) -4;//cannot search in list
                        }
                        try{
                            loggedList.add(validUser);
                        }catch (Exception e){
                            return (double)-7;
                        }
                        return key;
                    } else {
                        return (double) -1;
                    }
                }catch (Exception e){
                    return (double)-3;//cannot check user psw
                }

            }else {
                double key = Math.random();
                UserInfo validUser = new UserInfo(username, key);
                try{
                    if(loggedList.contains(validUser)){
                        loggedList.remove(validUser);
                    }
                }catch (Exception e){
                    return (double)-4;//cannot search in list
                }
                try{
                    RMIService.createNewUser(username, password);
                } catch (Exception e){
                    return (double)-6; //user was not added
                }
                loggedList.add(new UserInfo(username, key));
                return key;
            }
        } catch (Exception e) {
            return (double) -2;
        }
    }

    @Override
    public String follow(String username, double privateKey, String secondUser) {
        if(loggedList.contains(new UserInfo(username, privateKey))){
            if(username!=secondUser){
                try {
                    RMIService.startFollowing(username, secondUser);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    return "Network error";
                }
                return "You can't follow yourself";
            }
            return "Please log in before doing that";
        }
        return "Could not follow user";
    }

    // implement shutdown() method
    public void shutdown() {
        orb.shutdown(false);
    }
}

public class ServerORB {

    public static final String SERVICE_NAME = "TwitterService";

    public static void main(String[] argv){
        try{
            Properties props = new Properties();
            props.put("org.omg.CORBA.ORBInitialHost", "127.0.0.1");
            props.put("org.omg.CORBA.ORBInitialPort", "2000");
            // create and initialize the ORB
            ORB orb = ORB.init(argv, props);

            // get reference to rootpoa & activate the POAManager
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // create servant and register it with the ORB
            TwitterImpl helloImpl = new TwitterImpl();
            helloImpl.setORB(orb);

            // get object reference from the servant
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(helloImpl);
            TwitterService href = TwitterServiceHelper.narrow(ref);

            // get the root naming context
            org.omg.CORBA.Object objRef =
                    orb.resolve_initial_references("NameService");
            // Use NamingContextExt which is part of the Interoperable
            // Naming Service (INS) specification.
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // bind the Object Reference in Naming
            NameComponent path[] = ncRef.to_name( SERVICE_NAME );
            ncRef.rebind(path, href);

            System.out.println("Server ready and waiting ...");

            // wait for invocations from clients
            orb.run();
        }

        catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }
    }
}
