package com.crossover.techtrial.java.se.logic.security;

import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

/**
 * Encryption logic
 */
@Component
public class DataEncryptLogic extends StatelessServiceLogic<String, String> {

    private static final Logger logger = LoggerFactory.getLogger(DataEncryptLogic.class);

    private static SecretKeySpec secretKey;
    private static byte[] key;

    @Autowired
    private Environment environment;

    @Override
    public String invoke(String word) {

        ValidationUtil.validate(word, "Input is empty");
        return encrypt(word, environment.getRequiredProperty("encrypt.secret.key"));
    }

    public static String encrypt(String strToEncrypt, String secret) {
        try {
            init(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            logger.error("Error while encrypting: " + e.toString());
            throw new ServiceRuntimeException("Error occured while encrypting");
        }
    }

    public static void init(String myKey) {
        MessageDigest sha;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new ServiceRuntimeException(e.getMessage());
        }
    }
}
