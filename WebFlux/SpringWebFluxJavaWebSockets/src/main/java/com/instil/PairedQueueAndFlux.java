package com.instil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class PairedQueueAndFlux<T> {
    private BlockingQueue<T> incomingItems;
    private Flux<T> broadcaster;
    private ExecutorService executor;

    public PairedQueueAndFlux() {
        incomingItems = new LinkedBlockingQueue<>();
        executor = Executors.newSingleThreadExecutor();
        broadcaster = Flux.create(this::bindQueueToFlux).share();
    }

    public void enqueue(T item) {
        incomingItems.add(item);
    }

    public Flux<T> getBroadcaster() {
        return broadcaster;
    }

    private void bindQueueToFlux(FluxSink<T> sink) {
        var submit = executor.submit(() -> {
            while (true) {
                try {
                    sink.next(incomingItems.take());
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        sink.onCancel(() -> submit.cancel(true));
    }
}
