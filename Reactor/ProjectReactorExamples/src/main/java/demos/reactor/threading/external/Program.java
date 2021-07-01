package demos.reactor.threading.external;

import demos.reactor.threading.shared.Utils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static demos.reactor.threading.shared.Utils.*;
import static java.lang.String.format;

class Program {

    public static void main(String[] args) {
        ExecutorService pool1 = Executors.newFixedThreadPool(2);
        ExecutorService pool2 = Executors.newFixedThreadPool(2);

        runUI(pool1, pool2);
    }

    private static void runUI(ExecutorService... pools) {
        printMenu();
        runLoop(pools);
        tidyUp(pools);
    }

    private static void printMenu() {
        System.out.println("--- Demos of Threading and Flux ---");
        System.out.println("\t1) Synchronous");
        System.out.println("\t2) Async Publisher");
        System.out.println("\t3) Async Subscriber");
        System.out.println("\t4) Async Publisher and Subscriber");
        System.out.println("\t5) Exit");
    }

    private static void runLoop(ExecutorService... pools) {
        Scanner scanner = new Scanner(System.in);
        loop:
        while (scanner.hasNext()) {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    showSynchronousProcessing();
                    break;
                case 2:
                    showAsyncPublisher(pools[0]);
                    break;
                case 3:
                    showAsyncSubscriber(pools[0]);
                    break;
                case 4:
                    showAsyncPublisherAndSubscriber(pools[0], pools[1]);
                    break;
                case 5:
                    break loop;
                default:
                    System.out.println("Unknown option");
            }
        }
    }

    private static void showSynchronousProcessing() {
        System.out.println("--- Synchronous Flux ---");
        Flux<String> flux = Mono.fromCallable(Utils::randomValue)
                .repeat(5)
                .doOnNext(s -> printTabbed(format("%s generated on %s", s, threadId())));

        flux.subscribe(s -> printTabbed(format("%s received by S1 on %s", s, threadId())));
        flux.subscribe(s -> printTabbed(format("%s received by S2 on %s", s, threadId())));
    }

    private static void showAsyncPublisher(ExecutorService pool) {
        System.out.println("--- Flux With Async Publisher ---");
        Flux<String> flux = Mono.fromCallable(Utils::randomValue)
                .repeat(100)
                .doOnNext(s -> printTabbed(format("%s generated on %s", s, threadId())))
                .publishOn(Schedulers.fromExecutor(pool))
                .doOnNext(s -> printTabbed(format("%s moved to %s", s, threadId())));

        flux.subscribe(s -> printTabbed(format("%s received on %s", s, threadId())));
    }

    private static void showAsyncSubscriber(ExecutorService pool) {
        System.out.println("--- Flux With Async Subscriber ---");
        Flux<String> flux = Mono.fromCallable(Utils::randomValue)
                .repeat(10)
                .doOnNext(s -> printTabbed(format("%s generated on %s", s, threadId())))
                .subscribeOn(Schedulers.fromExecutor(pool))
                .doOnNext(s -> printTabbed(format("%s still on %s", s, threadId())));

        flux.subscribe(s -> printTabbed(format("%s received by S1 on %s", s, threadId())));
        flux.subscribe(s -> printTabbed(format("%s received by S2 on %s", s, threadId())));
    }

    private static void showAsyncPublisherAndSubscriber(ExecutorService pool1, ExecutorService pool2) {
        System.out.println("--- Flux With Async Publisher And Subscriber ---");
        Flux<String> flux = Mono.fromCallable(Utils::randomValue)
                .repeat(10)
                .doOnNext(s -> printTabbed(format("%s generated on %s", s, threadId())))
                .subscribeOn(Schedulers.fromExecutor(pool1))
                .publishOn(Schedulers.fromExecutor(pool2))
                .doOnNext(s -> printTabbed(format("%s now on %s", s, threadId())));

        flux.subscribe(s -> printTabbed(format("%s received by S1 on %s", s, threadId())));
        flux.subscribe(s -> printTabbed(format("%s received by S2 on %s", s, threadId())));
    }
}
