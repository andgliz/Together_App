package com.example.livingtogether.data.repository

import com.example.livingtogether.data.datasource.HouseworkRemoteDataSource
import com.example.livingtogether.data.datasource.UsersHouseworkRemoteDataSource
import com.example.livingtogether.data.model.Housework
import com.example.livingtogether.domain.repository.HouseworkRepository
import kotlinx.coroutines.flow.Flow

class HouseworkRepositoryImpl(
    private val houseworkRemoteDataSource: HouseworkRemoteDataSource,
    private val usersHouseworkRemoteDataSource: UsersHouseworkRemoteDataSource
) :
    HouseworkRepository {
    override fun getHouseworkListFlow(currentUsersFamily: String): Flow<List<Housework>> {
        return houseworkRemoteDataSource.getHouseworkListFlow(currentUsersFamily)
    }

    override suspend fun getHouseworkList(currentUsersFamily: String): List<Housework> {
        return houseworkRemoteDataSource.getHouseworkList(currentUsersFamily)
    }

    override suspend fun getHouseworkItem(houseworkId: String): Housework? {
        return houseworkRemoteDataSource.getHouseworkItem(houseworkId)
    }

    override suspend fun createHousework(housework: Housework) {
        houseworkRemoteDataSource.createHousework(housework)
    }

    override suspend fun updateHousework(housework: Housework) {
        houseworkRemoteDataSource.updateHousework(housework)
    }

    override suspend fun deleteHousework(houseworkId: String) {
        usersHouseworkRemoteDataSource.deleteAllByHouseworkId(houseworkId)
        houseworkRemoteDataSource.deleteHousework(houseworkId)
    }
}