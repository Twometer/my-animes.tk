package tk.myanimes.crypto;

public class Credential {

    private final String salt;

    private final String password;

    public Credential(String salt, String password) {
        this.salt = salt;
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public String getPassword() {
        return password;
    }

}
