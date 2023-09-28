package app.jdev.mybatis.springauth.test.service;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Component
public class HashService {

    public String getHashedValue(String data, String salt) {
        int iterations = 15000;
        int keyLength = 256;

        KeySpec spec = new PBEKeySpec(data.toCharArray(), salt.getBytes(), iterations, keyLength);

        byte[] hash;

        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            hash = factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        return Base64.getEncoder().encodeToString(hash);
    }

}
