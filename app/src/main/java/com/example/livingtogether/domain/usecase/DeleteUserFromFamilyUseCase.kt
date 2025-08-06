package com.example.livingtogether.domain.usecase

import com.example.livingtogether.data.model.User
import com.example.livingtogether.domain.repository.UserRepository
import com.example.livingtogether.domain.repository.UsersHouseworkRepository
import javax.inject.Inject

class DeleteUserFromFamilyUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val usersHouseworkRepository: UsersHouseworkRepository,
) {
    suspend operator fun invoke(user: User) {
        usersHouseworkRepository.deleteAllByUserId(userId = user.id)
        userRepository.updateUser(user.copy(family = ""))
    }
}
