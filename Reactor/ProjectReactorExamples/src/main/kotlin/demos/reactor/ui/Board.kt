package demos.reactor.ui

import java.awt.*
import javax.swing.JPanel

import demos.reactor.ui.Cell.CellState.*
import reactor.core.publisher.Flux

class Board(private val cellSize: Int, randomizeCellStates: Boolean) : JPanel() {
    private var spacing: Int = 0
    private val cells = Array(cellSize) { Array(cellSize) { Cell() } }
    private val listeners = mutableListOf<CellEventListener>()


    val flux: Flux<CellEvent> = Flux.create { sink ->
        listeners.add(object : CellEventListener {
            override fun onCellStateChange(x: Int, y: Int, state: Boolean) {
                sink.next(CellEvent(x, y, state))
            }

            override fun onGameStop() {
                sink.complete()
            }
        })
    }

    init {
        createCells()
        setAllCellNeighbours()
        if (randomizeCellStates) {
            assignRandomCellStates()
        }
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        spacing = Math.min(size.width, size.height) / cellSize
        for (x in 0 until cells[0].size) {
            for (y in cells.indices) {
                cells[x][y].draw(x * spacing, y * spacing, spacing, g)
            }
        }
    }

    fun calculateStateChange() {
        for (x in 0 until cellSize) {
            for (y in 0 until cellSize) {
                cells[x][y].calculateStateChange()
            }
        }
    }

    fun doStateChange() {
        for (x in 0 until cellSize) {
            for (y in 0 until cellSize) {
                cells[x][y].doStateChange()
                for (listener in listeners) {
                    listener.onCellStateChange(x, y, cells[x][y].isAlive)
                }
            }
        }
    }

    fun assignRandomCellStates() {
        for (x in 0 until cellSize) {
            for (y in 0 until cellSize) {
                cells[x][y].setState(if (Math.random() < 0.33) ALIVE else DEAD)
            }
        }
    }

    fun toggleCellAtLocation(point: Point) {
        val cell = findCell(point)
        cell?.toggleState()
    }

    private fun getCellBounds(point: Point): Rectangle {
        val x = point.x / spacing
        val y = point.y / spacing
        return Rectangle(x * spacing, y * spacing, spacing, spacing)
    }

    fun activateCellAtLocation(point: Point) {
        val cell = findCell(point)
        if (!cell?.isAlive!!) {
            cell.setState(ALIVE)
            repaint(getCellBounds(point))
        }
    }

    fun clear() {
        for (x in 0 until cellSize) {
            for (y in 0 until cellSize) {
                cells[x][y].setState(DEAD)
            }
        }
    }

    private fun setAllCellNeighbours() {
        for (x in 0 until cellSize) {
            for (y in 0 until cellSize) {
                setCellNeighbours(x, y)
            }
        }
    }

    private fun setCellNeighbours(x: Int, y: Int) {
        val current = cells[x][y]
        val positionsOfNeighbours = arrayOf(
            intArrayOf(x - 1, y),     //before current
            intArrayOf(x + 1, y),     //after current
            intArrayOf(x, y - 1),     //above current
            intArrayOf(x, y + 1),     //below current
            intArrayOf(x - 1, y - 1), //top left diagonal
            intArrayOf(x + 1, y - 1), //top right diagonal
            intArrayOf(x - 1, y + 1), //bottom left diagonal
            intArrayOf(x + 1, y + 1)  //bottom right diagonal
        )
        for (i in positionsOfNeighbours.indices) {
            val position = positionsOfNeighbours[i]
            if (cellExists(position)) {
                current.addNeighbour(cells[position[0]][position[1]])
            }
        }
    }

    private fun cellExists(position: IntArray): Boolean {
        val cellRange = 0 until cellSize
        return position[0] in cellRange && position[1] in cellRange
    }

    private fun createCells() {
        for (x in 0 until cellSize) {
            for (y in 0 until cellSize) {
                cells[x][y] = Cell()
            }
        }
    }

    private fun findCell(point: Point): Cell? {
        return cells[point.x / spacing][point.y / spacing]
    }
}
