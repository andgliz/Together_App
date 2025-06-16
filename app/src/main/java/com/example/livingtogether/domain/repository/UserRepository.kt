package com.example.livingtogether.domain.repository

import com.example.livingtogether.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getAllFromFamilyFlow(currentUsersFamily: String) : Flow<List<User>>

    fun getUserFlow(currentUserId: String): Flow<User?>

    suspend fun getAllFromFamily(currentUsersFamily: String): List<User>

    suspend fun getUser(currentUserId: String): User?

    suspend fun createUser(user: User)

    suspend fun updateUser(user: User)

    suspend fun deleteUser(userId: String)
}
