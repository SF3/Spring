package demos.reactor.operators;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import demos.reactor.operators.entity.Contact;
import demos.reactor.operators.entity.Department;
import demos.reactor.operators.entity.Employee;
import demos.reactor.operators.entity.Name;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuples;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

import static demos.reactor.operators.entity.Department.*;

public class Program {
    public static void main(String[] args) {
        displayWelcome();
        showStandardOperators();
        showOperatorChaining();
        showObservingOperators();
        showBatchingOperators();
        showBlockingOperators();
        showCombiningOperators();
    }

    private static void displayWelcome() {
        Mono.just("Examples of the Flux | Mono Operators")
                .subscribe(printer(0));
    }

    private static void showStandardOperators() {
        printTitle("The standard FP toolkit");
        showCommonOperators();
        showConvertingToList();
        showConvertingToMap();
        showTesting();
    }

    private static void showCommonOperators() {
        printer(1).accept("Common FP operators");

        String text = "Names of contacts of IT staff: ";
        Flux<Employee> staff = buildData();

        staff.filter(emp -> emp.getDept() == IT)
                .flatMapIterable(Employee::getContacts)
                .map(Contact::getName)
                .reduce(text, (a, b) -> a + " \"" + b + "\"")
                .subscribe(printer(2));
    }

    private static void showTesting() {
        printer(1).accept("FP testing operators");

        Flux<Employee> staff = buildData();
        Mono<Boolean> r1 = staff.filter(emp -> emp.getDept() == HR).all(emp -> emp.getSalary() == 30000.0);
        Mono<Boolean> r2 = staff.filter(emp -> emp.getDept() == IT).all(emp -> emp.getSalary() == 40000.0);
        Mono<Boolean> r3 = staff.filter(emp -> emp.getDept() == Sales).all(emp -> emp.getSalary() == 50000.0);

        Flux.concat(r1, r2, r3)
                .all(b -> b)
                .subscribe(b -> {
                    String word = b ? "are" : "are NOT";
                    printer(2).accept("Salary bands " + word + " correct");
                });
    }

    private static void showConvertingToList() {
        printer(1).accept("Converting to a List");

        String text = "Names of contacts of HR staff: ";
        Flux<Employee> staff = buildData();
        staff.filter(emp -> emp.getDept() == HR)
                .flatMapIterable(Employee::getContacts)
                .map(Contact::getName)
                .collectList()
                .subscribe(names -> printer(2).accept(text + names));
    }

    private static void showConvertingToMap() {
        printer(1).accept("Converting to a Map");

        Flux<Employee> staff = buildData();
        staff.collectMultimap(Employee::getDept, Employee::getName)
                .subscribe(multiMap -> {
                    multiMap.forEach((dept, contacts) -> {
                        String output = String.format("Names of contacts of %s staff: %s", dept, contacts);
                        printer(2).accept(output);
                    });
                });
    }

    private static void showOperatorChaining() {
        printTitle("Reusing chains of operators ");
        String text = "Names of contacts of IT staff: ";

        Function<Flux<Employee>, Mono<String>> chain = input ->
                input.flatMapIterable(Employee::getContacts)
                        .map(Contact::getName)
                        .reduce(text, (a, b) -> a + " \"" + b + "\"");

        Flux<Employee> staff = buildData();
        staff.filter(emp -> emp.getDept() == IT)
                .transform(chain)
                .subscribe(printer(1));
    }

    private static void showBatchingOperators() {
        printTitle("Batching items via groups, windows and buffers");
        showGrouping();
        showBuffering();
        showWindowing();
    }

    private static void showGrouping() {
        printer(1).accept("Staff grouped by Department");
        Flux<Employee> staff = buildData();
        staff.groupBy(Employee::getDept)
                .subscribe(group -> {
                    Department dept = group.key();
                    group.reduce(Tuples.of(0, 0.0), (tuple, emp) -> {
                        int newCount = tuple.getT1() + 1;
                        double newTotal = tuple.getT2() + emp.getSalary();
                        return Tuples.of(newCount, newTotal);
                    }).subscribe(tuple -> {
                        String msg = String.format("%s has %d staff and a salary bill of %.2f", dept, tuple.getT1(), tuple.getT2());
                        printer(2).accept(msg);
                    });
                });
    }

    private static void showBuffering() {
        printer(1).accept("Staff buffered to include Sales");
        Flux<Employee> staff = buildData();
        staff.bufferUntil(emp -> emp.getDept() == Sales)
                .subscribe(list -> {
                    printer(2).accept("New list of staff");
                    list.forEach(emp -> {
                        String msg = String.format("%s in %s", emp.getName(), emp.getDept());
                        printer(3).accept(msg);
                    });
                });
    }

    private static void showObservingOperators() {
        printTitle("Observing the Flux in action");

        Consumer<Object> printMsg = printer(2);
        Flux<Employee> staff = buildData();
        staff.map(Employee::getName)
                .doOnEach(signal -> printMsg.accept("OnEach for: " + signal.getType()))
                .doOnNext(name -> printMsg.accept("OnNext for: " + name))
                .doOnSubscribe(sub -> printMsg.accept("OnSubscribe"))
                .doOnComplete(() -> printMsg.accept("OnComplete"))
                .doFinally(signalType -> printMsg.accept("Finally"))
                .doAfterTerminate(() -> printMsg.accept("AfterTerminate"))
                .subscribe();
    }

