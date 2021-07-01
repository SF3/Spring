package demos.reactor.errors;

import reactor.core.publisher.Flux;

public class Program {
    public static void main(String[] args) {
        showNormalOperation();
        showErrorHandler();
        showFallbackValue();
        showFallbackMethod();
        showSideEffects();
        showHandle();
    }

    private static void showNormalOperation() {
        System.out.println("----- Normal Operation -----");
        buildTestData(-1, 5)
                .subscribe(
                        Program::printTabbed,
                        Program::errorHandler,
                        Program::completionHandler);
    }

    private static void showErrorHandler() {
        System.out.println("----- Error Handler -----");
        buildTestData(3, 5)
                .subscribe(
                        Program::printTabbed,
                        Program::errorHandler,
                        Program::completionHandler);
    }

    private static void showFallbackValue() {
        System.out.println("----- Fallback Value -----");
        buildTestData(3, 5)
                .onErrorReturn(101)
                .subscribe(
                        Program::printTabbed,
                        Program::errorHandler,
                        Program::completionHandler);
    }

    private static void showFallbackMethod() {
        System.out.println("----- Fallback Method -----");
        buildTestData(3, 5)
                .onErrorResume(ex -> Flux.just(100, 200, 300))
                .subscribe(
                        Program::printTabbed,
                        Program::errorHandler,
                        Program::completionHandler);
    }

    private static void showSideEffects() {
        System.out.println("----- Side Effects -----");
        buildTestData(3, 5)
                .doOnError(ex -> printTabbed("This just happened: " + ex.getMessage()))
                .onErrorResume(ex -> Flux.just(100, 200, 300))
                .subscribe(
                        Program::printTabbed,
                        Program::errorHandler,
                        Program::completionHandler);
    }

    private static void showHandle() {
        System.out.println("----- Handle Method -----");
        buildTestData(-1, 5)
                .handle((number, sink) -> {
                    if (number != 3) {
                        sink.next(number);
                    } else {
                        sink.error(new IllegalStateException("Can't handle 3"));
                    }
                })
                .subscribe(
                        Program::printTabbed,
                        Program::errorHandler,
                        Program::completionHandler);
    }

    private static Flux<Integer> buildTestData(int erroneous, int count) {
        return Flux.generate(
                () -> -1,
                (state, sink) -> {
                    int nextState = state + 1;
                    if (nextState == erroneous) {
                        throw new IllegalStateException("Can't handle " + nextState);
                    } else if (nextState == count) {
                        sink.complete();
                    } else {
                        sink.next(nextState);
                    }
                    return nextState;
                });
    }

    private static void printTabbed(Object thing) {
        System.out.println("\t" + thing);
    }

    private static void errorHandler(Throwable ex) {
        printTabbed("Caught: " + ex.getMessage());
    }

    private static void completionHandler() {
        printTabbed("End of data");
    }
}
