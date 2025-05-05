package com.example.livingtogether.data.datasource

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AuthRemoteDataSource(private val auth: FirebaseAuth) {
    val currentUser: FirebaseUser? get() = auth.currentUser

    val currentUserIdFlow: Flow<String?>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { _ -> this.trySend(currentUser?.uid) }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    suspend fun signUp(email: String, password: String): String {
        var error = ""
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
        } catch (e: FirebaseAuthException) {
            error = e.message ?: "Sign Up Error."
        }
        return error
    }

    suspend fun signIn(email: String, password: String): String {
        var error = ""
        try {
            auth.signInWithEmailAndPassword(email, password).await()
        } catch (e: FirebaseAuthException) {
            error = e.message ?: "Sign In Error."
        }
        return error
    }

    suspend fun linkAccount(email: String, password: String) {
        val credential = EmailAuthProvider.getCredential(email, password)
        auth.currentUser!!.linkWithCredential(credential).await()
    }

    fun signOut() {
        auth.signOut()
    }

    suspend fun deleteAccount() {
        auth.currentUser!!.delete().await()
    }
}