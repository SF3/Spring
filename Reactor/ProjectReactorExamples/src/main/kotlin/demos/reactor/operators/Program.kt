package demos.reactor.operators

import com.fasterxml.jackson.databind.ObjectMapper
import demos.reactor.operators.entity.Department
import demos.reactor.operators.model.Department.*
import demos.reactor.operators.model.Employee
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Signal
import reactor.core.scheduler.Schedulers
import java.lang.IllegalStateException
import java.nio.file.Paths
import java.time.Duration
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Predicate

fun main() {
    displayWelcome()
    showStandardOperators()
    showOperatorChaining()
    showObservingOperators()
    showBatchingOperators()
    showBlockingOperators()
    showCombiningOperators()
}

fun displayWelcome() {
    Mono.just("\nExamples of the Flux | Mono Operators\n")
        .subscribe(printer(0))
}

fun showStandardOperators() {
    fun showCommonOperators() {
        printer(1)("Common FP operators")

        val text = "Names of contacts of IT staff: "
        val staff = buildData()
        staff.filter { it.dept == IT }
            .flatMapIterable { it.contacts }
            .map { it.name }
            .reduce(text) { a, b -> "$a \"$b\"" }
            .subscribe(printer(2))
    }

    fun showTesting() {
        printer(1)("FP testing operators")

        val staff = buildData()
        val r1 = staff.filter { it.dept == HR }.all { it.salary == 30000.0 }
        val r2 = staff.filter { it.dept == IT }.all { it.salary == 40000.0 }
        val r3 = staff.filter { it.dept == Sales }.all { it.salary == 50000.0 }

        Flux.concat(r1, r2, r3)
            .all { it }
            .subscribe {
                val word = if (it) "are" else "are NOT"
                printer(2)("Salary bands $word correct")
            }
    }

    fun showConvertingToList() {
        printer(1)("Converting to a List")

        val text = "Names of contacts of HR staff: "
        val staff = buildData()
        staff.filter { it.dept == HR }
            .flatMapIterable { it.contacts }
            .map { it.name }
            .collectList()
            .subscribe { printer(2)(text + it) }
    }

    fun showConvertingToMap() {
        printer(1)("Converting to a Map")

        val staff = buildData()
        staff.collectMultimap({ it.dept }, { it.name })
            .subscribe { multiMap ->
                multiMap.forEach { (dept, contacts) ->
                    printer(2)("Names of contacts of $dept staff: $contacts")
                }
            }
    }

    printTitle("The standard FP toolkit")
    showCommonOperators()
    showConvertingToList()
    showConvertingToMap()
    showTesting()
}

fun showOperatorChaining() {
    printTitle("Reusing chains of operators ")
    val text = "Names of contacts of IT staff: "
    val chain: (Flux<Employee>) -> Mono<String> = { input ->
        input.flatMapIterable { it.contacts }
            .map { it.name }
            .reduce(text) { a, b -> "$a \"$b\"" }
    }

    val staff = buildData()
    staff.filter { it.dept == IT }
        .transform(chain)
        .subscribe(printer(1))
}

fun showObservingOperators() {
    printTitle("Observing the Flux in action")

    val printMsg = printer(2)
    val staff = buildData()
    staff.map { it.name }
        .doOnEach { signal -> printMsg("OnEach for: ${signal.type}") }
        .doOnNext { name -> printMsg("OnNext for: $name") }
        .doOnSubscribe { printMsg("OnSubscribe") }
        .doOnComplete { printMsg("OnComplete") }
        .doFinally { printMsg("Finally") }
        .doAfterTerminate { printMsg("AfterTerminate") }
        .subscribe()
}

fun showBatchingOperators() {
    fun showGrouping() {
        printer(1)("Staff grouped by Department")
        val staff = buildData()
        staff.groupBy { it.dept }
            .subscribe { group ->
                val dept = group.key()
                group.reduce(Pair(0, 0.0)) { (count, salary), emp ->
                    Pair(count + 1, salary + emp.salary)
                }.subscribe { (count, bill) ->
                    printer(2)("$dept has $count staff and a salary bill of $bill")
                }
            }
    }

    fun showBuffering() {
        printer(1)("Staff buffered to include Sales")
        val staff = buildData()
        staff.bufferUntil { it.dept == Sales }
            .subscribe { list ->
                printer(2)("New list of staff")
                list.forEach { emp ->
                    printer(3)("${emp.name} in ${emp.dept}")
                }
            }
    }

    fun showWindowing() {
        printer(1)("Staff windowed to include Sales")
        val staff = buildData()
        staff.windowUntil { it.dept == Sales }
            .doOnEach { printer(2)("New window of staff") }
            .flatMap { it }
            .subscribe { emp ->
                printer(3)("${emp.name} in ${emp.dept}")
            }
    }

    printTitle("Batching items via groups, windows and buffers")
    showGrouping()
    showBuffering()
    showWindowing()
}

