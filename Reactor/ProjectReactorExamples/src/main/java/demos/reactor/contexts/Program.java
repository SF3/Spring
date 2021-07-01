package demos.reactor.contexts;

import reactor.core.publisher.Flux;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Program {
    public static void main(String[] args) throws Exception {

        Flux<String> flux = Flux.deferContextual(contextView -> {
            return buildTestData(5).map(number -> {
                String contextData = contextView.get("time");
                return number + " " + contextData;
            });
        }).contextWrite(context -> context.put("time", timeRightNow()));

        flux.subscribe(str -> System.out.println("S1: " + str));
        Thread.sleep(5000);
        flux.subscribe(str -> System.out.println("S2: " + str));
    }

    private static String timeRightNow() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        return dtf.format(LocalTime.now());
    }

    private static Flux<Integer> buildTestData(int count) {
        return Flux.generate(
                () -> -1,
                (state, sink) -> {
                    int nextState = state + 1;
                    if (nextState == count) {
                        sink.complete();
                    } else {
                        sink.next(nextState);
                    }
                    return nextState;
                });
    }
}
