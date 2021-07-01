package demos.reactor.ui

interface CellEventListener {
    fun onCellStateChange(x: Int, y: Int, state: Boolean)
    fun onGameStop()
}