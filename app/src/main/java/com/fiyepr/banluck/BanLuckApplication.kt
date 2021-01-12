package com.fiyepr.banluck

import android.app.Application
import timber.log.Timber

class BanLuckApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}