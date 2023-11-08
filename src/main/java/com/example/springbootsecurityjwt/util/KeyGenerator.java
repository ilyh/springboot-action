package com.example.springbootsecurityjwt.util;

import java.security.SecureRandom;
import java.util.Base64;

public class KeyGenerator {

    public static void main(String[] args) {
        int keyLength = 64; // 密钥长度，以字节为单位

        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[keyLength];
        secureRandom.nextBytes(keyBytes);

        String base64Key = Base64.getEncoder().encodeToString(keyBytes);

        System.out.println("Generated Key: " + base64Key);
    }
}

