package com.example.livingtogether.data.datasource

import com.example.livingtogether.data.model.UsersHousework
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import java.util.Date

class UsersHouseworkRemoteDataSource(private val firestore: FirebaseFirestore) {

    fun getUsersHouseworkListFlow(currentUserId: String, date: Date): Flow<List<UsersHousework>> {
        return firestore
            .collection(USERS_HOUSEWORK_COLLECTION)
            .whereEqualTo(USER_ID, currentUserId)
            .whereEqualTo(DATE, date)
            .dataObjects()
    }

    suspend fun getUsersHouseworkItem(usersHouseworkId: String): UsersHousework? {
        return firestore.collection(USERS_HOUSEWORK_COLLECTION).document(usersHouseworkId).get()
            .await()
            .toObject()
    }

    suspend fun createUsersHousework(usersHousework: UsersHousework) {
        firestore.collection(USERS_HOUSEWORK_COLLECTION).add(usersHousework).await().id
    }

    suspend fun updateUsersHousework(usersHousework: UsersHousework) {
        firestore.collection(USERS_HOUSEWORK_COLLECTION).document(usersHousework.id)
            .set(usersHousework).await()
    }

    suspend fun deleteUsersHousework(usersHouseworkId: String) {
        firestore.collection(USERS_HOUSEWORK_COLLECTION).document(usersHouseworkId).delete().await()
    }

    suspend fun deleteAllByHouseworkId(houseworkId: String) {
        firestore.collection(USERS_HOUSEWORK_COLLECTION)
            .whereEqualTo(HOUSEWORK_ID, houseworkId)
            .get()
            .await()
            .toObjects<UsersHousework>()
            .map { deleteUsersHousework(it.id) }
    }

    companion object {
        private const val USER_ID = "userId"
        private const val HOUSEWORK_ID = "houseworkId"
        private const val DATE = "date"
        private const val USERS_HOUSEWORK_COLLECTION = "users_housework"
    }
}