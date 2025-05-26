package com.example.livingtogether.data.datasource

import com.example.livingtogether.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class UserRemoteDataSource(private val firestore: FirebaseFirestore) {

    fun getAllFromFamilyFlow(currentUsersFamily: String): Flow<List<User>> {
        return firestore
            .collection(USERS_COLLECTION)
            .whereEqualTo(FAMILY_ID, currentUsersFamily)
            .dataObjects()
    }

    fun getUserFlow(currentUserId: String): Flow<User?> {
        return firestore
            .collection(USERS_COLLECTION)
            .document(currentUserId)
            .dataObjects<User>()
    }

    suspend fun getAllFromFamily(currentUsersFamily: String): List<User> {
        return firestore
            .collection(USERS_COLLECTION)
            .whereEqualTo(FAMILY_ID, currentUsersFamily)
            .get()
            .await()
            .map { it.toObject<User>() }
    }

    suspend fun getUser(currentUserId: String): User? {
        return firestore
            .collection(USERS_COLLECTION)
            .document(currentUserId)
            .get()
            .await()
            .toObject()
    }

    suspend fun createUser(user: User) {
        firestore.collection(USERS_COLLECTION).document(user.id).set(user).await()
    }

    suspend fun updateUser(user: User) {
        firestore.collection(USERS_COLLECTION).document(user.id).set(user).await()
    }

    suspend fun deleteUser(userId: String) {
        firestore.collection(USERS_COLLECTION).document(userId).delete().await()
    }

    companion object {
        private const val FAMILY_ID = "family"
        private const val USERS_COLLECTION = "users"
    }
}
