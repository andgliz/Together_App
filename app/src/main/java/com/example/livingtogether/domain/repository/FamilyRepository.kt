package com.example.livingtogether.domain.repository

import com.example.livingtogether.data.model.Family

interface FamilyRepository {

    suspend fun findFamily(name: String, password: String): String?

    suspend fun getFamily(familyId: String): Family?

    suspend fun createFamily(family: Family): String

    suspend fun updateFamily(family: Family)

    suspend fun deleteFamily(familyId: String)
}
