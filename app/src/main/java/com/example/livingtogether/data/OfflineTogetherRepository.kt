package com.example.livingtogether.data

class OfflineTogetherRepository(private val togetherDao: TogetherDao) : TogetherRepository {

    override suspend fun getUsersDataStream(user: User): List<UsersHousework> = togetherDao.getUsersData(user)

    override suspend fun getHouseworkStream(): List<Housework> = togetherDao.getHousework()

    override suspend fun getUsersRatingStream(): List<User> = togetherDao.getUsersRating()

    override suspend fun insertIntoHouseworkStream(housework: Housework) = togetherDao.insertIntoHousework(housework)

    override suspend fun deleteFromHouseworkStream(housework: Housework) = togetherDao.deleteFromHousework(housework)

    override suspend fun updateHouseworkStream(housework: Housework) = togetherDao.updateHousework(housework)

}