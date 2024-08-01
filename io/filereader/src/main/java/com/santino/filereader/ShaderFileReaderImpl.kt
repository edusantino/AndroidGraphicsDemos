package com.santino.filereader

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class ShaderFileReaderImpl(private val context: Context) : ShaderReader {
    override fun readFile(resourceId: Int): Pair<String, String>? {
        return try {
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

            Pair(vertexShaderCode, fragmentShaderCode)
        } catch (io: IOException) {
            io.printStackTrace()
            null
        }
    }
}