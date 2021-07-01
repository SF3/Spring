package demos.reactor.flattening;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        printMenu();
        runUI();
    }

    public static void showFlatMap() {
        System.out.println("----- Show flatMap -----");

        buildBaseData()
                .flatMap(Program::buildTestData)
                .subscribe(Program::printTabbed);
    }

    public static void showFlatMapSequential() {
        System.out.println("----- Show flatMap Sequential -----");

        buildBaseData()
                .flatMapSequential(Program::buildTestData)
                .subscribe(Program::printTabbed);
    }

    public static void showConcatMap() {
        System.out.println("----- Show concatMap -----");

        buildBaseData()
                .concatMap(Program::buildTestData)
                .subscribe(Program::printTabbed);
    }

    public static void showSwitchMap() {
        System.out.println("----- Show switchMap -----");

        buildBaseData()
                .switchMap(Program::buildTestData)
                .subscribe(Program::printTabbed);
    }

    public static Flux<String> buildBaseData() {
        return Flux.just("Alpha", "Bravo", "Charlie")
                .publishOn(Schedulers.parallel());
    }

    public static Flux<String> buildTestData(String prefix) {
        Flux<String> testData = Flux.generate(
                () -> -1,
                (state, sink) -> {
                    int nextState = state + 1;
                    if (nextState == 3) {
                        sink.complete();
                    } else {
                        sink.next(prefix + " " + nextState);
                    }
                    return nextState;
                });
        return testData.delayElements(Duration.ofMillis(500));
    }

    public static void printTabbed(Object thing) {
        System.out.println("\t" + thing);
    }

    public static void printMenu() {
        System.out.println("--- Demos of Flattening Data ---");
        System.out.println("1) flatMap");
        System.out.println("2) flatMapSequential");
        System.out.println("3) concatMap");
        System.out.println("4) switchMap");
        System.out.println("5) Exit");
    }

    public static void runUI() {
        Scanner scanner = new Scanner(System.in);
        loop:
        while (scanner.hasNext()) {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    showFlatMap();
                    break;
                case 2:
                    showFlatMapSequential();
                    break;
                case 3:
                    showConcatMap();
                    break;
                case 4:
                    showSwitchMap();
                    break;
                case 5:
                    break loop;
                default:
                    System.out.println("Unknown choice");
            }
        }
    }
}