    private static void showWindowing() {
        printer(1).accept("Staff windowed to include Sales");
        Flux<Employee> staff = buildData();
        staff.windowUntil(emp -> emp.getDept() == Sales)
                .doOnEach(salesStaff -> printer(2).accept("New window of staff"))
                .flatMap(Function.identity())
                .subscribe(emp -> {
                    String msg = String.format("%s in %s", emp.getName(), emp.getDept());
                    printer(3).accept(msg);
                });
    }

    private static void showBlockingOperators() {
        printTitle("Introducing blocking");
        Consumer<Object> printMsg = printer(1);

        Name first = buildData()
                .map(Employee::getName)
                .blockFirst();
        printMsg.accept("First employee was " + first);

        Name last = buildData()
                .map(Employee::getName)
                .blockLast();
        printMsg.accept("Last employee was " + last);

        Iterator<Name> nameIterator = buildData()
                .map(Employee::getName)
                .toIterable()
                .iterator();
        nameIterator.next();
        printMsg.accept("Second employee was " + nameIterator.next());

        String firstInSales = buildData()
                .toStream()
                .filter(emp -> emp.getDept() == Sales)
                .findFirst()
                .map(emp -> emp.getName().toString())
                .orElse("Arthur Daley");
        printMsg.accept("First employee from Sales was " + firstInSales);
    }

    private static void showCombiningOperators() {
        printTitle("Combining different Fluxes");
        showThen();
        showConcatMap();
        showZip();
        showMerge();
    }

    private static void showThen() {
        printer(1).accept("Using the 'then' operator");
        Consumer<Object> printMsg = printer(2);

        Mono<Long> mono1 = buildData()
                .filter(emp -> emp.getDept() == Sales)
                .count()
                .doOnNext(num -> printMsg.accept("Sales count is " + num));

        Mono<Long> mono2 = buildData()
                .filter(emp -> emp.getDept() == HR)
                .count()
                .doOnNext(num -> printMsg.accept("HR count is " + num));

        Mono<Long> mono3 = mono1.then(mono2);
        mono3.subscribe(num -> printMsg.accept("Output of combined Mono is " + num));
    }

    private static void showConcatMap() {
        printer(1).accept("Using the 'concatMap' operator");

        String text = "All the contacts of Sales staff: ";
        buildData().filter(emp -> emp.getDept() == Sales)
                .map(Employee::getContacts)
                .concatMap(contacts -> Flux.fromIterable(contacts))
                .map(Contact::getName)
                .collectList()
                .subscribe(name -> printer(2).accept(text + name));
    }

    private static void showZip() {
        printer(1).accept("Using the 'zip' operator");

        Flux<String> salesFlux = buildData()
                .filter(emp -> emp.getDept() == Sales)
                .map(emp -> emp.getName().getFirst() + " from Sales");

        Flux<String> hrFlux = buildData()
                .filter(emp -> emp.getDept() == HR)
                .map(emp -> emp.getName().getFirst() + " from HR");

        Flux.zip(salesFlux, hrFlux)
                .map(tuple -> tuple.getT1() + " and " + tuple.getT2())
                .subscribe(printer(2));
    }

    private static void showMerge() {
        printer(1).accept("Using the 'merge' operator");
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Scheduler scheduler = Schedulers.fromExecutor(pool);

        ParallelFlux<String> salesFlux = buildData()
                .filter(emp -> emp.getDept() == Sales)
                .map(emp -> emp.getName().getFirst() + " from Sales")
                .delayElements(Duration.ofMillis(100))
                .parallel()
                .runOn(scheduler);

        ParallelFlux<String> hrFlux = buildData()
                .filter(emp -> emp.getDept() == HR)
                .map(emp -> emp.getName().getFirst() + " from HR")
                .delayElements(Duration.ofMillis(100))
                .parallel()
                .runOn(scheduler);

        Flux<String> flux = Flux.merge(salesFlux, hrFlux);
        flux.doOnNext(printer(2))
                .blockLast();

        pool.shutdown();
        try {
            if (!pool.awaitTermination(10, TimeUnit.SECONDS)) {
                System.err.println("Thread pool failed to finish");
            }
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    private static void printTitle(String title) {
        System.out.printf("\n---------- %s ----------\n", title);
    }

    private static Consumer<Object> printer(int level) {
        String indent = "\t".repeat(level);
        return info -> System.out.printf("%s%s\n", indent, info);
    }

    private static Flux<Employee> buildData() {
        Path path = Paths.get("./data/staff.json");
        ObjectMapper mapper = new ObjectMapper();
        CollectionType type = mapper.getTypeFactory()
                .constructCollectionType(List.class, Employee.class);

        List<Employee> staff;
        try {
            staff = mapper.readValue(path.toFile(), type);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return Flux.fromIterable(staff);
    }
}
