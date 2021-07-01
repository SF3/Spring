package demos.reactor.threading.external

import demos.reactor.threading.shared.randomValue
import demos.reactor.threading.shared.printTabbed
import demos.reactor.threading.shared.threadId

import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

fun main() {
    val pool1 = Executors.newFixedThreadPool(2)
    val pool2 = Executors.newFixedThreadPool(2)

    runUI(arrayOf(pool1, pool2))
}

private fun runUI(pools: Array<ExecutorService>) {
    fun printMenu() = println(
        """
        --- Demos of Threading and Flux ---
        1) Synchronous
        2) Async Publisher
        3) Async Subscriber
        4) Async Publisher and Subscriber
        5) Exit
""".replaceIndent()
    )

    fun tidyUp() {
        println("Shutting down thread pools")
        pools.forEach {
            it.shutdown()
            if (it.awaitTermination(10, TimeUnit.SECONDS)) {
                println("Thread pool closed")
            }
        }
        println("Bye...")
    }

    fun runLoop() {
        loop@ while (true) {
            when (readLine()?.toInt() ?: 5) {
                1 -> showSynchronousProcessing()
                2 -> showAsyncPublisher(pools[0])
                3 -> showAsyncSubscriber(pools[0])
                4 -> showAsyncPublisherAndSubscriber(pools[0], pools[1])
                5 -> break@loop
                else -> println("Unknown choice")
            }
        }
    }

    printMenu()
    runLoop()
    tidyUp()
}

fun showSynchronousProcessing() {
    println("--- Synchronous Flux ---")
    val flux = Mono.fromCallable(::randomValue)
        .repeat(5)
        .doOnNext { printTabbed("$it generated on ${threadId()}") }

    flux.subscribe { printTabbed("$it received by S1 on ${threadId()}") }
    flux.subscribe { printTabbed("$it received by S2 on ${threadId()}") }
}

fun showAsyncPublisher(pool: ExecutorService) {
    println("--- Flux With Async Publisher ---")
    val flux = Mono.fromCallable(::randomValue)
        .repeat(5)
        .doOnNext { printTabbed("$it generated on ${threadId()}") }
        .publishOn(Schedulers.fromExecutor(pool))
        .doOnNext { printTabbed("$it moved to ${threadId()}") }

    flux.subscribe { printTabbed("$it received on ${threadId()}") }
}

fun showAsyncSubscriber(pool: ExecutorService) {
    println("--- Flux With Async Subscriber ---")
    val flux = Mono.fromCallable(::randomValue)
        .repeat(10)
        .doOnNext { printTabbed("$it generated on ${threadId()}") }
        .subscribeOn(Schedulers.fromExecutor(pool))
        .doOnNext { printTabbed("$it still on ${threadId()}") }

    flux.subscribe { printTabbed("$it received by S1 on ${threadId()}") }
    flux.subscribe { printTabbed("$it received by S2 on ${threadId()}") }
}

fun showAsyncPublisherAndSubscriber(pool1: ExecutorService, pool2: ExecutorService) {
    println("--- Flux With Async Publisher And Subscriber ---")
    val flux = Mono.fromCallable(::randomValue)
        .repeat(10)
        .doOnNext { printTabbed("$it generated on ${threadId()}") }
        .subscribeOn(Schedulers.fromExecutor(pool1))
        .publishOn(Schedulers.fromExecutor(pool2))
        .doOnNext { printTabbed("$it now on ${threadId()}") }

    flux.subscribe { printTabbed("$it received by S1 on ${threadId()}") }
    flux.subscribe { printTabbed("$it received by S2 on ${threadId()}") }
}
