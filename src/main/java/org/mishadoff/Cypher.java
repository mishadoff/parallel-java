package org.mishadoff;

import com.sun.deploy.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author mishadoff
 */
public class Cypher {
    public static String encode(String input) {
        return MD5(input);
    }

    private static String MD5(String original) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] originalBytes = md.digest(original.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < originalBytes.length; ++i) {
                sb.append(Integer.toHexString((originalBytes[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) { }
        return null;
    }
}