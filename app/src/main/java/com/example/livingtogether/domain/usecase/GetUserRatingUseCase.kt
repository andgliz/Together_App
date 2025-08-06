package com.example.livingtogether.domain.usecase

import com.example.livingtogether.domain.repository.HouseworkRepository
import com.example.livingtogether.domain.repository.UsersHouseworkRepository
import java.util.Date
import javax.inject.Inject

class GetUserRatingUseCase @Inject constructor(
    private val usersHouseworkRepository: UsersHouseworkRepository,
    private val houseworkRepository: HouseworkRepository,
) {
    suspend operator fun invoke(userId: String, startDate: Date, endDate: Date): Int {
        return usersHouseworkRepository.getUserHouseworkList(
            userId = userId,
            startDate = startDate,
            endDate = endDate,
        ).sumOf {
            houseworkRepository.getHouseworkItem(it)!!.cost
        }
    }
}
