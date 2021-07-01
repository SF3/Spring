package simpsons.end

import reactor.core.publisher.Flux
import simpsons.shared.*
import simpsons.shared.Simpsons.*
import java.util.function.Consumer

val haircutRegex = "^ *\\(#+\\) *$".toRegex()

fun main() {
    simpsonsFamily()
    margeHaircut()
    homerAtMoes()
    homerIsUpstairsV1()
    homerIsUpstairsV2()
    simpsonsClonedV1()
    simpsonsClonedV2()
    homerIsOnTheRightV1()
    homerIsOnTheRightV2()
    simpsonsStackedV1()
    simpsonsStackedV2()
    simpsonsStackedV3()
}

private fun simpsonsFamily() {
    fetchSimpsons().subscribeWithTitle("The Simpsons Family")
}

private fun margeHaircut() {
    val flux = fetchSimpsons()
    flux.filter { !it.matches(haircutRegex) }
        .subscribeWithTitle("Marge After Haircut")
}

private fun homerAtMoes() {
    val flux = fetchSimpsons()
    flux.map { it.substring(11) }
        .subscribeWithTitle("Homer is at Moes")
}

private fun homerIsUpstairsV1() {
    val flux = fetchSimpsons()
    flux.filter { !it.matches(haircutRegex) }
        .map { it.substring(0, 12) }
        .subscribe(::printLine)

    flux.map { it.substring(11) }
        .subscribeWithTitle("Homer is upstairs V1")
}

private fun homerIsUpstairsV2() {
    val simpsons = fetchSimpsons()
    val homer = simpsons
        .filter { !it.matches(haircutRegex) }
        .map { it.substring(0, 12) }

    val family = simpsons
        .map { it.substring(11) }

    homer.concatWith(family)
        .subscribeWithTitle("Homer is upstairs V2")
}

private fun simpsonsClonedV1() {
    val lineWidth = 80

    fetchSimpsons()
        .map { "${padLine(it, lineWidth)} ${it}" }
        .subscribeWithTitle("The Simpsons cloned V1")
}

private fun simpsonsClonedV2() {
    val flux = fetchSimpsons()
    flux.zipWith(fetchSimpsons())
        .map { tuple ->
            "${padLine(tuple.t1, 80)} ${tuple.t2}"
        }
        .subscribeWithTitle("The Simpsons cloned V2")
}

private fun homerIsOnTheRightV1() {
    val homerWidth = 14
    val data = Pair(mutableListOf<String>(), mutableListOf<String>())

    fetchSimpsons()
        .collect({ data }) { container, line ->
            container.first.add(line.drop(homerWidth))
            container.second.add(line.take(homerWidth))
        }
        .subscribe { container ->
            val longestLine = container.first.maxOf(String::length)
            Flux.range(0, container.first.size)
                .subscribe {
                    val start = padLine(container.first[it], longestLine)
                    val end = container.second[it]
                    printLine(start + end)
                }
        }

    printTitle("Homer is on the right V1")
}

//Simpler alternative if we know the max line width
private fun homerIsOnTheRightV2() {
    val homerWidth = 14
    val lineWidth = 80

    fetchSimpsons()
        .map { padLine(it, lineWidth) }
        .map { "${it.drop(homerWidth)} ${it.take(homerWidth)}" }
        .subscribeWithTitle("Homer is on the right V2")
}

private fun simpsonsStackedV1() {
    fun tokenizeLine(line: String): Map<Simpsons, String> = mapOf(
        Homer to line.safeSubstring(0..12),
        Marge to line.safeSubstring(12..28),
        Bart to line.safeSubstring(28..42),
        Liza to line.safeSubstring(42..60),
        Maggie to line.safeSubstring(60)
    )

    fun simpsonsSplit() = fetchSimpsons()
        .filter { !it.matches(haircutRegex) }
        .map(::tokenizeLine)

    val tabulatedText = simpsonsSplit()
        .collectList()
        .map { it.toList() }
        .block()

    Flux.fromArray(Simpsons.values())
        .map { name -> Pair(name, tabulatedText) }
        .flatMapIterable { (name, tables) -> tables?.map { it[name] } }
        .subscribeWithTitle("Simpsons stacked V1")
}

private fun simpsonsStackedV2() {
    fun tokenizeLine(line: String): Flux<Pair<Int, String>> = Flux.create { sink ->
        fun String.buildPair(s: Simpsons, r: IntRange) = Pair(s.ordinal, this.safeSubstring(r))
        fun String.buildPair(s: Simpsons, i: Int) = Pair(s.ordinal, this.safeSubstring(i))

        with(sink) {
            next(line.buildPair(Homer, 0..12))
            next(line.buildPair(Marge, 12..28))
            next(line.buildPair(Bart, 28..42))
            next(line.buildPair(Liza, 42..60))
            next(line.buildPair(Maggie, 60))
            complete()
        }
    }

    fetchSimpsons()
        .filter { !it.matches(haircutRegex) }
        .flatMap(::tokenizeLine)
        .groupBy { it.first }
        .flatMap { group -> group.reduce("") { a, b -> "$a\n${b.second}" } }
        .subscribeWithTitle("Simpsons stacked V2")
}

private fun simpsonsStackedV3() {
    val homerWidth = 14
    val margeWidth = 16
    val bartWidth = 14
    val lisaWidth = 17
    val maggieWidth = 16

    val simpsons = fetchSimpsons().filter { !haircutRegex.matches(it) }

    fun extractCharacter(start: Int, width: Int) =
        simpsons
            .map { it.drop(start).take(width) }
            .filter(String::isNotBlank)

    fun Flux<Int>.sumWithValue() =
        scan(Pair(0, 0)) { (previousTotal, previousWidth), nextWidth ->
            Pair(previousTotal + previousWidth, nextWidth)
        }.skip(1)

    Flux.just(homerWidth, margeWidth, bartWidth, lisaWidth, maggieWidth)
        .sumWithValue()
        .concatMap { (start, width) -> extractCharacter(start, width) }
        .subscribeWithTitle("Simpsons stacked V3")
}

fun String.safeSubstring(range: IntRange): String {
    val length = this.length
    return if (range.first < length && range.last <= length) {
        this.substring(range.first, range.last)
    } else ""
}

fun String.safeSubstring(start: Int): String {
    return if (start < this.length) {
        this.substring(start)
    } else ""
}
