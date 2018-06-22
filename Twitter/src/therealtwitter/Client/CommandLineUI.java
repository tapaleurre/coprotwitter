package therealtwitter.Client;

import therealtwitter.Credential;
import therealtwitter.Serveur.UserInfo;
import therealtwitter.Tweet;

import java.util.List;
import java.util.Scanner;

class ConsoleEraser extends Thread {
    private boolean running = true;

    public void run() {
        while (running) {
            System.out.print("\b ");
            try {
                Thread.currentThread().sleep(1);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public synchronized void halt() {
        running = false;
    }
}

public class CommandLineUI implements ClientUI {
    private static Scanner reader = new Scanner(System.in);

    @Override
    public Credential getCredentials() {
        //ConsoleEraser consoleEraser = new ConsoleEraser();
        System.out.println("Please enter your identifier");
        String id = reader.nextLine();
        System.out.println("Please enter your password");
        //consoleEraser.start();
        String psw = reader.nextLine();
        //consoleEraser.halt();
        System.out.print("\b");
        return new Credential(id, psw);
    }

    @Override
    public Tweet getTweet(UserInfo user) throws Tweet.TweetTooLongException {
        System.out.println("Enter a new tweet:");
        String text = reader.nextLine();
        Tweet myTweet;
        myTweet = new Tweet(text,user.getUtilisateur());
        System.out.println(myTweet.getText());
        return myTweet;
    }

    @Override
    public UserAction promptMenu() {
        String selection = "";
        for (UserAction a : UserAction.values()){
            selection+=a.ordinal()+" - " + a.name() + "\n";
        }
        System.out.println(selection);
        String text = reader.nextLine();
        UserAction result;
        try {
            result = UserAction.values()[Integer.parseInt(text)];
        }catch (Exception e){
            return UserAction.ABOUT_ME;
        }
        return result;
    }

    @Override
    public void displayTweets(List<Tweet> tweets) {
        String text = "";
        for(Tweet i: tweets){
            text+="========== By "+i.getAuthor()+"\n";
            text+=i.getText()+"\n";
            text+="==========\n";
        }
        System.out.println(text);
    }

    @Override
    public void displayUsers(List<String> users) {
        System.out.println("==== Utilisateurs ====");
        for(String s:users){
            System.out.println(s);
        }
    }

    @Override
    public String promptUsername() {
        System.out.println("Merci d'entrer un nom d'utilisateur:");
        return reader.nextLine();
    }

    @Override
    public String followUnfollowUser(String username, Boolean isFollowing) {
        if(isFollowing){
            System.out.println("Vous suivez "+username);
        }else{
            System.out.println("Vous ne suivez pas "+username);
        }
        System.out.println("Pour suivre l'utilisateur tapez \"follow\"");
        System.out.println("Pour arrêter de suivre l'utilisateur tapez \"unfollow\"");
        System.out.println("Pour annuler appuyez sur entrée");
        String entry;
        do{
            entry = reader.nextLine();
        }while (!entry.equals("follow")&&!entry.equals("unfollow")&&!entry.equals(""));
        return entry;
    }


    @Override
    public void displayInfo(String info) {
        System.out.println(info);
    }

    @Override
    public void displayErrorMessage(String s) {
        System.err.println("Error: "+s);
    }
}
