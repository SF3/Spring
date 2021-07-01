package demos.reactor.threading.internal

import demos.reactor.threading.shared.randomValue
import demos.reactor.threading.shared.printTabbed
import demos.reactor.threading.shared.threadId
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

fun main() {
    val pool = Executors.newFixedThreadPool(2)
    runUI(pool)
}

private fun runUI(pool: ExecutorService) {
    fun printMenu() = println(
        """
        --- Demos of Threading and Flux ---
        1) Async and the Flatmap operation
        2) Immediate Scheduler
        3) Single Scheduler
        4) Parallel Scheduler
        5) Elastic Scheduler
        6) Exit
""".replaceIndent()
    )

    fun tidyUp() {
        println("Shutting down thread pool")
        pool.run {
            shutdown()
            if (awaitTermination(10, TimeUnit.SECONDS)) {
                println("Thread pool closed")
            }
        }
        println("Bye...")
    }

    fun runLoop() {
        loop@ while (true) {
            when (readLine()?.toInt() ?: 6) {
                1 -> showAsyncFlatMap(pool)
                2 -> showImmediateScheduler()
                3 -> showSingleScheduler()
                4 -> showParallelScheduler()
                5 -> showElasticScheduler()
                6 -> break@loop
                else -> println("Unknown option")
            }
        }
    }

    printMenu()
    runLoop()
    tidyUp()
}

fun showAsyncFlatMap(pool: ExecutorService) {
    val scheduler = Schedulers.fromExecutor(pool)
    val createValues = { label: String ->
        Flux.range(1, 10)
            .map { "$label$it" }
            .subscribeOn(scheduler)
    }

    println("--- Async with FlatMap ---")
    Flux.fromArray(arrayOf("A", "B"))
        .flatMap(createValues)
        .subscribe { printTabbed("$it received on ${threadId()}") }
}

fun showImmediateScheduler() {
    println("--- Immediate Scheduler ---")
    Mono.fromCallable(::randomValue)
        .repeat(10)
        .parallel()
        .runOn(Schedulers.immediate())
        .subscribe { printTabbed("$it received by subscriber on ${threadId()}") }
}

fun showSingleScheduler() {
    println("--- Single Scheduler ---")
    Mono.fromCallable(::randomValue)
        .repeat(10)
        .parallel()
        .runOn(Schedulers.single())
        .subscribe { printTabbed("$it received by subscriber on ${threadId()}") }
}

fun showParallelScheduler() {
    println("--- Parallel Scheduler ---")
    Mono.fromCallable(::randomValue)
        .repeat(10)
        .parallel()
        .runOn(Schedulers.parallel())
        .subscribe { printTabbed("$it received by subscriber on ${threadId()}") }
}

fun showElasticScheduler() {
    println("--- Elastic Scheduler ---")
    Mono.fromCallable(::randomValue)
        .repeat(10)
        .parallel()
        .runOn(Schedulers.boundedElastic())
        .subscribe { printTabbed("$it received by subscriber on ${threadId()}") }
}
