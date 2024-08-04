package com.santino.mytechdemos.demos

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.Choreographer
import android.view.View
import kotlin.random.Random.Default.nextDouble

class Raining(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs), Choreographer.FrameCallback {
    var counter: Int = 0
    var rainingIntensity = 3
    var timer = 100L
    val spawnInterval = 100L
    val drops: MutableList<Drop> = mutableListOf()

    init {
        // Start animation loop
        Choreographer.getInstance().postFrameCallback(this)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        timer -= System.currentTimeMillis()
        if (timer < 0) {
            timer = spawnInterval
            drops.add(Drop(posX = nextDouble(0.0, width.toDouble())))
        }

        for (d in drops) {
            d.updatePos(height) {
                //drops.remove(d)
            }
            canvas.drawLine(d.posX.toFloat(), d.posY, d.posX.toFloat(), (d.posY + d.dropTailSize), d.paint)
        }
    }

    override fun doFrame(frameTimeNanos: Long) {
        // Request frame update
        invalidate()

        // Schedule next frame to keep 24 FPS
        Choreographer.getInstance().postFrameCallbackDelayed(this, (1000 / 24).toLong())
    }
}

class Drop(
    val posX: Double = 0.0,
    val fallSpeed: Float = 5.0f
) {
    val dropTailSize: Float = 3.0f
    val fallDirection: Float = 30.0f
    var posY: Float = 0.0f

    val paint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
    }

    fun updatePos(height: Int, onReachBoard: () -> Unit) {
        if (posY < height) {
            onReachBoard.invoke()
        }
        posY += fallSpeed
    }
}
