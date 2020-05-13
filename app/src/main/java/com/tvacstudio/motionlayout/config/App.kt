package com.tvacstudio.motionlayout.config

import android.app.Application
import com.tvacstudio.motionlayout.api.NetworkModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        NetworkModule.init()

    }

}