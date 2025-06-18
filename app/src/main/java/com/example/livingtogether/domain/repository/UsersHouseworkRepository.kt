package com.example.livingtogether.domain.repository

import com.example.livingtogether.data.model.UsersHousework
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface UsersHouseworkRepository {
    fun getUsersHouseworkListFlow(currentUserId: String, date: Date) : Flow<List<UsersHousework>>

    suspend fun getUserHouseworkList(userId: String, startDate: Date, endDate: Date): List<String>

    suspend fun getUsersHouseworkItem(usersHouseworkId: String): UsersHousework?

    suspend fun createUsersHousework(usersHousework: UsersHousework)

    suspend fun deleteUsersHousework(usersHouseworkId: String)
}