fun showBlockingOperators() {
    printTitle("Introducing blocking")
    val printMsg = printer(1)

    val first = buildData()
        .map { it.name }
        .blockFirst()
    printMsg("First employee was $first")

    val last = buildData()
        .map { it.name }
        .blockLast()
    printMsg("Last employee was $last")

    val nameIterator = buildData()
        .map { it.name }
        .toIterable()
        .iterator()
    nameIterator.next()
    printMsg("Second employee was ${nameIterator.next()}")

    val firstInSales = buildData()
        .toStream()
        .filter { it.dept == Sales }
        .findFirst()
        .map { it.name.toString() }
        .orElse("Arthur Daley")
    printMsg("First employee from Sales was $firstInSales")
}

fun showCombiningOperators() {
    fun showThen() {
        printer(1)("Using the 'then' operator")
        val printMsg = printer(2)

        val mono1 = buildData()
            .filter { it.dept == Sales }
            .count()
            .doOnNext { printMsg("Sales count is $it") }

        val mono2 = buildData()
            .filter { it.dept == HR }
            .count()
            .doOnNext { printMsg("HR count is $it") }

        val mono3 = mono1.thenMany(mono2)

        mono3.subscribe { printMsg("Output of combined Mono is $it") }
    }

    fun showConcatMap() {
        printer(1)("Using the 'concatMap' operator")

        val text = "All the contacts of Sales staff: "
        buildData()
            .filter { it.dept == Sales }
            .map { it.contacts }
            .concatMap { Flux.fromIterable(it) }
            .map { it.name }
            .collectList()
            .subscribe { printer(2)(text + it) }
    }

    fun showZip() {
        printer(1)("Using the 'zip' operator")

        val salesFlux = buildData()
            .filter { it.dept == Sales }
            .map { "${it.name.first} from Sales" }

        val hrFlux = buildData()
            .filter { it.dept == HR }
            .map { "${it.name.first} from HR" }

        Flux.zip(salesFlux, hrFlux)
            .map { tuple -> "${tuple.t1} and ${tuple.t2}" }
            .subscribe(printer(2))
    }

    fun showMerge() {
        printer(1)("Using the 'merge' operator")
        val pool = Executors.newFixedThreadPool(2)
        val scheduler = Schedulers.fromExecutor(pool)

        val salesFlux = buildData()
            .filter { it.dept == Sales }
            .map { "${it.name.first} from Sales" }
            .delayElements(Duration.ofMillis(100))
            .parallel()
            .runOn(scheduler)

        val hrFlux = buildData()
            .filter { it.dept == HR }
            .map { "${it.name.first} from HR" }
            .delayElements(Duration.ofMillis(100))
            .parallel()
            .runOn(scheduler)

        val flux = Flux.merge(salesFlux, hrFlux)
        flux.doOnNext(printer(2))
            .blockLast()

        pool.shutdown()
        if (!pool.awaitTermination(10, TimeUnit.SECONDS)) {
            throw IllegalStateException("Thread pool failed to finish")
        }
    }

    printTitle("Combining different Fluxes")
    showThen()
    showConcatMap()
    showZip()
    showMerge()
}

fun printTitle(title: String) = println("\n---------- $title ----------")

fun printer(level: Int): (Any) -> Unit = { info ->
    val indent = "\t".repeat(level)
    println("$indent$info")
}

fun buildData(): Flux<Employee> {
    val path = Paths.get("./data/staff.json")
    val mapper = ObjectMapper()
    val type = mapper.typeFactory
        .constructCollectionType(List::class.java, Employee::class.java)

    val staff = mapper.readValue<List<Employee>>(path.toFile(), type)
    return Flux.fromIterable(staff)
}
