package org.mishadoff;

import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static org.mishadoff.HackTest.test;

/**
 * @author mishadoff
 */
public class Runner {

    public static final int LIMIT = 100_000_000;

    public static void main(String[] args) throws Exception {
        String message = "No Woman, No Cry!";
        Cypher c = new Cypher();
        Random r = new Random();
        String key = String.valueOf(r.nextInt(LIMIT));
        String cyphered = c.encrypt(message, key);

        System.out.printf("Generated KEY: [%s]\n", key);
        System.out.printf("Original Message: [%s]\n", message);
        System.out.printf("Encrypted Message: [%s]\n", cyphered);
        System.out.println("========================================");

        // single threaded simulation
        singleThreadSimulation(cyphered);


        // multi threaded simulation
        multiThreadedSimulation(cyphered, 1);
        multiThreadedSimulation(cyphered, 2);
        multiThreadedSimulation(cyphered, 4);
        multiThreadedSimulation(cyphered, 8);
        multiThreadedSimulation(cyphered, 16);
        multiThreadedSimulation(cyphered, 32);
        multiThreadedSimulation(cyphered, 64);

        // ForkJoin
        forkJoinSimulation(cyphered);

        // streams
        streamSimulation(cyphered);

        // parallel streams
        parallelStreamSimulation(cyphered);
    }

    private static void parallelStreamSimulation(String encryptedMessage) {
        System.out.printf("stream simulation...\n");
        long before = System.currentTimeMillis();
        final Cypher cypher = new Cypher();

        IntStream.range(0, LIMIT)
                 .parallel()
                 .mapToObj(i -> cypher.decrypt(encryptedMessage, String.valueOf(i)))
                 .filter(s -> test(s))
                 .forEach(e -> System.out.println(e));

        long after = System.currentTimeMillis();
        System.out.println("Elapsed time: " + (after - before) + "ms");
        System.out.println("========================================");
    }

    private static void streamSimulation(String encryptedMessage) {
        System.out.printf("Parallel Stream simulation...\n");
        long before = System.currentTimeMillis();
        final Cypher cypher = new Cypher();

        IntStream.range(0, LIMIT)
                .mapToObj(i -> cypher.decrypt(encryptedMessage, String.valueOf(i)))
                .filter(s -> test(s))
                .forEach(e -> System.out.println(e));

        long after = System.currentTimeMillis();
        System.out.println("Elapsed time: " + (after - before) + "ms");
        System.out.println("========================================");
    }

    private static void forkJoinSimulation(String cyphered) {
        System.out.println("Fork-Join simulation...");
        ForkJoinHacker hacker = new ForkJoinHacker(0, LIMIT, cyphered);
        ForkJoinPool pool = new ForkJoinPool();
        long before = System.currentTimeMillis();
        pool.invoke(hacker);
        long after = System.currentTimeMillis();
        System.out.println("Elapsed time: " + (after - before) + "ms");
        System.out.println("========================================");
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
            service.submit(new Hacker(cyphered, from, to));
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
        Hacker hacker = new Hacker(cyphered, 0, LIMIT);
        long before = System.currentTimeMillis();
        hacker.hack();
        long after = System.currentTimeMillis();
        System.out.println("Elapsed time: " + (after - before) + "ms");
        System.out.println("========================================");
    }




}
