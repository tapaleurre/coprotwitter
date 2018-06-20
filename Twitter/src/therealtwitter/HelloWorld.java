package therealtwitter;
import therealtwitter.Client.ClientRMI;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

@WebService()
public class HelloWorld {
  @WebMethod
  public String sayHelloWorldFrom(String from) {
    String result = "Hello, world, from " + from;
    System.out.println(result);
    return result;
  }
  public static void main(String[] argv) throws RemoteException, NotBoundException, MalformedURLException, Tweet.TweetTooLongException {
    /*Object implementor = new HelloWorld ();
    String address = "http://localhost:9000/HelloWorld";
    Endpoint.publish(address, implementor);
    System.out.println("PFF ! Screw gravity !");*/

    // création d'un utilisateur
   // Utilisateur uti = new Utilisateur("Jambon","jambon");

      List<Utilisateur> uti = Utilisateur.getUtilisateurs();
      for(Utilisateur u:uti){
          u.toString();
      }


      Utilisateur utilisateur = Utilisateur.getUtilisateur("Jambon");
      Utilisateur utilisateurquisuit = Utilisateur.getUtilisateur("Tristan2");
      System.out.println(utilisateurquisuit.isFollowingUser(utilisateur)); // doit renvoyer faux -> GOOD

      Tweet t = new Tweet("Le jambon c'est la vie !",utilisateur);

      //utilisateur.createTweet(t); //déja fait
      List<Tweet> tweets = utilisateur.getTweet();
      for(Tweet t1 : tweets){
          t1.toString();
      }


  }
}
