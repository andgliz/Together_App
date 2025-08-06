package com.example.livingtogether.data.datasource

import com.example.livingtogether.data.model.Family
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FamilyRemoteDataSource @Inject constructor(private val firestore: FirebaseFirestore) {

    suspend fun findFamily(name: String, password: String): String? {
        val findFamily =  firestore
            .collection(FAMILY_COLLECTION)
            .whereEqualTo(NAME_FIELD, name)
            .whereEqualTo(PASSWORD_FIELD, password)
            .get()
            .await()
            .toObjects<Family>()
        return if (findFamily.isEmpty()) {
            null
        } else {
            findFamily.first().id
        }
    }

    suspend fun getFamily(familyId: String): Family? {
        return firestore.collection(FAMILY_COLLECTION).document(familyId).get().await().toObject()
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
