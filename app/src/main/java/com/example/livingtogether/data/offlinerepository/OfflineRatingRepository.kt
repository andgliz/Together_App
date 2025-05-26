package com.example.livingtogether.data.offlinerepository

import com.example.livingtogether.data.datasource.RatingRemoteDataSource
import com.example.livingtogether.data.model.Rating
import com.example.livingtogether.data.repository.RatingRepository
import java.util.Date

class OfflineRatingRepository(private val ratingRemoteDataSource: RatingRemoteDataSource) :
    RatingRepository {

    override suspend fun getForUserWithDateRange(
        userId: String,
        startDate: Date,
        endDate: Date
    ): List<Rating> {
        return ratingRemoteDataSource.getForUserWithDateRange(
            userId, startDate, endDate
        )
    }

    override suspend fun addForDate(rating: Rating) {
        ratingRemoteDataSource.addForDate(rating)
    }

    override suspend fun updateTotalSum(rating: Rating) {
        ratingRemoteDataSource.updateTotalSum(rating)
    }
}