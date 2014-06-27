package org.mishadoff;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @author mishadoff
 */
public class Alice {
    private String code;

    private static Random random = new Random();

    public Alice() {
        this.code = generateCode();
    }

    private String generateCode() {
        return String.valueOf(random.nextInt(Runner.LIMIT));
    }


    // NOT AVAILABLE
    public String getOriginal() {
        return code;
    }

    public String getCyphered() {
        return Cypher.encode(code);
    }
}