package therealtwitter;

public class Tweet {
    String text;
    Utilisateur author;

    public Tweet(String text, Utilisateur author) throws TweetTooLongException {
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

    public String getText(){
        return this.text;
    }
    public Utilisateur getAuthor(){
        return this.author;
    }

    public class TweetTooLongException extends Exception {
    }
}
