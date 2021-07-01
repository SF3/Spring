package simpsons.finish;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import static simpsons.common.Simpsons.*;

import simpsons.common.Simpsons;
import simpsons.common.Utils;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import static simpsons.common.Utils.*;

public class Program {
    public static final String HAIRCUT_REGEX = "^ *\\(#+\\) *$";

    public static void main(String[] args) {
        simpsonsFamily();
        margeHaircut();
        homerAtMoes();
        homerIsUpstairsV1();
        homerIsUpstairsV2();
        simpsonsClonedV1();
        simpsonsClonedV2();
        homerIsOnTheRightV1();
        homerIsOnTheRightV2();
        simpsonsStackedV1();
        simpsonsStackedV2();
        simpsonsStackedV3();
    }

    private static void simpsonsFamily() {
        Flux<String> flux = fetchSimpsons();
        printResults(flux, "The Simpsons Family");
    }

    private static void margeHaircut() {
        Flux<String> flux = fetchSimpsons();
        var result = flux.filter(line -> !line.matches(HAIRCUT_REGEX));
        printResults(result, "Marge After Haircut");
    }

    private static void homerAtMoes() {
        Flux<String> flux = fetchSimpsons();
        var result = flux.map(line -> line.substring(11));
        printResults(result, "Homer is at Moes");
    }

    private static void homerIsUpstairsV1() {
        Flux<String> flux = fetchSimpsons();
        flux.filter(line -> !line.matches(HAIRCUT_REGEX))
                .map(line -> line.substring(0, 12))
                .subscribe(Utils::printLine);
        var repeat = flux.map(line -> line.substring(11));
        printResults(repeat, "Homer is upstairs (first version)");
    }

    private static void homerIsUpstairsV2() {
        var simpsons = fetchSimpsons();
        var homer = simpsons
                .filter(line -> !line.matches(HAIRCUT_REGEX))
                .map(line -> line.substring(0, 12));
        var family = simpsons
                .map(line -> line.substring(11));
        var result = homer.concatWith(family);
        printResults(result, "Homer is upstairs (second version)");
    }

    private static void simpsonsClonedV1() {
        int lineWidth = 80;

        var result = fetchSimpsons()
                .map(line -> {
                    String begin = padLine(line, lineWidth);
                    return String.format("%s %s", begin, line);
                });

        printResults(result, "The Simpsons cloned V1");
    }

    private static void simpsonsClonedV2() {
        Flux<String> flux = fetchSimpsons();
        var result = flux
                .zipWith(fetchSimpsons())
                .map(tuple -> Tuples.of(padLine(tuple.getT1(), 80), tuple.getT2()))
                .map(tuple -> String.format("%s  %s", tuple.getT1(), tuple.getT2()));
        printResults(result, "The Simpsons cloned V2");
    }

    private static void homerIsOnTheRightV1() {
        int homerWidth = 14;
        var data = Tuples.of(new ArrayList<String>(), new ArrayList<String>());

        var result = fetchSimpsons()
                .collect(() -> data, (container, line) -> {
                    container.getT1().add(line.substring(homerWidth));
                    container.getT2().add(line.substring(0, homerWidth));
                })
                .subscribe(container -> {
                    var longestLine = container.getT1()
                            .stream()
                            .mapToInt(String::length)
                            .max()
                            .orElse(0);

                    Flux.range(0, container.getT1().size())
                            .subscribe(num -> {
                                var start = padLine(container.getT1().get(num), longestLine);
                                var end = container.getT2().get(num);
                                printLine(start + end);
                            });
                });

        printTitle("Homer is on the right V1");
    }

    //Simpler alternative if we know the max line width
    private static void homerIsOnTheRightV2() {
        int homerWidth = 14;
        int lineWidth = 80;

        var result = fetchSimpsons()
                .map(line -> padLine(line, lineWidth))
                .map(line -> {
                    String end = line.substring(0, homerWidth);
                    String begin = line.substring(homerWidth);
                    return begin + end;
                });

        printResults(result, "Homer is on the right V2");
    }

