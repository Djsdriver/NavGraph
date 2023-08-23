package com.example.navgraph

import android.app.Application
import com.example.navgraph.di.dataModule
import com.example.navgraph.di.playlist
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(playlist, dataModule) )
        }
    }
}