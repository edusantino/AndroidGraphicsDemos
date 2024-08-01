package com.santino.filereader

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

class ShaderFileReader {
    fun readShaderFileFromRawResource(context: Context, resourceId: Int): Pair<String, String> {
        val sb = StringBuilder()
        val inputStream = context.resources.openRawResource(resourceId)
        val reader = BufferedReader(InputStreamReader(inputStream))
        var line: String? = reader.readLine()
        while (line != null) {
            sb.append(line).append("\n")
            line = reader.readLine()
        }
        reader.close()

        val shaderCode = sb.toString()
        val vertexShaderCode = shaderCode.substringBefore("// Fragment Shader").trim()
        val fragmentShaderCode = shaderCode.substringAfter("// Fragment Shader").trim()

        return Pair(vertexShaderCode, fragmentShaderCode)
    }
}