package com.santino.mytechdemos.demos.rainfall

import android.graphics.Color
import android.graphics.Paint

class Drop(
    var posX: Double = 0.0,
    var fallSpeed: Float = 5.0f,
    val dropTailSize: Float
) {
    val fallDirection: Float = 30.0f
    var posY: Float = 0.0f

    val paint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
        strokeWidth = 3.0f
    }

    fun updatePos(height: Int, onReachBoard: (Boolean) -> Unit) {
        if (posY > height) {
            onReachBoard.invoke(true)
        }
        posY += fallSpeed
    }
}