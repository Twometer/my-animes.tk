package tk.myanimes.crypto;

public class Credential {

    private final String password;

    private final String salt;

    public Credential(String salt, String password) {
        this.salt = salt;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }
}
