package com.psi.smarttoothcare

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@Suppress("unused")
@HiltAndroidApp
class STCApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}