package com.projecttango.experiments.javapointcloud;

import java.util.concurrent.ConcurrentLinkedQueue;

import io.flux.pipe.api.Client;

public class Submitter<T> implements Runnable {
    private final ConcurrentLinkedQueue<T> q_;
    private final Client c_ = new Client();

    public Submitter(ConcurrentLinkedQueue<T> queue) {
        this.q_ = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            while (!q_.isEmpty()) {
                c_.PutKey((String) q_.remove());
            }
        }
    }
}
