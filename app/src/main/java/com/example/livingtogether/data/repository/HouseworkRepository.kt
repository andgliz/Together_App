package com.example.livingtogether.data.repository

import com.example.livingtogether.data.model.Housework
import kotlinx.coroutines.flow.Flow

interface HouseworkRepository {
    fun getHouseworkListFlow(currentUsersFamily: String) : Flow<List<Housework>>

    suspend fun getHouseworkItem(houseworkId: String): Housework?

    suspend fun createHousework(housework: Housework)

    suspend fun updateHousework(housework: Housework)

    suspend fun deleteHousework(houseworkId: String)
}