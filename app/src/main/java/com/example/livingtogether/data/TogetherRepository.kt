package com.example.livingtogether.data



interface TogetherRepository {

    suspend fun getUserStream(email: String?): User

    suspend fun insertIntoFamilyStream(family: Family)

    suspend fun addFamilyForUserStream(family: Family?, email: String?)

    suspend fun getFamilyDataStream(familyName:String, inputPassword: String) : Family?

    suspend fun getUsersDataStream(user: User): List<UsersHousework>

    suspend fun getHouseworkStream(family: Family): List<Housework>

    suspend fun getUsersRatingStream(family: Family): List<User>

    suspend fun insertIntoUserStream(user: User)

    suspend fun insertIntoHouseworkStream(housework: Housework)

    suspend fun deleteFromHouseworkStream(housework: Housework)

    suspend fun updateHouseworkStream(housework: Housework)
}