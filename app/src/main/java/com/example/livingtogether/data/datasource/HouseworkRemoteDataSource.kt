package com.example.livingtogether.data.datasource

import com.example.livingtogether.data.model.Housework
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class HouseworkRemoteDataSource(private val firestore: FirebaseFirestore) {

    fun getHouseworkListFlow(currentUsersFamily: String) : Flow<List<Housework>> {
        return firestore
                .collection(HOUSEWORK_COLLECTION)
                .whereEqualTo(FAMILY_ID, currentUsersFamily)
                .dataObjects()
    }

    suspend fun getHouseworkItem(houseworkId: String): Housework? {
        return firestore.collection(HOUSEWORK_COLLECTION).document(houseworkId).get().await()
            .toObject()
    }

    suspend fun createHousework(housework: Housework) {
        firestore.collection(HOUSEWORK_COLLECTION).add(housework).await().id
    }

    suspend fun updateHousework(housework: Housework) {
        firestore.collection(HOUSEWORK_COLLECTION).document(housework.id).set(housework).await()
    }

    suspend fun deleteHousework(houseworkId: String) {
        firestore.collection(HOUSEWORK_COLLECTION).document(houseworkId).delete().await()
    }

    companion object {
        private const val FAMILY_ID = "family"
        private const val HOUSEWORK_COLLECTION = "housework"
    }
}
