package com.example.livingtogether.data

import android.content.Context
import com.example.livingtogether.data.datasource.AuthRemoteDataSource
import com.example.livingtogether.data.offlinerepository.OfflineAuthRepository
import com.example.livingtogether.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth

interface AppContainer {
    val togetherRepository: TogetherRepository
    val authRepository: AuthRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val togetherRepository: TogetherRepository by lazy {
        OfflineTogetherRepository(TogetherDatabase.getDatabase(context).togetherDao())
    }

    override val authRepository: AuthRepository by lazy {
        OfflineAuthRepository(AuthRemoteDataSource(FirebaseAuth.getInstance()))
    }
}