package elections.shared

import com.fasterxml.jackson.databind.ObjectMapper
import reactor.core.publisher.Flux
import java.nio.file.Files
import java.nio.file.Paths

fun fetchResults(): Flux<String> {
    val pathToFile = Paths.get("./data/electionResults.json")
    return Flux.using(
        { Files.lines(pathToFile) },
        { Flux.fromStream(it) },
        { it.close() })
}

fun mapResult(input: String): Result {
    val mapper = ObjectMapper()
    return mapper.readValue(input, Result::class.java)
}

fun printTitle(title: String) = println("\n----- $title -----")

fun printTabbed(input: Any) = println("\t$input")
