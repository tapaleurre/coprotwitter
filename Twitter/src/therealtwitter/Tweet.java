package therealtwitter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;

@XmlRootElement(name = "Tweet")
public class Tweet {
    String text;
    String author;

    public Tweet(String text, String author) throws TweetTooLongException {
        if(text.length()>280){
            throw new TweetTooLongException();
        } else{
            this.text = text;
            this.author = author;
        }
    }

    public Tweet(String text, Utilisateur author) throws TweetTooLongException {
        if(text.length()>280){
            throw new TweetTooLongException();
        } else{
            this.text = text;
            this.author = author.getUsername();
        }
    }



    void EditTweet(String newText) throws TweetTooLongException {
        if(text.length()>280){
            throw new TweetTooLongException();
        } else{
            this.text = text;
        }
    }

    public String getText(){
        return this.text;
    }
    public String getAuthor(){
        return this.author;
    }

    public class TweetTooLongException extends Exception {
    }

    /**
     * Permet de returner le nom de l'utilisateur
     *
     * @return string : name
     */
    @Override
    public String toString() {
        String tweet = "Twittos : " + getAuthor() + " à twitté :" + getText() +" \n";

        System.out.println(tweet);
        return tweet;
    }

    public static LinkedList<Tweet> toTweets(String list, String author) throws TweetTooLongException {
        LinkedList<Tweet> tweets = new LinkedList<>();
        for(String s: list.split(";\n")){
            tweets.add(new Tweet(s, author));
        }
        return tweets;
    }

}
