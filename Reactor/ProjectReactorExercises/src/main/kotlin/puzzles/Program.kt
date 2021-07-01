package puzzles

import reactor.core.publisher.Flux
import java.time.Duration

fun main() {
    println("Running examples - hit return to exit")

    //TODO: Try to predict the behaviour of these functions,
    // then run them to see if your expectations are correct
    puzzle1()
    puzzle2()
    puzzle3()

    readLine()
    println("Bye...")
}

fun puzzle1() {
    println("\n--- Puzzle 1 ---")

    val names = listOf("Jane", "Pete", "Mary", "Fred")
    val flux = Flux.fromIterable(names)

    flux.map(String::toUpperCase)
    flux.subscribe(::println)
}

fun puzzle2() {
    println("\n--- Puzzle 2 ---")

    val flux = fluxOfFourIntegers()

    flux.subscribe(
        { println("S1 received: $it") },
        { println("S1 error: ${it.message}") },
        { println("S1 complete") }
    )
    flux.subscribe(
        { println("S2 received: $it") },
        { println("S2 error: ${it.message}") },
        { println("S2 complete") }
    )
}

fun puzzle3() {
    println("\n--- Puzzle 3 ---")

    val flux = Flux.just("foo", "bar", "zed")
    flux.delayElements(Duration.ofMillis(500))
        .subscribe { println("Received $it on ${Thread.currentThread().id}") }
}

private fun fluxOfFourIntegers() = Flux.generate<Int, Pair<Int, Int>>(
    { Pair((Math.random() * 100).toInt(), 1) },
    { (total, count), sink ->
        val newState = Pair(total + 1, count + 1)
        if (count < 5) {
            sink.next(newState.first)
        } else {
            sink.complete()
        }
        newState
    }
)
