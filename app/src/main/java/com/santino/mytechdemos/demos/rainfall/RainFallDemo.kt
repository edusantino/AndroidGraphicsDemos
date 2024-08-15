package com.santino.mytechdemos.demos.rainfall

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.Choreographer
import android.view.View
import kotlin.random.Random.Default.nextDouble

class RainFallDemo(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs), Choreographer.FrameCallback {
    var counter: Int = 0
    var rainDirection = 30.0f
    var rainingIntensity = 3
    private var timer = 100L
    private val spawnInterval = 100L
    private val drops: MutableList<Drop> = mutableListOf()

    init {
        // Start animation loop
        Choreographer.getInstance().postFrameCallback(this)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        timer -= System.currentTimeMillis()
        if (timer < 0) {
            timer = spawnInterval
            drops.add(Drop(dropTailSize = nextDouble(1.0, 10.0).toFloat(), posX = nextDouble(0.0, width.toDouble()), fallSpeed = nextDouble(3.0, 5.0).toFloat()))
        }

        val iterator = drops.iterator()

        while (iterator.hasNext()) {
            val drop = iterator.next()
            drop.updatePos(height) { reachBottom ->
                if (reachBottom) {
                    iterator.remove()
                }
            }
            updateScene(drop, canvas)
        }
    }

    private fun updateScene(drop: Drop, canvas: Canvas) =
        canvas.drawLine(
            drop.posX.toFloat(), drop.posY,
            drop.posX.toFloat(),
            (drop.posY + drop.dropTailSize),
            drop.paint
        )

    override fun doFrame(frameTimeNanos: Long) {
        // Request frame update
        invalidate()

        // Schedule next frame to keep 60 FPS
        Choreographer.getInstance().postFrameCallbackDelayed(this, (1000 / 60).toLong())
    }
}
