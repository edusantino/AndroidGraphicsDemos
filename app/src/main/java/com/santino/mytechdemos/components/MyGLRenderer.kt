package com.santino.mytechdemos.components

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.santino.filereader.ShaderFileReader
import com.santino.mytechdemos.R
import com.santino.mytechdemos.engine.PhysicsEngine
import com.santino.mytechdemos.engine.PhysicsSquare
import org.koin.java.KoinJavaComponent.inject
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyGLRenderer(val context: Context) : GLSurfaceView.Renderer {

    private val shaderReader: ShaderFileReader by inject(ShaderFileReader::class.java)

    private var program: Int = 0
    private val physicsEngine = PhysicsEngine()
    private var lastFrameTime: Long = System.currentTimeMillis()

    override fun onSurfaceCreated(unused: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)

        val (vertexShaderCode, fragmentShaderCode) = shaderReader
            .readShaderFileFromRawResource(context, R.raw.myshader)

        val vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)
        program = GLES20.glCreateProgram().apply {
            GLES20.glAttachShader(this, vertexShader)
            GLES20.glAttachShader(this, fragmentShader)
            GLES20.glLinkProgram(this)
        }

        PhysicsSquare.program = program

        // Adiciona um objeto físico
        val square = PhysicsSquare(0f, 1f, 0.1f)
        physicsEngine.addObject(square)

        lastFrameTime = System.currentTimeMillis()
    }

    override fun onDrawFrame(unused: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        GLES20.glUseProgram(program)

        val currentTime = System.currentTimeMillis()
        val deltaTime = (currentTime - lastFrameTime) / 1000.0f
        lastFrameTime = currentTime

        physicsEngine.update(deltaTime)
        physicsEngine.draw()
    }

    override fun onSurfaceChanged(unused: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    private fun loadShader(type: Int, shaderCode: String): Int {
        return GLES20.glCreateShader(type).also { shader ->
            GLES20.glShaderSource(shader, shaderCode)
            GLES20.glCompileShader(shader)
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
}