package com.example.livingtogether.data.local

class OfflineTogetherRepository(private val togetherDao: TogetherDao) : TogetherRepository {
    override suspend fun getUserStream(email: String?): User = togetherDao.getUser(email = email)

    override suspend fun insertIntoFamilyStream(family: Family) =
        togetherDao.insertIntoFamily(family = family)

    override suspend fun addFamilyForUserStream(family: Family?, email: String?) =
        togetherDao.addFamilyForUser(family = family, email = email)

    override suspend fun getFamilyDataStream(
        familyName: String,
        inputPassword: String
    ): Family? =
        togetherDao.getFamilyData(familyName = familyName, inputPassword = inputPassword)

    override suspend fun getUsersDataStream(user: User): List<UsersHousework> =
        togetherDao.getUsersData(user = user)

    override suspend fun getHouseworkStream(family: Family): List<Housework> =
        togetherDao.getHousework(family = family)

    override suspend fun getUsersRatingStream(family: Family): List<User> =
        togetherDao.getUsersRating(family = family)

    override suspend fun insertIntoUserStream(user: User) = togetherDao.insertIntoUser(user = user)

    override suspend fun insertIntoHouseworkStream(housework: Housework) =
        togetherDao.insertIntoHousework(housework = housework)

    override suspend fun deleteFromHouseworkStream(housework: Housework) =
        togetherDao.deleteFromHousework(housework = housework)

    override suspend fun updateHouseworkStream(housework: Housework) =
        togetherDao.updateHousework(housework = housework)

}