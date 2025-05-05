package com.example.livingtogether.data.repository

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val currentUser: FirebaseUser?

    val currentUserIdFlow: Flow<String?>

    suspend fun signUp(email: String, password: String): String

    suspend fun signIn(email: String, password: String): String

    fun signOut()

    suspend fun deleteAccount()
}