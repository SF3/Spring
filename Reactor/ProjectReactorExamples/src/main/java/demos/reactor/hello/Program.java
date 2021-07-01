package demos.reactor.hello;

import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class Program {
    private static String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing\n"
            + "elit, sed do eiusmod tempor incididunt ut labore et\n"
            + "dolore magna aliqua. Ut enim ad minim veniam, quis\n"
            + "nostrud exercitation ullamco laboris nisi ut aliquip\n"
            + "ex ea commodo consequat. Duis aute irure dolor in\n"
            + "reprehenderit in voluptate velit esse cillum dolore\n"
            + "eu fugiat nulla pariatur. Excepteur sint occaecat\n"
            + "cupidatat non proident, sunt in culpa qui officia\n"
            + "deserunt mollit anim id est laborum.";

    public static void main(String[] args) {
        Flux<String> flux = Flux.fromArray(loremIpsum.split(" "));
        flux.map(String::toLowerCase)
                .flatMap(word -> Flux.fromStream(word.chars().boxed()))
                .map(num -> (char) num.intValue())
                .filter(Character::isLetter)
                .collectMultimap(Function.identity())
                .map(Program::mapTable)
                .subscribe(Program::printTable);
    }

    private static Map<Character, Integer> mapTable(Map<Character, Collection<Character>> table) {
        return table.entrySet()
                .stream()
                .collect(toMap(Map.Entry::getKey, e -> e.getValue().size()));
    }

    private static void printTable(Map<Character, Integer> table) {
        System.out.println("The frequency count of letters in 'lorem ipsum' is:");
        table.forEach((key, value) -> {
            String msgWord = "instance" + (value > 1 ? "s" : "");
            System.out.printf("\t%s %s of %s\n", value, msgWord, key);
        });
    }
}
