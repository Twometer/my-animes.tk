package tk.myanimes.crypto;

public class Credential {

    private final String password;

    private final String salt;

    public Credential(String password, String salt) {
        this.password = password;
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }
}
