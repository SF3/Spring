package demos.reactor.connectable;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws Exception {
        printMenu();
        runLoop();
        tidyUp();
    }

    private static void printMenu() {
        System.out.println("--- Demos of Connectable Flux ---");
        System.out.println("1) Regular Flux");
        System.out.println("2) Connectable Flux using Connect");
        System.out.println("3) Connectable Flux using Auto Connect");
        System.out.println("4) Connectable Flux using Replay");
        System.out.println("5) Exit");
    }

    private static void runLoop() throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    showRegularFlux();
                    break;
                case 2:
                    showConnect();
                    break;
                case 3:
                    showAutoConnect();
                    break;
                case 4:
                    showReplay();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Unknown option");
            }
        }
    }

    private static void tidyUp() {
        System.out.println("Bye...");
    }

    private static void showRegularFlux() {
        System.out.println("----- Regular Flux -----");

        Flux<String> source = Flux.just("ab", "cd", "ef");

        source.subscribe(s -> System.out.printf("First subscriber received %s\n", s));
        source.subscribe(s -> System.out.printf("Second subscriber received %s\n", s));
        source.subscribe(s -> System.out.printf("Third subscriber received %s\n", s));
    }

    private static void showConnect() throws Exception {
        System.out.println("----- Connectable Flux Using Connect-----");

        ConnectableFlux<String> source = Flux
                .just("ab", "cd", "ef", "gh", "ij", "kl", "mn", "op")
                .delayElements(Duration.ofMillis(500))
                .publish();

        addSubscribersViaConnect(source);
    }

    private static void showAutoConnect() throws Exception {
        System.out.println("----- Connectable Flux Using Auto Connect -----");

        Flux<String> source = Flux
                .just("ab", "cd", "ef", "gh", "ij", "kl", "mn", "op")
                .delayElements(Duration.ofMillis(500))
                .publish()
                .autoConnect(4);

        addSubscribers(source);
    }

    private static void showReplay() throws Exception {
        System.out.println("----- Connectable Flux With Replay -----");

        ConnectableFlux<String> source = Flux
                .just("ab", "cd", "ef", "gh", "ij", "kl", "mn", "op")
                .delayElements(Duration.ofMillis(500))
                .replay();

        addSubscribersViaConnect(source);
    }

    private static void addSubscribers(Flux<String> source) throws Exception {
        source.subscribe(s -> System.out.printf("First subscriber received %s\n", s));
        source.subscribe(s -> System.out.printf("Second subscriber received %s\n", s));
        source.subscribe(s -> System.out.printf("Third subscriber received %s\n", s));

        Thread.sleep(2000);
        System.out.println("----------");

        source.subscribe(s -> System.out.printf("Fourth subscriber received %s\n", s));
        source.subscribe(s -> System.out.printf("Fifth subscriber received %s\n", s));
    }

    private static void addSubscribersViaConnect(ConnectableFlux<String> source) throws Exception {
        source.subscribe(s -> System.out.printf("First subscriber received %s\n", s));
        source.subscribe(s -> System.out.printf("Second subscriber received %s\n", s));
        source.subscribe(s -> System.out.printf("Third subscriber received %s\n", s));

        source.connect();
        Thread.sleep(2000);
        System.out.println("----------");

        source.subscribe(s -> System.out.printf("Fourth subscriber received %s\n", s));
        source.subscribe(s -> System.out.printf("Fifth subscriber received %s\n", s));
    }
}
