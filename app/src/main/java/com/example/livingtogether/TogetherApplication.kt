package com.example.livingtogether

import android.app.Application
import com.example.livingtogether.data.AppContainer
import com.example.livingtogether.data.AppDataContainer

class TogetherApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}