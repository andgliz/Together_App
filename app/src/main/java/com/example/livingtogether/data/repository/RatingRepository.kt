package com.example.livingtogether.data.repository

import com.example.livingtogether.data.model.Rating
import java.util.Date

interface RatingRepository {

    suspend fun getForUserWithDateRange(userId: String, startDate: Date, endDate: Date): List<Rating>

    suspend fun addForDate(rating: Rating)

    suspend fun updateTotalSum(rating: Rating)
}