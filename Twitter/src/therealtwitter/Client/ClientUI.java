package therealtwitter.Client;

import therealtwitter.Credential;
import therealtwitter.Tweet;
import therealtwitter.Utilisateur;

import java.util.List;

public interface ClientUI {
    Credential getCredentials();
    Tweet getTweet(Utilisateur user) throws Tweet.TweetTooLongException;

    /**
     *
     * @return the action the user selected
     */
    UserAction promptMenu();
    void displayInfo(String s);
    void displayErrorMessage(String s);
    void displayTweets(List<Tweet> tweets);
    String promptUsername();
    String followUnfollowUser(String username,Boolean isFollowing);
}
