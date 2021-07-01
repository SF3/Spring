package demos.reactor.connectable

import reactor.core.publisher.ConnectableFlux
import reactor.core.publisher.Flux
import java.time.Duration

fun main() {
    fun printMenu() = println(
        """
        --- Demos of Connectable Flux ---
        1) Regular Flux
        2) Connectable Flux using Connect
        3) Connectable Flux using Auto Connect
        4) Connectable Flux using Replay
        5) Exit
""".replaceIndent()
    )

    fun tidyUp() = println("Bye...")

    fun runLoop() {
        loop@ while (true) {
            when (readLine()?.toInt() ?: 4) {
                1 -> showRegularFlux()
                2 -> showConnect()
                3 -> showAutoConnect()
                4 -> showReplay()
                5 -> break@loop
                else -> println("Unknown choice")
            }
        }
    }

    printMenu()
    runLoop()
    tidyUp()
}

fun showRegularFlux() {
    println("----- Regular Flux -----")

    val source: Flux<String> = Flux
        .just("ab", "cd", "ef")

    source.subscribe { println("First subscriber received $it") }
    source.subscribe { println("Second subscriber received $it") }
    source.subscribe { println("Third subscriber received $it") }
}

fun showConnect() {
    println("----- Connectable Flux Using Connect-----")

    val source: ConnectableFlux<String> = Flux
        .just("ab", "cd", "ef", "gh", "ij", "kl", "mn", "op")
        .delayElements(Duration.ofMillis(500))
        .publish()

    addSubscribersViaConnect(source)
}

fun showAutoConnect() {
    println("----- Connectable Flux Using Auto Connect -----")

    val source = Flux
        .just("ab", "cd", "ef", "gh", "ij", "kl", "mn", "op")
        .delayElements(Duration.ofMillis(500))
        .publish()
        .autoConnect(4)

    addSubscribers(source)
}

fun showReplay() {
    println("----- Connectable Flux With Replay -----")

    val source: ConnectableFlux<String> = Flux
        .just("ab", "cd", "ef", "gh", "ij", "kl", "mn", "op")
        .delayElements(Duration.ofMillis(500))
        .replay()

    addSubscribersViaConnect(source)
}

fun addSubscribers(source: Flux<String>) {
    source.subscribe { println("First subscriber received $it") }
    source.subscribe { println("Second subscriber received $it") }
    source.subscribe { println("Third subscriber received $it") }

    Thread.sleep(2000)
    println("----------")

    source.subscribe { println("Fourth subscriber received $it") }
    source.subscribe { println("Fifth subscriber received $it") }
}

fun addSubscribersViaConnect(source: ConnectableFlux<String>) {
    source.subscribe { println("First subscriber received $it") }
    source.subscribe { println("Second subscriber received $it") }
    source.subscribe { println("Third subscriber received $it") }

    source.connect()
    Thread.sleep(2000)
    println("----------")

    source.subscribe { println("Fourth subscriber received $it") }
    source.subscribe { println("Fifth subscriber received $it") }
}
