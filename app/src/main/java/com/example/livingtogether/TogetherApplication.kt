package com.example.livingtogether

import android.app.Application
import com.example.livingtogether.di.AppComponent
import com.example.livingtogether.di.DaggerAppComponent
import com.example.livingtogether.di.FirebaseModule
import com.example.livingtogether.di.RepositoryModule

class TogetherApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .firebaseModule(FirebaseModule())
            .repositoryModule(RepositoryModule())
            .build()

        appComponent.inject(this)
    }
}
