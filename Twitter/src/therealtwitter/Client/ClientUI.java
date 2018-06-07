package therealtwitter.Client;

import therealtwitter.Credential;
import therealtwitter.Tweet;
import therealtwitter.Utilisateur;

public interface ClientUI {
    Credential getCredentials();
    Tweet getTweet();
    void displayFeed(Utilisateur user);
    void displayUserInfo(Utilisateur user);
}
