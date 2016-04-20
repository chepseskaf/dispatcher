package org.chepseskaf.goinfre;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.chepseskaf.goinfre.dispatcher.api.Consumer;
import org.chepseskaf.goinfre.dispatcher.api.Producer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */
public class AppTest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        assertTrue(true);
    }

    public void testBarrier() {
        final Drop drop = new Drop();
        final CountDownLatch barrier = new CountDownLatch(2);
        final Producer producer = new ProducerImpl(drop, barrier);
        final Consumer consumer = new ConsumerImpl(drop, barrier);

        final ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(producer);
        executorService.execute(consumer);
        try {
            barrier.await(20, TimeUnit.SECONDS);
            executorService.shutdown();
        } catch (InterruptedException e) {
            fail("PRODUCER and CONSUMER are still working");
        }
    }


}
