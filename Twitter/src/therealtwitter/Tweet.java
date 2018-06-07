package therealtwitter;

public class Tweet {
    String text;
    Utilisateur author;

    Tweet(String text, Utilisateur author) throws TweetTooLongException {
        if(text.length()>280){
            throw new TweetTooLongException();
        } else{
            this.text = text;
            this.author = author;
        }
    }

    void EditTweet(String newText) throws TweetTooLongException {
        if(text.length()>280){
            throw new TweetTooLongException();
        } else{
            this.text = text;
        }
    }

    private class TweetTooLongException extends Exception {
    }
}
