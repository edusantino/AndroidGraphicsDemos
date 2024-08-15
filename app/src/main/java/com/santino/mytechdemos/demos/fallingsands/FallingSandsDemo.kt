package com.santino.mytechdemos.demos.fallingsands

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import kotlin.math.abs
import kotlin.math.floor

class FallingSandsDemo(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val sands: MutableList<Sand> = mutableListOf()
    private var rows = 0
    private var lines = 0
    private var occupancyMatrix: Array<Array<Boolean>> = arrayOf()

    private val updateInterval = 100L
    private var lastUpdateTime = System.currentTimeMillis()

    private lateinit var gridBitmap: Bitmap
    private lateinit var gridCanvas: Canvas
    private val gridSize = 50
    private val margin = 40 // start (20) + end (20)

    private var startX = 0
    private var startY = 0

    private val gridPaint = Paint().apply {
        color = Color.LTGRAY
        strokeWidth = 2f
    }

    init {
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (width > 0 && height > 0 && !::gridBitmap.isInitialized) {
                    createGridBitmap()
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            }
        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Draw grid
        if (::gridBitmap.isInitialized) {
            canvas.drawBitmap(gridBitmap, 0f, 0f, null)
        }

        drawSands(canvas)

        val currentTime = System.currentTimeMillis()
        if (currentTime - lastUpdateTime >= updateInterval) {
            lastUpdateTime = currentTime
            updateScene()
        }
        invalidate()
    }

    private fun drawSands(canvas: Canvas) {
        for (sand in sands) {
            canvas.drawRect(sand.posX, sand.posY, sand.posX + sand.size, sand.posY + sand.size, sand.paint)
        }
    }

    private fun updateScene() {
        val movingSands = sands.filter { it.isMoving }
        if (movingSands.isNotEmpty()) {
            for (sand in movingSands) {
                if (isBelowOrOutOfBounds(sand)) {
                    if (canMoveLeft(sand)) {
                        occupancyMatrix[sand.matX][sand.matY] = false
                        sand.moveLeft()
                        occupancyMatrix[sand.matX][sand.matY] = true
                    } else if (canMoveRight(sand)) {
                        occupancyMatrix[sand.matX][sand.matY] = false
                        sand.moveRight()
                        occupancyMatrix[sand.matX][sand.matY] = true
                    } else {
                        sand.isMoving = false
                    }
                } else {
                    occupancyMatrix[sand.matX][sand.matY] = false
                    sand.moveDown()
                    occupancyMatrix[sand.matX][sand.matY] = true
                }
            }
        }
    }

    private fun isBelowOrOutOfBounds(sand: Sand) =
        sand.matY + 1 >= lines || occupancyMatrix[sand.matX][sand.matY + 1]

    private fun canMoveLeft(sand: Sand) =
        sand.matY + 1 < lines && sand.matX - 1 >= 0 && !occupancyMatrix[sand.matX - 1][sand.matY + 1]

    private fun canMoveRight(sand: Sand) =
        sand.matY + 1 < lines && sand.matX + 1 < rows && !occupancyMatrix[sand.matX + 1][sand.matY + 1]

    private fun getMatrixPosition(x: Int, y: Int): Pair<Int, Int> {
        val dx = x - startX
        val dy = y - startY
        val squareXPosition = (dx / gridSize)
        val squareYPosition = (dy / gridSize)
        return Pair(squareXPosition, squareYPosition)
    }

    private fun createGridBitmap() {
        gridBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        gridCanvas = Canvas(gridBitmap)

        // Resize when screen rotate
        if (width > height) {
            rows = (height - margin) / gridSize
            lines = (height - margin) / gridSize
        } else {
            rows = (width - margin) / gridSize
            lines = (width - margin) / gridSize
        }

        // Initiate array dimensions after screen is created
        occupancyMatrix = Array(rows) { Array(lines) { false } }

        // Calculate grid size
        val totalGridWidth = rows * gridSize
        val totalGridHeight = lines * gridSize

        // Calculate initial point (top left of grid)
        startX = (width / 2) - (totalGridWidth / 2)
        startY = (height / 2) - (totalGridHeight / 2)

        // Draw horizontal lines
        for (l in 0..lines) {
            val y = startY + l * gridSize
            gridCanvas.drawLine(startX.toFloat(), y.toFloat(), (startX + totalGridWidth).toFloat(), y.toFloat(), gridPaint)
        }

        // Draw vertical lines
        for (r in 0..rows) {
            val x = startX + r * gridSize
            gridCanvas.drawLine(x.toFloat(), startY.toFloat(), x.toFloat(), (startY + totalGridHeight).toFloat(), gridPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = floor((abs(startX - event.x.toDouble())) / gridSize)
        val y = floor((abs(startY - event.y.toDouble())) / gridSize)

        val currentPositionInMatrix = getMatrixPosition(event.x.toInt(), event.y.toInt())

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                sands.add(
                    Sand(
                        posX = (startX + x * gridSize).toFloat(),
                        posY = (startY + y * gridSize).toFloat(),
                        isMoving = true,
                        size = gridSize.toFloat(),
                        matX = currentPositionInMatrix.first,
                        matY = currentPositionInMatrix.second
                    )
                )
                occupancyMatrix[currentPositionInMatrix.first][currentPositionInMatrix.second] = true

            }

            MotionEvent.ACTION_MOVE -> {
                // handleTouchMove(x, y)
            }

            MotionEvent.ACTION_UP -> {
                // handleTouchUp(x, y)
            }
        }
        return true
    }
}