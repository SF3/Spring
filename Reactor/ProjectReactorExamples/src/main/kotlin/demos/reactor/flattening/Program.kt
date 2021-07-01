package demos.reactor.flattening

import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.time.Duration

fun main() {
    printMenu()
    runUI()
}

fun showFlatMap() {
    println("----- Show flatMap -----")

    buildBaseData()
        .flatMap(::buildTestData)
        .subscribe(::printTabbed)
}

fun showFlatMapSequential() {
    println("----- Show flatMap Sequential -----")

    buildBaseData()
        .flatMapSequential(::buildTestData)
        .subscribe(::printTabbed)
}

fun showConcatMap() {
    println("----- Show concatMap -----")

    buildBaseData()
        .concatMap(::buildTestData)
        .subscribe(::printTabbed)
}

fun showSwitchMap() {
    println("----- Show switchMap -----")

    buildBaseData()
        .switchMap(::buildTestData)
        .subscribe(::printTabbed)
}

fun buildBaseData(): Flux<String> = Flux
    .just("Alpha", "Bravo", "Charlie")
    .publishOn(Schedulers.parallel())

fun buildTestData(prefix: String): Flux<String> {
    val testData = Flux.generate<String?, Int?>({ -1 }) { state, sink ->
        val nextState = state + 1
        when (nextState) {
            3 -> sink.complete()
            else -> sink.next("$prefix: $nextState")
        }
        nextState
    }
    return testData.delayElements(Duration.ofMillis(500))
}

fun printTabbed(thing: Any) = println("\t$thing")

fun printMenu() = println(
    """
        --- Demos of Flattening Data ---
        1) flatMap
        2) flatMapSequential
        3) concatMap
        4) switchMap
        5) Exit
""".replaceIndent()
)

fun runUI() {
    loop@ while (true) {
        when (readLine()?.toInt() ?: 5) {
            1 -> showFlatMap()
            2 -> showFlatMapSequential()
            3 -> showConcatMap()
            4 -> showSwitchMap()
            5 -> break@loop
            else -> println("Unknown choice")
        }
    }
}
