package com.example.livingtogether.data.offlinerepository

import com.example.livingtogether.data.datasource.UsersHouseworkRemoteDataSource
import com.example.livingtogether.data.model.UsersHousework
import com.example.livingtogether.domain.repository.UsersHouseworkRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date

class OfflineUsersHouseworkRepository(private val usersHouseworkRemoteDataSource: UsersHouseworkRemoteDataSource) :
    UsersHouseworkRepository {
    override fun getUsersHouseworkListFlow(
        currentUserId: String,
        date: Date
    ): Flow<List<UsersHousework>> {
        return usersHouseworkRemoteDataSource.getUsersHouseworkListFlow(currentUserId, date)
    }

    override suspend fun getUsersHouseworkItem(usersHouseworkId: String): UsersHousework? {
        return usersHouseworkRemoteDataSource.getUsersHouseworkItem(usersHouseworkId)
    }

    override suspend fun createUsersHousework(usersHousework: UsersHousework) {
        usersHouseworkRemoteDataSource.createUsersHousework(usersHousework)
    }

    override suspend fun updateUsersHousework(usersHousework: UsersHousework) {
        usersHouseworkRemoteDataSource.updateUsersHousework(usersHousework)
    }

    override suspend fun deleteUsersHousework(usersHouseworkId: String) {
        usersHouseworkRemoteDataSource.deleteUsersHousework(usersHouseworkId)
    }

}