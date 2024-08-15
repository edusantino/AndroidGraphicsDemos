package com.santino.mytechdemos.demos.fireworks

import android.content.Context
import android.util.AttributeSet
import android.view.Choreographer
import android.view.View

class FireworksDemo(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs), Choreographer.FrameCallback {

    init {
        // Start animation loop
        Choreographer.getInstance().postFrameCallback(this)
    }

    override fun doFrame(p0: Long) {
        // Request frame update
        invalidate()

        // Schedule next frame to keep 60 FPS
        Choreographer.getInstance().postFrameCallbackDelayed(this, (1000 / 60).toLong())
    }

}