package therealtwitter;

public class Credential {
    public String getIdentifier() {
        return identifier;
    }

    public String getPassword() {
        return password;
    }

    String identifier;
    String password;

    public Credential(String id, String psw){
        this.identifier = id;
        this.password = psw;
    }


}
