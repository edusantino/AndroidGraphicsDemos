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
    var rainingIntensity = 3
    var posY = 0.0f

    private val paint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
    }

    init {
        // Start animation loop
        Choreographer.getInstance().postFrameCallback(this)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        /*for (d in drops) {
            canvas.drawLines()
        }*/

        // Update position
        posY += 5 // Move 5px per frame

        // Draw circle
        val radius = 100f
        val cx = width / 2f
        canvas.drawCircle(cx, posY, radius, paint)

        // Check if circle is out of boundaries
        if (posY - radius > height) {
            posY = -radius
        }
    }

    override fun doFrame(frameTimeNanos: Long) {
        // Request frame update
        invalidate()

        // Schedule next frame to keep 24 FPS
        Choreographer.getInstance().postFrameCallbackDelayed(this, (1000 / 24).toLong())
    }
}

class Drop {
    val dropFallSpeed: Float = 3.0f
    val dropTailSize: Float = 3.0f
    val dropColor = Color.WHITE
    val fallDirection: Float = 30.0f
    var posY: Float = 0.0f
    val posX: Double = nextDouble(0.0, 10.0)
}