    private static void simpsonsStackedV1() {
        var tabulatedText = fetchSimpsons()
                .filter(line -> !line.matches(HAIRCUT_REGEX))
                .map(Program::tokenizeLineToTable)
                .collectList()
                .block();

        var result = Flux.fromArray(Simpsons.values())
                .map(name -> Tuples.of(name, tabulatedText))
                .flatMapIterable(tuple -> {
                    var simpson = tuple.getT1();
                    var tables = tuple.getT2();

                    return tables.stream()
                            .map(table -> table.get(simpson))
                            .collect(Collectors.toList());
                });

        printResults(result, "The Simpsons Stacked V1");
    }

    private static void simpsonsStackedV2() {
        var result = fetchSimpsons()
                .filter(line -> !line.matches(HAIRCUT_REGEX))
                .flatMap(Program::tokenizeLineToFlux)
                .groupBy(Tuple2::getT1)
                .flatMap(group -> group.reduce("", (a, b) -> a + "\n" + b.getT2()));

        printResults(result, "The Simpsons Stacked V2");
    }

    private static void simpsonsStackedV3() {
        int homerWidth = 14;
        int margeWidth = 16;
        int bartWidth = 14;
        int lisaWidth = 17;
        int maggieWidth = 16;

        var simpsons = fetchSimpsons()
                .filter(line -> !line.matches(HAIRCUT_REGEX));
        var widths = Flux.just(homerWidth, margeWidth, bartWidth, lisaWidth, maggieWidth);

        var result = sumWithValue(widths)
                .concatMap(tuple -> extractCharacter(simpsons, tuple));

        printResults(result, "The Simpsons Stacked V3");
    }

    private static Flux<Tuple2<Integer, Integer>> sumWithValue(Flux<Integer> flux) {
        return flux.scan(Tuples.of(0, 0), (oldTuple, number) ->
                Tuples.of(oldTuple.getT1() + oldTuple.getT2(), number)
        ).skip(1);
    }

    private static Flux<String> extractCharacter(Flux<String> simpsons, Tuple2<Integer, Integer> markers) {
        final int drop = markers.getT1();
        final int width = markers.getT2();
        return simpsons.map(line -> {
            String result = line;
            if (result.length() >= drop) {
                result = result.substring(drop);
            }
            if (result.length() >= width) {
                result = result.substring(0, width);
            }
            return result;
        }).filter(str -> !str.isBlank());
    }

    private static Flux<Tuple2<Integer, String>> tokenizeLineToFlux(String line) {
        return Flux.create(sink -> {
            sink.next(Tuples.of(Homer.ordinal(), safeSubstring(line, 0, 12)));
            sink.next(Tuples.of(Marge.ordinal(), safeSubstring(line, 12, 28)));
            sink.next(Tuples.of(Bart.ordinal(), safeSubstring(line, 28, 42)));
            sink.next(Tuples.of(Liza.ordinal(), safeSubstring(line, 42, 60)));
            sink.next(Tuples.of(Maggie.ordinal(), safeSubstring(line, 60)));
            sink.complete();
        });
    }

    private static Map<Simpsons, String> tokenizeLineToTable(String line) {
        return Map.of(
                Homer, safeSubstring(line, 0, 12),
                Marge, safeSubstring(line, 12, 28),
                Bart, safeSubstring(line, 28, 42),
                Liza, safeSubstring(line, 42, 60),
                Maggie, safeSubstring(line, 60)
        );
    }

    private static String safeSubstring(String line, int start, int end) {
        int length = line.length();
        if (start < length && end <= length) {
            return line.substring(start, end);
        }
        return "";
    }

    private static String safeSubstring(String line, int start) {
        if (start < line.length()) {
            return line.substring(start);
        }
        return "";
    }
}
