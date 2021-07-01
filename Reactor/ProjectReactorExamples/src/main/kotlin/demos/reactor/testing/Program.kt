package demos.reactor.testing

import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.time.Duration

fun main() {
    showBasicVerification()
    showExpectations()
    showTestingTime()

    println("All verifications successful")
}

private fun showBasicVerification() {
    val data = Flux.just("abc", "def", "ghi", "jkl")
    StepVerifier.create(data)
        .expectNext("abc")
        .expectNext("def")
        .expectNext("ghi")
        .expectNext("jkl")
        .verifyComplete()
}

private fun showExpectations() {
    val data = Flux.just("abc", "def", "ghi", "jkl", "mno")
    StepVerifier.create(data)
        .expectNextMatches { it.matches(Regex("[a-z]{3}")) }
        .expectNextCount(2)
        .expectNextSequence(listOf("jkl", "mno"))
        .verifyComplete()
}

private fun showTestingTime() {
    val builder = {
        Flux.just("abc", "def", "ghi", "jkl", "mno")
            .delayElements(Duration.ofHours(2))
    }

    StepVerifier.withVirtualTime(builder)
        .expectSubscription()
        .thenAwait(Duration.ofHours(5))
        .expectNext("abc")
        .expectNext("def")
        .thenAwait(Duration.ofHours(2))
        .expectNext("ghi")
        .thenAwait(Duration.ofDays(1))
        .expectNextCount(2)
        .verifyComplete()
}
