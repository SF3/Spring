package simpsons.shared

import reactor.core.publisher.Flux
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

const val INPUT_FILE = "./data/simpsons.txt"

fun fetchSimpsons(): Flux<String> {
    return try {
        val stream = Files.readAllLines(Paths.get(INPUT_FILE))
        Flux.fromIterable(stream)
    } catch (ex: IOException) {
        println("Cannot load input data! " + ex.message)
        Flux.empty()
    }
}

fun <T> Flux<T>.subscribeWithTitle(title: String) = subscribe(::printLine, ::errorHandler) { printTitle(title) }

fun printTitle(title: String) {
    println(title)
    println("\n-----------------------------------\n")
}

fun <T> printLine(line: T) = println("\t$line")

fun errorHandler(error: Throwable) = println("Problem with Flux: $error.message")

fun padLine(line: String, width: Int): String {
    val padding = width - line.length
    return if (padding > 0) {
        line + " ".repeat(padding)
    } else line
}
