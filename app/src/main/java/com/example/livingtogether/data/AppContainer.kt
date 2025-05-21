package com.example.livingtogether.data

import android.content.Context
import com.example.livingtogether.data.datasource.AuthRemoteDataSource
import com.example.livingtogether.data.datasource.FamilyRemoteDataSource
import com.example.livingtogether.data.datasource.HouseworkRemoteDataSource
import com.example.livingtogether.data.datasource.UserRemoteDataSource
import com.example.livingtogether.data.datasource.UsersHouseworkRemoteDataSource
import com.example.livingtogether.data.offlinerepository.OfflineAuthRepository
import com.example.livingtogether.data.offlinerepository.OfflineFamilyRepository
import com.example.livingtogether.data.offlinerepository.OfflineHouseworkRepository
import com.example.livingtogether.data.offlinerepository.OfflineUserRepository
import com.example.livingtogether.data.offlinerepository.OfflineUsersHouseworkRepository
import com.example.livingtogether.data.repository.AuthRepository
import com.example.livingtogether.data.repository.FamilyRepository
import com.example.livingtogether.data.repository.HouseworkRepository
import com.example.livingtogether.data.repository.UserRepository
import com.example.livingtogether.data.repository.UsersHouseworkRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

interface AppContainer {
    val togetherRepository: TogetherRepository
    val authRepository: AuthRepository
    val userRepository: UserRepository
    val familyRepository: FamilyRepository
    val houseworkRepository: HouseworkRepository
    val usersHouseworkRepository: UsersHouseworkRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val togetherRepository: TogetherRepository by lazy {
        OfflineTogetherRepository(TogetherDatabase.getDatabase(context).togetherDao())
    }

    override val authRepository: AuthRepository by lazy {
        OfflineAuthRepository(AuthRemoteDataSource(FirebaseAuth.getInstance()))
    }

    override val userRepository: UserRepository by lazy {
        OfflineUserRepository(UserRemoteDataSource(FirebaseFirestore.getInstance()))
    }

    override val familyRepository: FamilyRepository by lazy {
        OfflineFamilyRepository(FamilyRemoteDataSource(FirebaseFirestore.getInstance()))
    }

    override val houseworkRepository: HouseworkRepository by lazy {
        OfflineHouseworkRepository(HouseworkRemoteDataSource(FirebaseFirestore.getInstance()), UsersHouseworkRemoteDataSource(FirebaseFirestore.getInstance()))
    }

    override val usersHouseworkRepository: UsersHouseworkRepository by lazy {
        OfflineUsersHouseworkRepository(UsersHouseworkRemoteDataSource(FirebaseFirestore.getInstance()))
    }
}
