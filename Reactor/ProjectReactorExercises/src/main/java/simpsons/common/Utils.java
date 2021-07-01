package simpsons.common;

import reactor.core.publisher.Flux;
import simpsons.start.Program;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Utils {
    public static final String INPUT_FILE = "./data/simpsons.txt";

    public static Flux<String> fetchSimpsons() {
        try {
            List<String> stream = Files.readAllLines(Paths.get(INPUT_FILE));
            return Flux.fromIterable(stream);
        } catch (IOException ex) {
            System.err.println("Cannot load input data! " + ex.getMessage());
            return Flux.empty();
        }
    }

    public static void printResults(Flux<String> flux, String title) {
        flux.subscribe(
                Utils::printLine,
                Utils::errorHandler,
                () -> printTitle(title));
    }

    public static void printTitle(String title) {
        System.out.println(title);
        System.out.println("\n-----------------------------------\n");
    }

    public static void printLine(String line) {
        System.out.printf("\t%s\n", line);
    }

    public static void errorHandler(Throwable error) {
        System.err.println("Problem with Flux: " + error.getMessage());
    }

    public static String padLine(String line, int width) {
        int padding = width - line.length();
        if (padding > 0) {
            return line + " ".repeat(padding);
        }
        return line;
    }
}
