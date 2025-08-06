package com.example.livingtogether.di

import com.example.livingtogether.data.datasource.AuthRemoteDataSource
import com.example.livingtogether.data.datasource.FamilyRemoteDataSource
import com.example.livingtogether.data.datasource.HouseworkRemoteDataSource
import com.example.livingtogether.data.datasource.UserRemoteDataSource
import com.example.livingtogether.data.datasource.UsersHouseworkRemoteDataSource
import com.example.livingtogether.data.repository.AuthRepositoryImpl
import com.example.livingtogether.data.repository.FamilyRepositoryImpl
import com.example.livingtogether.data.repository.HouseworkRepositoryImpl
import com.example.livingtogether.data.repository.UserRepositoryImpl
import com.example.livingtogether.data.repository.UsersHouseworkRepositoryImpl
import com.example.livingtogether.domain.repository.AuthRepository
import com.example.livingtogether.domain.repository.FamilyRepository
import com.example.livingtogether.domain.repository.HouseworkRepository
import com.example.livingtogether.domain.repository.UserRepository
import com.example.livingtogether.domain.repository.UsersHouseworkRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun providesAuthRepository(authRemoteDataSource: AuthRemoteDataSource): AuthRepository =
        AuthRepositoryImpl(authRemoteDataSource)

    @Provides
    @Singleton
    fun providesFamilyRepository(familyRemoteDataSource: FamilyRemoteDataSource): FamilyRepository =
        FamilyRepositoryImpl(familyRemoteDataSource)

    @Provides
    @Singleton
    fun providesHouseworkRepository(
        houseworkRemoteDataSource: HouseworkRemoteDataSource,
        usersHouseworkRemoteDataSource: UsersHouseworkRemoteDataSource
    ): HouseworkRepository =
        HouseworkRepositoryImpl(houseworkRemoteDataSource, usersHouseworkRemoteDataSource)

    @Provides
    @Singleton
    fun providesUserRepository(userRemoteDataSource: UserRemoteDataSource): UserRepository =
        UserRepositoryImpl(userRemoteDataSource)

    @Provides
    @Singleton
    fun providesUsersHouseworkRepository(usersHouseworkRemoteDataSource: UsersHouseworkRemoteDataSource): UsersHouseworkRepository =
        UsersHouseworkRepositoryImpl(usersHouseworkRemoteDataSource)
}
