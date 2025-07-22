package com.example.livingtogether.data.repository

import com.example.livingtogether.data.datasource.AuthRemoteDataSource
import com.example.livingtogether.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(private val authRemoteDataSource: AuthRemoteDataSource) :
    AuthRepository {
    override val currentUser: FirebaseUser? get() = authRemoteDataSource.currentUser

    override val currentUserIdFlow: Flow<String?> get() = authRemoteDataSource.currentUserIdFlow

    override suspend fun signUp(email: String, password: String): String {
        return authRemoteDataSource.signUp(email, password)
    }

    override suspend fun signIn(email: String, password: String): String {
        return authRemoteDataSource.signIn(email, password)
    }

    override fun signOut() {
        authRemoteDataSource.signOut()
    }

    override suspend fun deleteAccount() {
        authRemoteDataSource.deleteAccount()
    }
}
