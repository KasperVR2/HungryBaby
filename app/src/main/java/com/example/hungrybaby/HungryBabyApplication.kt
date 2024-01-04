package com.example.hungrybaby

import android.app.Application
import com.example.hungrybaby.data.AppContainer
import com.example.hungrybaby.data.DefaultAppContainer

class HungryBabyApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(context = applicationContext)
    }
}
