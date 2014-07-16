package org.mishadoff;

import com.sun.deploy.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author mishadoff
 */
public class Cypher {

    public String encrypt(String message, String key) {
        StringBuilder sb = new StringBuilder();
        int expectedLength = message.length() * 2 - 1;
        int currentLength = 0;
        while (currentLength < expectedLength) {
            if (currentLength % 2 == 0) {
                sb.append(message.charAt(currentLength / 2));
            } else {
                sb.append(key.charAt((currentLength / 2) % key.length()));
            }
            currentLength++;
        }
        return sb.toString();
    }

    public String decrypt(String message, String key) {
        StringBuilder sb = new StringBuilder();
        int keyIndex = 0;
        for (int i = 0; i < message.length(); i++) {
            if (i % 2 == 0) {
                sb.append(message.charAt(i));
            } else {
                int keyDigit = Integer.parseInt("" + key.charAt(keyIndex));
                int messageDigit = Integer.parseInt("" + message.charAt(i));
                keyIndex++;
                if (keyIndex >= key.length()) {
                    keyIndex = 0;
                }
                int delta = Math.abs(keyDigit - messageDigit);
                if (delta != 0) {
                    sb.append(String.valueOf(delta).charAt(0));
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Cypher c = new Cypher();
        String key = "123";
        String enc = c.encrypt("No Woman No Cry!", key);
        System.out.println(enc);
        System.out.println(c.decrypt(enc, "133"));
    }
}