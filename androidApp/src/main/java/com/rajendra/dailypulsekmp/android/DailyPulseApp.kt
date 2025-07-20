package com.rajendra.dailypulsekmp.android

import android.app.Application
import com.rajendra.dailypulsekmp.android.di.viewmodelModule
import com.rajendra.dailypulsekmp.di.sharedKotlinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DailyPulseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        val modules = sharedKotlinModule + viewmodelModule

        startKoin {
            androidContext(this@DailyPulseApp)
            modules(modules)
        }
    }
}