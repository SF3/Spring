package demos.reactor.testing;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.function.Supplier;

class Program {

    public static void main(String[] args) {
        showBasicVerification();
        showExpectations();
        showTestingTime();

        System.out.println("All verifications successful");
    }

    private static void showBasicVerification() {
        Flux<String> data = Flux.just("abc", "def", "ghi", "jkl");
        StepVerifier.FirstStep<String> verifier = StepVerifier.create(data);
        verifier.expectNext("abc")
                .expectNext("def")
                .expectNext("ghi")
                .expectNext("jkl")
                .verifyComplete();
    }

    private static void showExpectations() {
        Flux<String> data = Flux.just("abc", "def", "ghi", "jkl", "mno");
        StepVerifier.FirstStep<String> verifier = StepVerifier.create(data);

        verifier.expectNextMatches(str -> str.matches("[a-z]{3}"))
                .expectNextCount(2)
                .expectNextSequence(Arrays.asList("jkl", "mno"))
                .verifyComplete();
    }

    private static void showTestingTime() {
        Supplier<Flux<String>> builder = () -> Flux
                .just("abc", "def", "ghi", "jkl", "mno")
                .delayElements(Duration.ofHours(2));

        StepVerifier.withVirtualTime(builder)
                .expectSubscription()
                .thenAwait(Duration.ofHours(5))
                .expectNext("abc")
                .expectNext("def")
                .thenAwait(Duration.ofHours(2))
                .expectNext("ghi")
                .thenAwait(Duration.ofDays(1))
                .expectNextCount(2)
                .verifyComplete();
    }
}
