package simpsons.start;

import reactor.core.publisher.Flux;

import static simpsons.common.Utils.fetchSimpsons;
import static simpsons.common.Utils.printResults;

public class Program {
    public static final String HAIRCUT_REGEX = "^ *\\(#+\\) *$";

    public static void main(String[] args) {
        simpsonsFamily();
        margeHaircut();
        homerAtMoes();
        homerIsUpstairs();
        simpsonsCloned();
        homerIsOnRHS();
        simpsonsStacked();
    }

    private static void simpsonsFamily() {
        Flux<String> flux = fetchSimpsons();
        printResults(flux, "The Simpsons Family");
    }

    private static void margeHaircut() {
        //Remove any lines that only contain Marge's hair
    }

    private static void homerAtMoes() {
        //Remove Homer from the picture
    }

    private static void homerIsUpstairs() {
        //Put Homer above the other characters
        //Hint - you can subscribe multiple times
    }

    private static void simpsonsCloned() {
        //Print the Simpsons beside one another
        //Hint - you can assume no line is longer than 80 chars
        //Hint - you might want to use 'zipWith'
    }

    private static void homerIsOnRHS() {
        //Move Homer to the right hand side of the picture
        //NB you cannot assume the maximum line length
        //Hint - you might want to use 'collect'
    }

    private static void simpsonsStacked() {
        //Print the characters on top of one another
        //Hint - you might want to use 'reduce'
    }
}
