package org.chepseskaf.goinfre;

import org.chepseskaf.goinfre.dispatcher.api.Consumer;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by cheps.
 * 4/20/2016
 */
public class ConsumerImpl implements Consumer {
    private final Drop drop;
    private final CountDownLatch barrier;

    public ConsumerImpl(Drop drop, CountDownLatch barrier) {
        this.drop = drop;
        this.barrier = barrier;
    }

    public void run() {
        Random random = new Random();
        for (String message = drop.take();
             !message.equals("DONE");
             message = drop.take()) {
            System.out.format("MESSAGE RECEIVED: %s%n", message);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e) {
                // nothing to do
            }
        }
        System.out.format("CONSUMER STOPPED%n");
        barrier.countDown();
    }
}
