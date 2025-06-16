package com.example.livingtogether.data.offlinerepository

import com.example.livingtogether.data.datasource.FamilyRemoteDataSource
import com.example.livingtogether.data.model.Family
import com.example.livingtogether.domain.repository.FamilyRepository

class OfflineFamilyRepository(private val familyRemoteDataSource: FamilyRemoteDataSource) :
    FamilyRepository {
    override suspend fun findFamily(name: String, password: String): String {
        return familyRemoteDataSource.findFamily(name = name, password = password)
    }

    override suspend fun createFamily(family: Family): String {
        return familyRemoteDataSource.createFamily(family)
    }

    override suspend fun updateFamily(family: Family) {
        familyRemoteDataSource.updateFamily(family)
    }

    override suspend fun deleteFamily(familyId: String) {
        familyRemoteDataSource.deleteFamily(familyId)
    }
}
