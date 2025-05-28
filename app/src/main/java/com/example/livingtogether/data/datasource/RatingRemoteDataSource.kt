package com.example.livingtogether.data.datasource

import com.example.livingtogether.data.model.Rating
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import java.util.Date

class RatingRemoteDataSource(private val firestore: FirebaseFirestore) {

    suspend fun getForUserWithDateRange(userId: String, startDate: Date, endDate: Date): List<Rating> {
        return firestore
            .collection(RATING_COLLECTION)
            .whereEqualTo(USER_ID, userId)
            .whereGreaterThanOrEqualTo(DATE, startDate)
            .whereLessThanOrEqualTo(DATE, endDate)
            .get()
            .await()
            .map { it.toObject() }
    }

    suspend fun addForDate(rating: Rating) {
        firestore.collection(RATING_COLLECTION).add(rating).await()
    }

    suspend fun addOrUpdateForDate(rating: Rating) {
        firestore.collection(RATING_COLLECTION).document(rating.userId + rating.date)
            .set(rating).await()
    }

    companion object {
        private const val USER_ID = "userId"
        private const val DATE = "date"
        private const val RATING_COLLECTION = "rating"
    }
}