package tk.myanimes.crypto;

public class Password {

    private final String password;

    private final String salt;

    public Password(String password, String salt) {
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
