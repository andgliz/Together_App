package com.example.livingtogether.data



interface TogetherRepository {

    suspend fun getUsersDataStream(user: User): List<UsersHousework>

    suspend fun getHouseworkStream(): List<Housework>

    suspend fun getUsersRatingStream(): List<User>

    suspend fun insertIntoHouseworkStream(housework: Housework)

    suspend fun deleteFromHouseworkStream(housework: Housework)

    suspend fun updateHouseworkStream(housework: Housework)
}