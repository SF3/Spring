package elections.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Flux;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Utils {

    public static Flux<String> fetchResults() {
        Path pathToFile = Paths.get("./data/electionResults.json");
        return Flux.using(
                () -> Files.lines(pathToFile),
                Flux::fromStream,
                Stream::close);
    }

    public static Result mapResult(String input) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(input, Result.class);
        } catch (Exception ex) {
            System.err.println("Cannot deserialize JSON");
            throw new IllegalStateException(ex);
        }
    }

    public static void printTitle(String title) {
        System.out.printf("\n----- %s -----\n", title);
    }

    public static void printTabbed(Object input) {
        System.out.printf("\t%s\n", input);
    }
}
