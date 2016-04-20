package org.chepseskaf.goinfre;

import org.chepseskaf.goinfre.dispatcher.api.Producer;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by cheps.
 * 4/20/2016
 */
public class ProducerImpl implements Producer {
    private final Drop drop;
    private final CountDownLatch barrier;

    public ProducerImpl(Drop drop, CountDownLatch barrier) {
        this.drop = drop;
        this.barrier = barrier;
    }

    public void run() {
        String messages[] = {
                "Mares eat oats",
                "Does eat oats",
                "Little lambs eat ivy",
                "A kid will eat ivy too"
        };
        final Random random = new Random();

        for (String message : messages) {
            drop.put(message);
            System.out.format("MESSAGE SEND: %s%n", message);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e) {
                // nothing to do
            }
        }
        drop.put("DONE");
        System.out.format("PRODUCER STOPPED%n");
        barrier.countDown();
    }
}
