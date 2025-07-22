package com.example.livingtogether.data

import android.content.Context
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
import com.example.livingtogether.domain.usecase.DeleteUserFromFamilyUseCase
import com.example.livingtogether.domain.usecase.DeleteUserUseCase
import com.example.livingtogether.domain.usecase.GetUserHouseworkListUseCase
import com.example.livingtogether.domain.usecase.GetUserRatingUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

interface AppContainer {
    val authRepository: AuthRepository
    val userRepository: UserRepository
    val familyRepository: FamilyRepository
    val houseworkRepository: HouseworkRepository
    val usersHouseworkRepository: UsersHouseworkRepository
    val getUserHouseworkListUseCase: GetUserHouseworkListUseCase
    val getUserRatingUseCase: GetUserRatingUseCase
    val deleteUserFromFamilyUseCase: DeleteUserFromFamilyUseCase
    val deleteUserUseCase: DeleteUserUseCase
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(AuthRemoteDataSource(FirebaseAuth.getInstance()))
    }

    override val userRepository: UserRepository by lazy {
        UserRepositoryImpl(UserRemoteDataSource(FirebaseFirestore.getInstance()))
    }

    override val familyRepository: FamilyRepository by lazy {
        FamilyRepositoryImpl(FamilyRemoteDataSource(FirebaseFirestore.getInstance()))
    }

    override val houseworkRepository: HouseworkRepository by lazy {
        HouseworkRepositoryImpl(
            HouseworkRemoteDataSource(FirebaseFirestore.getInstance()),
            UsersHouseworkRemoteDataSource(FirebaseFirestore.getInstance())
        )
    }

    override val usersHouseworkRepository: UsersHouseworkRepository by lazy {
        UsersHouseworkRepositoryImpl(UsersHouseworkRemoteDataSource(FirebaseFirestore.getInstance()))
    }

    override val getUserHouseworkListUseCase: GetUserHouseworkListUseCase =
        GetUserHouseworkListUseCase(houseworkRepository)

    override val getUserRatingUseCase: GetUserRatingUseCase =
        GetUserRatingUseCase(usersHouseworkRepository, houseworkRepository)

    override val deleteUserFromFamilyUseCase: DeleteUserFromFamilyUseCase =
        DeleteUserFromFamilyUseCase(
            userRepository = userRepository,
            usersHouseworkRepository = usersHouseworkRepository,
        )

    override val deleteUserUseCase: DeleteUserUseCase =
        DeleteUserUseCase(
            authRepository = authRepository,
            userRepository = userRepository,
            usersHouseworkRepository = usersHouseworkRepository
        )
}
