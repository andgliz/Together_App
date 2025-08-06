package com.example.livingtogether.domain.usecase

import com.example.livingtogether.data.model.Housework
import com.example.livingtogether.data.model.UsersHousework
import com.example.livingtogether.domain.repository.HouseworkRepository
import javax.inject.Inject

class GetUserHouseworkListUseCase @Inject constructor(
    private val houseworkRepository: HouseworkRepository
) {
    suspend operator fun invoke(houseworkList: List<UsersHousework>): List<Housework> {
        return houseworkList.map {
            val housework = houseworkRepository.getHouseworkItem(it.houseworkId)!!
            Housework(
                id = it.id,
                name = housework.name,
                cost = housework.cost,
            )
        }
    }
}
