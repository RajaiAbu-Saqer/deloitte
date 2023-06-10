package com.rajai.deloitte.base_ui

import android.app.Application
import com.rajai.deloitte.utility.ContextUtil
import com.rajai.deloitte.utility.CryptoPrefsUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppController : Application() {
    override fun onCreate() {
        super.onCreate()
        ContextUtil.init(this)
    }
}