package puzzles;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        System.out.println("Running examples - hit return to exit");

        //TODO: Try to predict the behaviour of these functions,
        // then run them to see if your expectations are correct
        puzzle1();
        puzzle2();
        puzzle3();

        new Scanner(System.in).nextLine();
        System.out.println("Bye...");
    }

    public static void puzzle1() {
        System.out.println("\n--- Puzzle 1 ---");

        List<String> names = Arrays.asList("Jane", "Pete", "Mary", "Fred");
        Flux<String> flux = Flux.fromIterable(names);

        flux.map(String::toUpperCase);
        flux.subscribe(System.out::println);
    }

    public static void puzzle2() {
        System.out.println("\n--- Puzzle 2 ---");

        Flux<Integer> flux = fluxOfFourIntegers();

        flux.subscribe(
                item -> System.out.println("S1 received: " + item),
                error -> System.out.println("S1 error: " + error.getMessage()),
                () -> System.out.println("S1 complete"));

        flux.subscribe(
                item -> System.out.println("S2 received: " + item),
                error -> System.out.println("S2 error: " + error.getMessage()),
                () -> System.out.println("S2 complete"));
    }

    public static void puzzle3() {
        System.out.println("\n--- Puzzle 3 ---");

        Flux<String> flux = Flux.just("foo", "bar", "zed");
        flux.delayElements(Duration.ofMillis(500))
                .subscribe(item -> {
                    String msg = "Received %s on %d\n";
                    long threadId = Thread.currentThread().getId();

                    System.out.printf(msg, item, threadId);
                });
    }

    private static Flux<Integer> fluxOfFourIntegers() {
        return Flux.generate(
                () -> Tuples.of((int) (Math.random() * 100), 1),
                (state, sink) -> {
                    Tuple2<Integer, Integer> newState = Tuples.of(state.getT1() + 1, state.getT2() + 1);
                    if (state.getT2() < 5) {
                        sink.next(newState.getT1());
                    } else {
                        sink.complete();
                    }
                    return newState;
                });
    }
}
