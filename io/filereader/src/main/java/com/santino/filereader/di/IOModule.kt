package com.santino.filereader.di

import com.santino.filereader.ShaderFileReaderImpl
import com.santino.filereader.ShaderReader
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class IOModule {
    companion object {
        val koin = module {
            factory<ShaderReader> {
                ShaderFileReaderImpl(androidContext())
            }
        }
    }
}