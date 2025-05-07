package com.example.livingtogether.data.repository

import com.example.livingtogether.data.model.Family

interface FamilyRepository {

    suspend fun findFamily(name: String, password: String): String

    suspend fun createFamily(family: Family): String

    suspend fun updateFamily(family: Family)

    suspend fun deleteFamily(familyId: String)
}
