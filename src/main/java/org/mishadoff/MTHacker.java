package org.mishadoff;

/**
 * @author mishadoff
 */
public class MTHacker implements Runnable {
    private String cyphered;
    private int from;
    private int to;
    private Hacker hacker;

    public MTHacker(String cyphered, int from, int to) {
        this.cyphered = cyphered;
        this.from = from;
        this.to = to;
        hacker = new Hacker();
    }

    @Override
    public void run() {
        try {
            hacker.hack(cyphered, from, to);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
