package com.chat.realtime.web.util;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class SHA256Util {

    public String getEncrypt(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(str.getBytes());
        String hex = String.format("%064x", new BigInteger(1, md.digest()));
        return hex;
    }

}
