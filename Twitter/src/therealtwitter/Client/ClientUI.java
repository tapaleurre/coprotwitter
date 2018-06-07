package therealtwitter.Client;

import therealtwitter.Credential;
import therealtwitter.Tweet;
import therealtwitter.Utilisateur;

import java.util.List;

public interface ClientUI {
    Credential getCredentials();
    Tweet getTweet(Utilisateur user) throws Tweet.TweetTooLongException;
    void displayUserInfo(Utilisateur user);
    void displayErrorMessage(String s);
    void displayTweets(List<Tweet> tweets);
}
