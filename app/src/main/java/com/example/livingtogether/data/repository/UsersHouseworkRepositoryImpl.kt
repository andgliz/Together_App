package com.example.livingtogether.data.repository

import com.example.livingtogether.data.datasource.UsersHouseworkRemoteDataSource
import com.example.livingtogether.data.model.UsersHousework
import com.example.livingtogether.domain.repository.UsersHouseworkRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class UsersHouseworkRepositoryImpl @Inject constructor(private val usersHouseworkRemoteDataSource: UsersHouseworkRemoteDataSource) :
    UsersHouseworkRepository {
    override fun getUsersHouseworkListFlow(
        currentUserId: String,
        date: Date
    ): Flow<List<UsersHousework>> {
        return usersHouseworkRemoteDataSource.getUsersHouseworkListFlow(currentUserId, date)
    }

    override suspend fun getUserHouseworkList(
        userId: String,
        startDate: Date,
        endDate: Date
    ): List<String> {
        return usersHouseworkRemoteDataSource.getUserHouseworkList(userId, startDate, endDate)
    }

    override suspend fun getUsersHouseworkItem(usersHouseworkId: String): UsersHousework? {
        return usersHouseworkRemoteDataSource.getUsersHouseworkItem(usersHouseworkId)
    }

    override suspend fun createUsersHousework(usersHousework: UsersHousework) {
        usersHouseworkRemoteDataSource.createUsersHousework(usersHousework)
    }

    override suspend fun deleteUsersHousework(usersHouseworkId: String) {
        usersHouseworkRemoteDataSource.deleteUsersHousework(usersHouseworkId)
    }

    override suspend fun deleteAllByUserId(userId: String) {
        usersHouseworkRemoteDataSource.deleteAllByUserId(userId)
    }
}