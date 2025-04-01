package com.example.livingtogether.data

import android.content.Context

interface AppContainer {
    val togetherRepository: TogetherRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val togetherRepository: TogetherRepository by lazy {
        OfflineTogetherRepository(TogetherDatabase.getDatabase(context).togetherDao())
    }
}