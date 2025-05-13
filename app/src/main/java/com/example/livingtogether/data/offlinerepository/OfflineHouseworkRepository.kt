package com.example.livingtogether.data.offlinerepository

import com.example.livingtogether.data.datasource.HouseworkRemoteDataSource
import com.example.livingtogether.data.model.Housework
import com.example.livingtogether.data.repository.HouseworkRepository
import kotlinx.coroutines.flow.Flow

class OfflineHouseworkRepository(private val houseworkRemoteDataSource: HouseworkRemoteDataSource) :
    HouseworkRepository {
    override fun getHouseworkListFlow(currentUsersFamily: String): Flow<List<Housework>> {
        return houseworkRemoteDataSource.getHouseworkListFlow(currentUsersFamily)
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
        houseworkRemoteDataSource.deleteHousework(houseworkId)
    }

}