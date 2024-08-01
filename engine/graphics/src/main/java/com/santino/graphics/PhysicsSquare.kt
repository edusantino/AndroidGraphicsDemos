package com.santino.mytechdemos.engine

import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

abstract class PhysicsObject(var x: Float, var y: Float) {
    var velocityX: Float = 0f
    var velocityY: Float = 0f
    var gravity: Float = -9.8f

    abstract fun update(deltaTime: Float)
    abstract fun draw()
}

class PhysicsSquare(x: Float, y: Float, private val size: Float) : PhysicsObject(x, y) {

    override fun update(deltaTime: Float) {
        velocityY += gravity * deltaTime
        y += velocityY * deltaTime

        if (y < -1.0f + size) {
            y = -1.0f + size
            velocityY = 0f
        }
    }

    override fun draw() {
        val halfSize = size / 2
        val vertexBuffer = floatArrayOf(
            x - halfSize, y + halfSize,  // Top Left
            x - halfSize, y - halfSize,  // Bottom Left
            x + halfSize, y - halfSize,  // Bottom Right
            x + halfSize, y + halfSize   // Top Right
        ).toBuffer()

        val positionHandle = GLES20.glGetAttribLocation(program, "vPosition")
        GLES20.glEnableVertexAttribArray(positionHandle)
        GLES20.glVertexAttribPointer(positionHandle, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer)

        val colorHandle = GLES20.glGetUniformLocation(program, "vColor")
        GLES20.glUniform4fv(colorHandle, 1, floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f), 0)

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4)
        GLES20.glDisableVertexAttribArray(positionHandle)
    }

    companion object {
        var program: Int = 0
    }
}

class PhysicsEngine {
    private val objects = mutableListOf<PhysicsObject>()

    fun addObject(obj: PhysicsObject) {
        objects.add(obj)
    }

    fun update(deltaTime: Float) {
        for (obj in objects) {
            obj.update(deltaTime)
        }
    }

    fun draw() {
        for (obj in objects) {
            obj.draw()
        }
    }
}

private fun FloatArray.toBuffer(): FloatBuffer {
    return ByteBuffer.allocateDirect(this.size * 4).run {
        order(ByteOrder.nativeOrder())
        asFloatBuffer().apply {
            put(this@toBuffer)
            position(0)
        }
    }
}