package com.santino.mytechdemos

import android.app.Application
import com.santino.filereader.di.IOModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(IOModule.koin)
        }
    }
}