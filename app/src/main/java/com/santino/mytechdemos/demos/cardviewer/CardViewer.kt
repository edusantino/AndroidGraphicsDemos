package com.santino.mytechdemos.demos.cardviewer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class CardViewer(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private var left = (width / 2) - 100f
    private var top = (height / 2) + 100f
    private var right = (width / 2) + 100f
    private var bottom = (height / 2) - 100f

    private val paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.WHITE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        invalidate()
        canvas.drawRect(left, top, right, bottom, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (x in left..right || y in top..bottom) {
                    println("$x, $y")
                    left += 10
                    top += 10
                }
            }

            MotionEvent.ACTION_MOVE -> {
            }

            MotionEvent.ACTION_UP -> {
            }
        }
        return true
    }
}