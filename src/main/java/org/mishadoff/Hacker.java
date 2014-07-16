package org.mishadoff;

import java.util.concurrent.RecursiveAction;

import static org.mishadoff.HackTest.*;

/**
 * @author mishadoff
 */
public class Hacker implements Runnable {
    // algorithm
    private Cypher cypher = new Cypher();
    // stolen encrypted message
    private String encryptedMessage;
    // keyspace
    private int from;
    private int to;

    public Hacker(String encryptedMessage, int from, int to) {
        this.encryptedMessage = encryptedMessage;
        this.from = from;
        this.to = to;
    }

    void hack() {
        for (int code = from; code < to; code++) {
            String decryptedMessage =
                    cypher.decrypt(encryptedMessage, String.valueOf(code));
            if (test(decryptedMessage)) {
                System.out.println("Code found: " + code);
            }
        }
    }

    @Override
    public void run() {
        hack();
    }
}
