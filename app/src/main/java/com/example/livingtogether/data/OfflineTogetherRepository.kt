package com.example.livingtogether.data

class OfflineTogetherRepository(private val togetherDao: TogetherDao) : TogetherRepository {
    override suspend fun getUserStream(email: String?): User = togetherDao.getUser(email = email)

    override suspend fun insertIntoFamilyStream(family: Family) = togetherDao.insertIntoFamily(family = family)

    override suspend fun addFamilyForUserStream(family: Family?, email: String?) = togetherDao.addFamilyForUser(family = family, email = email)

    override suspend fun getFamilyDataStream(familyName: String): List<Family> = togetherDao.getFamilyData(familyName = familyName)

    override suspend fun getUsersDataStream(user: User): List<UsersHousework> = togetherDao.getUsersData(user = user)

    override suspend fun getHouseworkStream(): List<Housework> = togetherDao.getHousework()

    override suspend fun getUsersRatingStream(): List<User> = togetherDao.getUsersRating()

    override suspend fun insertIntoUserStream(user: User) = togetherDao.insertIntoUser(user = user)

    override suspend fun insertIntoHouseworkStream(housework: Housework) = togetherDao.insertIntoHousework(housework = housework)

    override suspend fun deleteFromHouseworkStream(housework: Housework) = togetherDao.deleteFromHousework(housework = housework)

    override suspend fun updateHouseworkStream(housework: Housework) = togetherDao.updateHousework(housework = housework)

}