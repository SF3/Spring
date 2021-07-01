package demos.reactor.ui

import java.awt.Color
import java.awt.Graphics

import demos.reactor.ui.Cell.CellState.ALIVE
import demos.reactor.ui.Cell.CellState.DEAD

class Cell {
    val isAlive: Boolean
        get() = state == ALIVE
    private val neighboursList = arrayListOf<Cell>()
    private var state: CellState = DEAD
    private var nextState: CellState = DEAD

    enum class CellState {
        ALIVE,
        DEAD
    }

    fun setState(newState: CellState) {
        state = newState
    }

    fun addNeighbour(neighbour: Cell) {
        neighboursList.add(neighbour)
    }

    fun calculateStateChange() {
        val aliveNeighbours = countAliveNeighbours()
        if (state == DEAD && aliveNeighbours == 3) {
            nextState = ALIVE
        } else if (state == ALIVE && (aliveNeighbours == 2 || aliveNeighbours == 3)) {
            nextState = ALIVE
        } else {
            nextState = DEAD
        }
    }

    private fun countAliveNeighbours(): Int {
        var aliveNeighbours = 0
        for (c in neighboursList) {
            if (c.state == ALIVE) {
                aliveNeighbours++
            }
        }
        return aliveNeighbours
    }

    fun doStateChange() {
        state = nextState
    }

    fun toggleState() {
        state = if (state == ALIVE) DEAD else ALIVE
    }

    fun draw(x: Int, y: Int, length: Int, g: Graphics) {
        g.color = Color.BLACK
        g.drawOval(x, y, length, length)
        if (state == ALIVE) {
            g.color = Color.BLUE
        } else {
            g.color = Color.WHITE
        }
        g.fillOval(x, y, length, length)
    }
}