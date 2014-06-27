package org.mishadoff;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author mishadoff
 */
public class Runner {

    public static final int LIMIT = 1_0_000;
    public static final int SLEEP_TIME = 1;

    public static void main(String[] args) throws Exception {

        Alice alice = new Alice();
        System.out.println(alice.getOriginal());
        System.out.println(alice.getCyphered());

        String cyphered = alice.getCyphered();

        // simulation

        singleThreadSimulation(cyphered);

        multiThreadedSimulation(cyphered, 1);
        multiThreadedSimulation(cyphered, 2);
        multiThreadedSimulation(cyphered, 4);
        multiThreadedSimulation(cyphered, 8);
        multiThreadedSimulation(cyphered, 16);
        multiThreadedSimulation(cyphered, 32);
        multiThreadedSimulation(cyphered, 64);
        multiThreadedSimulation(cyphered, 128);
        multiThreadedSimulation(cyphered, 256);
        multiThreadedSimulation(cyphered, 512);
        multiThreadedSimulation(cyphered, 1024);
    }

    private static void multiThreadedSimulation(String cyphered, int threads) throws Exception {
        System.out.printf("Multi-threaded simulation [Threads=%d]...\n", threads);
        final int NUM_OF_THREADS = threads;
        ExecutorService service = Executors.newFixedThreadPool(NUM_OF_THREADS);
        int from = 0;
        int step = LIMIT / NUM_OF_THREADS;
        int to = step;
        long before = System.currentTimeMillis();
        for (int i = 0; i < NUM_OF_THREADS; i++) {
            service.submit(new MTHacker(cyphered, from, to));
            from += step;
            to += step;
        }
        service.shutdown();
        service.awaitTermination(5, TimeUnit.MINUTES);
        long after = System.currentTimeMillis();
        System.out.println("Elapsed time: " + (after - before) + "ms");
        System.out.println("========================================");
    }

    private static void singleThreadSimulation(String cyphered) throws Exception {
        System.out.println("Single-threaded simulation...");
        Hacker hacker = new Hacker();
        long before = System.currentTimeMillis();
        hacker.hack(cyphered, 0, LIMIT);
        long after = System.currentTimeMillis();
        System.out.println("Elapsed time: " + (after - before) + "ms");
        System.out.println("========================================");
    }
}
