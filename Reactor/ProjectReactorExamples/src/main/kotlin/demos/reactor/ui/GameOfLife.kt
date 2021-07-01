package demos.reactor.ui

import javax.swing.*

import java.awt.*
import java.awt.event.*

class GameOfLife : JFrame("The Game Of Life") {
    private var timer: Thread? = null
    private val board = Board(BOARD_SIZE, true)
    private val startButton = JButton("Start")
    private val stopButton = JButton("Stop")
    private val clearButton = JButton("Clear")
    private val randomizeButton = JButton("Randomize")
    private val iterationsDisplay = JTextField(5)
    private val listener: EventListener = EventListener()

    val flux = board.flux

    init {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        initComponents()
        createLayout()
        initEventHandling()
        pack()
    }

    private fun initEventHandling() {
        startButton.addActionListener(listener)
        clearButton.addActionListener(listener)
        stopButton.addActionListener(listener)
        randomizeButton.addActionListener(listener)
        board.addMouseMotionListener(listener)
        board.addMouseListener(listener)
    }

    private fun createLayout() {
        setBoardSize()
        contentPane.add(board, BorderLayout.CENTER)
        val controlsBox = buildControlsBox()
        val controlsPanel = JPanel()
        controlsPanel.add(controlsBox)
        contentPane.add(controlsPanel, BorderLayout.NORTH)
        isResizable = false
    }

    private fun setBoardSize() {
        val size = calculateBoardSize()
        board.preferredSize = Dimension(size, size)
    }

    private fun calculateBoardSize(): Int {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        val shortestScreenSize = Math.min(screenSize.getHeight(), screenSize.getWidth())
        val size = shortestScreenSize * PERCENTAGE_OF_TOTAL_SCREEN_SIZE
        val multiple = (size / BOARD_SIZE).toInt()
        return multiple * BOARD_SIZE
    }

    private fun buildControlsBox(): Box {
        val box = Box.createHorizontalBox()
        box.add(startButton)
        box.add(stopButton)
        box.add(clearButton)
        box.add(randomizeButton)
        box.add(Box.createHorizontalStrut(10))
        box.add(JLabel("Iteration: "))
        box.add(iterationsDisplay)
        return box
    }

    private fun initComponents() {
        iterationsDisplay.isEditable = false
    }

    fun resetBoard() {
        if (timer == null || !timer!!.isAlive) {
            board.clear()
            board.assignRandomCellStates()
            iterationsDisplay.text = "0"
            repaint()
        }
    }

    fun clearBoard() {
        if (timer == null || !timer!!.isAlive) {
            board.clear()
            iterationsDisplay.text = "0"
            repaint()
        }
    }

    fun stopGame() {
        if (timer != null && timer!!.isAlive) {
            timer!!.interrupt()
        }
    }

    fun startGame() {
        if (timer == null || !timer!!.isAlive) {
            timer = TimerThread()
            timer!!.start()
        } else {
            println("ERROR: ALREADY STARTED")
        }
    }

    internal fun activateCell(p: Point) {
        if (timer != null && timer!!.isAlive) {
            return
        } else {
            board.activateCellAtLocation(p)
        }
    }

    internal fun toggleCell(p: Point) {
        board.toggleCellAtLocation(p)
        repaint()
    }

    private inner class TimerThread : Thread() {
        override fun run() {
            var count = 1
            while (!isInterrupted) {
                try {
                    Thread.sleep(INTERVAL_BETWEEN_STATE_CHANGES.toLong())
                } catch (ex: InterruptedException) {
                    break
                }

                board.calculateStateChange()
                board.doStateChange()
                iterationsDisplay.text = count.toString()
                repaint()
                count++
            }
        }
    }

    internal inner class EventListener : MouseAdapter(), ActionListener, MouseMotionListener {
        override fun actionPerformed(e: ActionEvent) {
            val source = e.source
            when {
                source === startButton -> startGame()
                source === stopButton -> stopGame()
                source === clearButton -> clearBoard()
                source === randomizeButton -> resetBoard()
                else -> JOptionPane.showInternalMessageDialog(
                    contentPane,
                    "Unknown Event Triggered",
                    "Whoops!",
                    JOptionPane.WARNING_MESSAGE
                )
            }
        }

        override fun mouseDragged(e: MouseEvent?) {
            activateCell(e!!.point)
        }

        override fun mouseClicked(e: MouseEvent?) {
            toggleCell(e!!.point)
        }

        override fun mouseMoved(e: MouseEvent?) {}
    }

    companion object {
        private const val INTERVAL_BETWEEN_STATE_CHANGES = 250
        private const val PERCENTAGE_OF_TOTAL_SCREEN_SIZE = 0.80
        private const val BOARD_SIZE = 60
    }
}
