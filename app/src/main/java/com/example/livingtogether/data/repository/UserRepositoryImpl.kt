package com.example.livingtogether.data.repository

import com.example.livingtogether.data.datasource.UserRemoteDataSource
import com.example.livingtogether.data.model.User
import com.example.livingtogether.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(private val userRemoteDataSource: UserRemoteDataSource) :
    UserRepository {

    override fun getAllFromFamilyFlow(currentUsersFamily: String): Flow<List<User>> {
        return userRemoteDataSource.getAllFromFamilyFlow(currentUsersFamily)
    }

    override fun getUserFlow(currentUserId: String): Flow<User?> {
        return userRemoteDataSource.getUserFlow(currentUserId)
    }

    override suspend fun getAllFromFamily(currentUsersFamily: String): List<User> {
        return userRemoteDataSource.getAllFromFamily(currentUsersFamily)
    }

    override suspend fun getUser(currentUserId: String): User? {
        return userRemoteDataSource.getUser(currentUserId)
    }

    override suspend fun createUser(user: User) {
        userRemoteDataSource.createUser(user)
    }

    override suspend fun updateUser(user: User) {
        userRemoteDataSource.updateUser(user)
    }

    override suspend fun deleteUser(userId: String) {
        userRemoteDataSource.deleteUser(userId)
    }
}
