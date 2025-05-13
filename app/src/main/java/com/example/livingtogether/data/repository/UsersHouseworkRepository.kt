package com.example.livingtogether.data.repository

import com.example.livingtogether.data.model.UsersHousework
import kotlinx.coroutines.flow.Flow

interface UsersHouseworkRepository {
    fun getUsersHouseworkListFlow(currentUserId: String) : Flow<List<UsersHousework>>

    suspend fun getUsersHouseworkItem(usersHouseworkId: String): UsersHousework?

    suspend fun createUsersHousework(usersHousework: UsersHousework)

    suspend fun updateUsersHousework(usersHousework: UsersHousework)

    suspend fun deleteUsersHousework(usersHouseworkId: String)
}