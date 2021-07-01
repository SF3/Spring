package demos.reactor.threading.internal;

import demos.reactor.threading.shared.Utils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static demos.reactor.threading.shared.Utils.*;
import static java.lang.String.format;

class Program {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        runUI(pool);
    }

    private static void runUI(ExecutorService pool) {
        printMenu();
        runLoop(pool);
        tidyUp(pool);
    }

    private static void printMenu() {
        System.out.println("--- Demos of Threading and Flux ---");
        System.out.println("\t1) Async and the Flatmap operation");
        System.out.println("\t2) Immediate Scheduler");
        System.out.println("\t3) Single Scheduler");
        System.out.println("\t4) Parallel Scheduler");
        System.out.println("\t5) Elastic Scheduler");
        System.out.println("\t6) Exit");
    }

    private static void runLoop(ExecutorService pool) {
        Scanner scanner = new Scanner(System.in);
        loop:
        while (scanner.hasNext()) {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    showAsyncFlatMap(pool);
                    break;
                case 2:
                    showImmediateScheduler();
                    break;
                case 3:
                    showSingleScheduler();
                    break;
                case 4:
                    showParallelScheduler();
                    break;
                case 5:
                    showElasticScheduler();
                    break;
                case 6:
                    break loop;
                default:
                    System.out.println("Unknown option");
            }
        }
    }

    private static void showAsyncFlatMap(ExecutorService pool) {
        System.out.println("--- Async with FlatMap ---");
        Scheduler scheduler = Schedulers.fromExecutor(pool);
        Flux.just("A", "B")
                .flatMap(str -> createValues(str, scheduler))
                .subscribe(s -> printTabbed(format("%s received on %s", s, threadId())));
    }

    private static Flux<String> createValues(String label, Scheduler scheduler) {
        return Flux.range(1, 10)
                .map(num -> label + num)
                .subscribeOn(scheduler);
    }

    private static void showImmediateScheduler() {
        System.out.println("--- Immediate Scheduler ---");
        Mono.fromCallable(Utils::randomValue)
                .repeat(10)
                .parallel()
                .runOn(Schedulers.immediate())
                .subscribe(s -> printTabbed(format("%s received by subscriber on %s", s, threadId())));
    }

    private static void showSingleScheduler() {
        System.out.println("--- Single Scheduler ---");
        Mono.fromCallable(Utils::randomValue)
                .repeat(10)
                .parallel()
                .runOn(Schedulers.single())
                .subscribe(s -> printTabbed(format("%s received by subscriber on %s", s, threadId())));
    }

    private static void showParallelScheduler() {
        System.out.println("--- Parallel Scheduler ---");
        Mono.fromCallable(Utils::randomValue)
                .repeat(10)
                .parallel()
                .runOn(Schedulers.parallel())
                .subscribe(s -> printTabbed(format("%s received by subscriber on %s", s, threadId())));
    }

    private static void showElasticScheduler() {
        System.out.println("--- Elastic Scheduler ---");
        Mono.fromCallable(Utils::randomValue)
                .repeat(10)
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .subscribe(s -> printTabbed(format("%s received by subscriber on %s", s, threadId())));
    }
}
