package therealtwitter.Client;

public enum UserAction {
    CONNECT("Se connecter"),
    DISPLAY_USERS("Afficher les utilisateurs"),
    SEND_TWEET("Envoyer un tweet"),
    ABOUT_USER("À propos de l'utilisateur"),
    ABOUT_ME("Mon profil"),
    MY_FEED("Mon feed"),
    USER_FEED("Posts par utilisateur"),
    FOLLOW_UNFOLLOW("Suivre ou arrêter de suivre");

    private final String text;

    UserAction(String s) {
        this.text = s;
    }
    @Override
    public String toString(){
        return this.text;
    }
}
