package demos.reactor.threading.shared

import java.text.DecimalFormat

fun threadId() = Thread.currentThread().id

fun printTabbed(str: String) = println("\t$str")

fun randomValue(): String {
    val df = DecimalFormat("00.00")
    return df.format(Math.random() * 100)
}
