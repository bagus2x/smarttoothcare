package com.psi.smarttoothcare

import android.app.Application
import timber.log.Timber

@Suppress("unused")
class STCApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}