package demos.reactor.creating;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class Program {
    public static void main(String[] args) throws Exception {
        System.out.println("--- Demos of Creating Flux ---");
        showEmptyFlux();
        showFluxViaJust();
        showFluxViaIterable();
        showFluxViaArray();
        showFluxViaRange();
        showFluxViaInterval();
        showFluxViaMerge();
        showFluxViaZip();
        showFluxViaGenerate();
        showFluxViaCreate();
        showFluxViaUsing();
    }

    private static void showEmptyFlux() {
        System.out.println("--- Empty Flux ---");
        Flux.empty()
                .subscribe(
                        str -> System.out.printf("\tReceived %s\n", str),
                        error -> System.out.printf("\tError occurred %s\n", error.getMessage()),
                        () -> System.out.println("\tAll done"));
    }

    private static void showFluxViaJust() {
        System.out.println("--- Flux built via 'just' ---");
        Flux.just("abc", "def", "ghi", "jkl", "mno")
                .subscribe(
                        str -> System.out.printf("\tReceived %s\n", str),
                        error -> System.out.printf("\tError occurred %s\n", error.getMessage()),
                        () -> System.out.println("\tAll done"));
    }

    private static void showFluxViaIterable() {
        List<String> data = Arrays.asList("abc", "def", "ghi", "jkl", "mno");
        System.out.println("--- Flux built via 'Iterable' ---");
        Flux.fromIterable(data)
                .subscribe(
                        str -> System.out.printf("\tReceived %s\n", str),
                        error -> System.out.printf("\tError occurred %s\n", error.getMessage()),
                        () -> System.out.println("\tAll done"));
    }

    private static void showFluxViaArray() {
        String[] data = {"abc", "def", "ghi", "jkl", "mno"};
        System.out.println("--- Flux built via array ---");
        Flux.fromArray(data)
                .subscribe(
                        str -> System.out.printf("\tReceived %s\n", str),
                        error -> System.out.printf("\tError occurred (%s)\n", error.getMessage()),
                        () -> System.out.println("\tAll done"));
    }

    private static void showFluxViaRange() {
        System.out.println("--- Flux built from range ---");
        Flux.range(1, 10)
                .subscribe(
                        item -> System.out.printf("\tReceived %s", item),
                        error -> System.out.printf("\tError occurred %s\n", error.getMessage()),
                        () -> System.out.println("\tAll done"));
    }

    private static void showFluxViaInterval() throws Exception {
        System.out.println("--- Flux built from interval ---");
        Disposable flux = Flux.interval(Duration.ofSeconds(1))
                .take(10)
                .subscribe(
                        item -> System.out.printf("\tReceived %s\n", item),
                        error -> System.out.printf("\tError occurred (%s)", error.getMessage()),
                        () -> System.out.println("\tAll done"));

        //Polling for the sake of the demo
        while (!flux.isDisposed()) {
            System.out.println("\tWaiting for interval...");
            Thread.sleep(500);
        }
    }

    private static void showFluxViaMerge() {
        List<String> data1 = Arrays.asList("abc", "def", "ghi", "jkl", "mno");
        String[] data2 = {"pqr", "stu", "vwx", "yza"};

        System.out.println("--- Flux built via merging ---");
        Flux.fromIterable(data1)
                .mergeWith(Flux.fromArray(data2))
                .subscribe(
                        str -> System.out.printf("\tReceived %s\n", str),
                        error -> System.out.printf("\tError occurred %s\n", error.getMessage()),
                        () -> System.out.println("\tAll done"));
    }

    private static void showFluxViaZip() {
        List<String> simpsons = Arrays.asList("Marge", "Homer", "Bart", "Liza", "Snowball");
        List<String> flintstones = Arrays.asList("Wilma", "Fred", "Barney", "Betty", "Dino");

        System.out.println("--- Flux built via zipping ---");
        Flux.fromIterable(simpsons)
                .zipWith(Flux.fromIterable(flintstones))
                .subscribe(
                        tuple2 -> System.out.printf("\tReceived %s and %s\n", tuple2.getT1(), tuple2.getT2()),
                        error -> System.out.printf("\tError occurred (%s)", error.getMessage()),
                        () -> System.out.println("\tAll done"));
    }

    private static void showFluxViaGenerate() {
        List<String> data = Arrays.asList("abc", "def", "ghi", "jkl", "mno");
        System.out.println("--- Flux built via 'generate' ---");
        Flux.generate(
                () -> 0,
                (state, sink) -> {
                    if (state < data.size()) {
                        sink.next(data.get(state));
                    } else {
                        sink.complete();
                    }
                    return state + 1;
                })
                .subscribe(
                        str -> System.out.printf("\tReceived %s\n", str),
                        error -> System.out.printf("\tError occurred %s\n", error.getMessage()),
                        () -> System.out.println("\tAll done"));
    }

    private static void showFluxViaCreate() {
        List<String> data = Arrays.asList("abc", "def", "ghi", "jkl", "mno");
        System.out.println("--- Flux built via 'create' ---");
        Flux.create(sink -> {
            for (String str : data) {
                sink.next(str);
            }
            sink.complete();
        }).subscribe(
                str -> System.out.printf("\tReceived %s\n", str),
                error -> System.out.printf("\tError occurred %s\n", error.getMessage()),
                () -> System.out.println("\tAll done"));
    }

    private static void showFluxViaUsing() {
        System.out.println("--- Flux built via 'using' ---");

        Path pathToFile = Paths.get("./build.gradle");
        Flux<String> flux = Flux.using(
                () -> Files.lines(pathToFile),
                Flux::fromStream,
                Stream::close);

        flux.map(s -> ">\t" + s).subscribe(System.out::println);
    }
}
