package com.example.livingtogether.data.datasource

import com.example.livingtogether.data.model.UsersHousework
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class UsersHouseworkRemoteDataSource(private val firestore: FirebaseFirestore) {

    fun getUsersHouseworkListFlow(currentUserId: String) : Flow<List<UsersHousework>> {
        return firestore
            .collection(USERS_HOUSEWORK_COLLECTION)
            .whereEqualTo(USER_ID, currentUserId)
            .dataObjects()
    }

    suspend fun getUsersHouseworkItem(usersHouseworkId: String): UsersHousework? {
        return firestore.collection(USERS_HOUSEWORK_COLLECTION).document(usersHouseworkId).get().await()
            .toObject()
    }

    suspend fun createUsersHousework(usersHousework: UsersHousework) {
        firestore.collection(USERS_HOUSEWORK_COLLECTION).add(usersHousework).await().id
    }

    suspend fun updateUsersHousework(usersHousework: UsersHousework) {
        firestore.collection(USERS_HOUSEWORK_COLLECTION).document(usersHousework.id).set(usersHousework).await()
    }

    suspend fun deleteUsersHousework(usersHouseworkId: String) {
        firestore.collection(USERS_HOUSEWORK_COLLECTION).document(usersHouseworkId).delete().await()
    }

    companion object {
        private const val USER_ID = "userId"
        private const val USERS_HOUSEWORK_COLLECTION = "users_housework"
    }
}