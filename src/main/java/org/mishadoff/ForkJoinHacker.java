package org.mishadoff;

import java.util.concurrent.RecursiveAction;

/**
 * @author mishadoff
 */
public class ForkJoinHacker extends RecursiveAction {
    private int from;
    private int to;
    private String encryptedMessage;
    private Cypher cypher = new Cypher();

    private static final int THRESHOLD = 100000;

    public ForkJoinHacker(int from, int to, String cyphered) {
        this.from = from;
        this.to = to;
        this.encryptedMessage = cyphered;
    }

    private void computeDirectly() {
        for (int code = from; code < to; code++) {
            String decryptedMessage = cypher.decrypt(encryptedMessage, String.valueOf(code));
            if (HackTest.test(decryptedMessage)) {
                System.out.println("Code found: " + code);
            }
        }
    }

    @Override
    protected void compute() {
        if (to - from < THRESHOLD) {
            computeDirectly();
        } else {
            int split = (from + to) / 2;
            invokeAll(new ForkJoinHacker(from, split, encryptedMessage),
                      new ForkJoinHacker(split, to, encryptedMessage));
        }
    }
}
