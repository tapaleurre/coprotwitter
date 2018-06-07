package therealtwitter.Client;

import therealtwitter.Credential;
import therealtwitter.Tweet;
import therealtwitter.Utilisateur;

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
    @Override
    public Credential getCredentials() {
        Scanner reader = new Scanner(System.in);
        ConsoleEraser consoleEraser = new ConsoleEraser();
        System.out.println("Please enter your identifier");
        String id = reader.nextLine();
        System.out.println("Please enter your password");
        consoleEraser.start();
        String psw = reader.nextLine();
        consoleEraser.halt();
        System.out.print("\b");
        return new Credential(id, psw);
    }

    @Override
    public Tweet getTweet(Utilisateur user) throws Tweet.TweetTooLongException {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter a new tweet:");
        String text = reader.nextLine();
        Tweet myTweet = null;
        myTweet = new Tweet(text,user);
        return myTweet;
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
    public void displayUserInfo(Utilisateur user) {
        System.out.println("User: "+user.getUsername());
    }

    @Override
    public void displayErrorMessage(String s) {
        System.err.println("Error: "+s);
    }
}
