package therealtwitter.Serveur;

public class UserInfo {
    private String utilisateur;
    private Double privateKey;

    public UserInfo(String user, double privateKey){
        this.utilisateur = user;
        this.privateKey = privateKey;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Double getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(Double privateKey) {
        this.privateKey = privateKey;
    }

    /**
     * Admire the most beautiful equals method ever
     * give a string to do equals on the username ad a userinfo to get it on the username and key
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o){
        if(o.getClass() == String.class){
            if(((String)o).equals(this.getUtilisateur())) return true;
        }else if(((UserInfo)o).getUtilisateur().equals(this.getUtilisateur())
                &&((UserInfo)o).getPrivateKey()==this.getPrivateKey()) return true;
        return false;
    }
}
