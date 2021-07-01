package demos.reactor.ui

fun main() {
    val gui = GameOfLife()
    gui.isVisible = true

    gui.flux.filter { event -> event.alive }
        .map { event -> "Cell at ${event.x}, ${event.y}" }
        .subscribe(::println)
}
