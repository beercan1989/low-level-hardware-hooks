package uk.co.baconi.keylogger.framework.impl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class AbstractImpl<T> {

    private final BlockingQueue<T> processQueue = new LinkedBlockingQueue<>();

    protected AbstractImpl() {
        super();

        new Thread(new Runnable() {
            @Override
            public void run() {
                processResults(processQueue);
            }
        }).start();
    }

    /**
     * Adds the value to process queue and blocks if the queue is full.
     *
     * @param value the value to add to the queue.
     * @throws InterruptedException
     */
    protected void addToProcessQueue(final T value) throws InterruptedException {
        processQueue.put(value);
    }

    protected abstract void processResults(BlockingQueue<T> processQueue);
}
