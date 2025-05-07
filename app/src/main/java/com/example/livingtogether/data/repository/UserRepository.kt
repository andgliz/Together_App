package com.example.livingtogether.data.repository

import com.example.livingtogether.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUsersFromFamilyList(currentUsersFamily: String) : Flow<List<User>>

    fun getUserFlow(currentUserId: String): Flow<User?>

    suspend fun getUser(currentUserId: String): User?

    suspend fun createUser(user: User)

    suspend fun updateUser(user: User)

    suspend fun deleteUser(userId: String)
}
