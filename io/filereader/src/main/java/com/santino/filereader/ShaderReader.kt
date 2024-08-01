package com.santino.filereader

interface ShaderReader {
    fun readFile(resourceId: Int): Pair<String, String>?
}