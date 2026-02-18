package com.propertyfinder.shopr

import android.app.Application
import com.propertyfinder.shopr.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ShoprApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ShoprApplication)
            modules(appModule)
        }
    }
}
