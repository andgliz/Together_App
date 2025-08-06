package com.example.livingtogether.di

import com.example.livingtogether.TogetherApplication
import com.example.livingtogether.domain.repository.AuthRepository
import com.example.livingtogether.domain.repository.FamilyRepository
import com.example.livingtogether.domain.repository.HouseworkRepository
import com.example.livingtogether.domain.repository.UserRepository
import com.example.livingtogether.domain.repository.UsersHouseworkRepository
import com.example.livingtogether.domain.usecase.DeleteUserFromFamilyUseCase
import com.example.livingtogether.domain.usecase.DeleteUserUseCase
import com.example.livingtogether.domain.usecase.GetUserHouseworkListUseCase
import com.example.livingtogether.domain.usecase.GetUserRatingUseCase
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FirebaseModule::class, RepositoryModule::class])
interface AppComponent {
    fun inject(app: TogetherApplication)
    fun authRepository(): AuthRepository
    fun usersHouseworkRepository(): UsersHouseworkRepository
    fun houseworkRepository(): HouseworkRepository
    fun userRepository(): UserRepository
    fun getUserHouseworkListUseCase(): GetUserHouseworkListUseCase
    fun getUserRatingUseCase(): GetUserRatingUseCase
    fun familyRepository(): FamilyRepository
    fun deleteUserFromFamilyUseCase(): DeleteUserFromFamilyUseCase
    fun deleteUserUseCase(): DeleteUserUseCase
}
