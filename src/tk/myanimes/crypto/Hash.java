package tk.myanimes.crypto;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;

public class Hash {

    public static byte[] passwordHash(Password password) {
        try {
            var key = password.getPassword().toCharArray();
            var salt = password.getSalt().getBytes(StandardCharsets.UTF_8);

            var spec = new PBEKeySpec(key, salt, 65536, 256);
            var factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return factory.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean validateHash(Password password, byte[] reference) {
        var hash = passwordHash(password);
        if (hash.length != reference.length)
            return false;

        for (var i = 0; i < hash.length; i++) {
            if (hash[i] != reference[i])
                return false;
        }
        return true;
    }

}
