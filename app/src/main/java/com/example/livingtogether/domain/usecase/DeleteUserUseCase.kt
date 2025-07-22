package com.example.livingtogether.domain.usecase

import com.example.livingtogether.domain.repository.AuthRepository
import com.example.livingtogether.domain.repository.UserRepository
import com.example.livingtogether.domain.repository.UsersHouseworkRepository

class DeleteUserUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val usersHouseworkRepository: UsersHouseworkRepository,
) {
    suspend operator fun invoke(userId: String) {
        authRepository.deleteAccount()
        usersHouseworkRepository.deleteAllByUserId(userId)
        userRepository.deleteUser(userId)
    }
}
