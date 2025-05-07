package com.example.livingtogether.data.datasource

import com.example.livingtogether.data.model.Family
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.tasks.await

class FamilyRemoteDataSource(private val firestore: FirebaseFirestore) {

    suspend fun findFamily(name: String, password: String): String {
        return firestore
            .collection(FAMILY_COLLECTION)
            .whereEqualTo(NAME_FIELD, name)
            .whereEqualTo(PASSWORD_FIELD, password)
            .get()
            .await()
            .toObjects<Family>().first().id
    }

    suspend fun createFamily(family: Family): String {
        return firestore.collection(FAMILY_COLLECTION).add(family).await().id
    }

    suspend fun updateFamily(family: Family) {
        firestore.collection(FAMILY_COLLECTION).document(family.id).set(family).await()
    }

    suspend fun deleteFamily(familyId: String) {
        firestore.collection(FAMILY_COLLECTION).document(familyId).delete().await()
    }

    companion object {
        private const val NAME_FIELD = "name"
        private const val PASSWORD_FIELD = "password"
        private const val FAMILY_COLLECTION = "family"
    }
}
