package demos.reactor.errors

import reactor.core.publisher.Flux

fun main() {
    showNormalOperation()
    showErrorHandler()
    showFallbackValue()
    showFallbackMethod()
    showSideEffects()
    showHandle()
}

fun showNormalOperation() {
    println("----- Normal Operation -----")
    buildTestData(-1, 5)
        .subscribe(::printTabbed, ::errorHandler, ::completionHandler)
}

fun showErrorHandler() {
    println("----- Error Handler -----")
    buildTestData(3, 5)
        .subscribe(::printTabbed, ::errorHandler, ::completionHandler)
}

fun showFallbackValue() {
    println("----- Fallback Value -----")
    buildTestData(3, 5)
        .onErrorReturn(101)
        .subscribe(::printTabbed, ::errorHandler, ::completionHandler)
}

fun showFallbackMethod() {
    println("----- Fallback Method -----")
    buildTestData(3, 5)
        .onErrorResume { Flux.just(100, 200, 300) }
        .subscribe(::printTabbed, ::errorHandler, ::completionHandler)
}

fun showSideEffects() {
    println("----- Side Effects -----")
    buildTestData(3, 5)
        .doOnError { ex -> printTabbed("This just happened: ${ex.message}") }
        .onErrorResume { Flux.just(100, 200, 300) }
        .subscribe(::printTabbed, ::errorHandler, ::completionHandler)
}

fun showHandle() {
    println("----- Handle Method -----")
    buildTestData(-1, 5)
        .handle<Int> { number, sink ->
            if (number != 3) {
                sink.next(number)
            } else {
                sink.error(IllegalStateException("Can't handle 3"))
            }
        }
        .subscribe(::printTabbed, ::errorHandler, ::completionHandler)
}

fun buildTestData(erroneous: Int, count: Int): Flux<Int> {
    return Flux.generate(
        { -1 }
    ) { state, sink ->
        val nextState = state + 1
        when (nextState) {
            erroneous -> throw IllegalStateException("Can't handle $nextState")
            count -> sink.complete()
            else -> sink.next(nextState)
        }
        nextState
    }
}

fun printTabbed(thing: Any) = println("\t$thing")
fun errorHandler(ex: Throwable) = printTabbed("Caught: ${ex.message}")
fun completionHandler() = printTabbed("End of data")

