package org.mishadoff;

/**
 * @author mishadoff
 */
public class Hacker {

    private boolean testCode(String code, String md5) {
        return md5.equals(Cypher.encode(code));
    }

    void hack(String cyphered, int from, int to) throws Exception {
        for (int code = from; code < to; code++) {
            if (testCode(String.valueOf(code), cyphered)) {
                System.out.println("Match: " + code);
            } else {
                if (Runner.SLEEP_TIME > 0) {
                    Thread.sleep(Runner.SLEEP_TIME);
                }
            }
        }
    }
}
