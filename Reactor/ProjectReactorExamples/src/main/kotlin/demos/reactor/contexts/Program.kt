package demos.reactor.contexts

import reactor.core.publisher.Flux
import java.time.LocalTime

import java.time.format.DateTimeFormatter

fun main() {
    val flux = Flux.deferContextual { contextView ->
        buildTestData(5).map { number ->
            val contextData = contextView.get<String>("time")
            "$number $contextData"
        }
    }.contextWrite { context ->
        context.put("time", timeRightNow())
    }
    flux.subscribe { str -> println("S1: $str") }
    Thread.sleep(5000)
    flux.subscribe { str -> println("S2: $str") }
}

fun timeRightNow(): String {
    val dtf = DateTimeFormatter.ofPattern("HH:mm:ss")
    return dtf.format(LocalTime.now())
}

fun buildTestData(count: Int): Flux<Int> {
    return Flux.generate(
        { -1 },
        { state, sink ->
            val nextState = state + 1
            when (nextState) {
                count -> sink.complete()
                else -> sink.next(nextState)
            }
            nextState
        })
}
