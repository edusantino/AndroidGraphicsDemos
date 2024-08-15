package com.santino.mytechdemos.demos.fallingsands

import android.graphics.Color
import android.graphics.Paint

class Sand(
    var posX: Float = 0.0f,
    var posY: Float = 0.0f,
    var isMoving: Boolean = false,
    val size: Float = 0.0f,
    var matX: Int = 0,
    var matY: Int = 0
) {
    val paint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
    }

    fun moveDown() {
        posY += size
        matY += 1
    }

    fun moveLeft() {
        moveDown()
        posX -= size
        matX -= 1
    }

    fun moveRight() {
        moveDown()
        posX += size
        matX += 1
    }
}