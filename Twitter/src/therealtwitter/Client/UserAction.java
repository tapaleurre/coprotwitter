package therealtwitter.Client;

public enum UserAction {
    CONNECT("Se connecter"),
    SEND_TWEET("Envoyer un tweet"),
    ABOUT_USER("À propos de l'utilisateur"),
    ABOUT_ME("Mon profil"),
    MY_FEED("Mon feed"),
    USER_FEED("Posts par utilisateur"),
    FOLLOW("Suivre"),
    UNFOLLOW("Ne plus suivre");

    private final String text;

    UserAction(String s) {
        this.text = s;
    }
    @Override
    public String toString(){
        return this.text;
    }
}
