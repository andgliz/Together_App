package com.example.livingtogether.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.livingtogether.data.TogetherRepository
import com.example.livingtogether.ui.LoginUiState
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel(
    private val togetherRepository: TogetherRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val auth: FirebaseAuth = Firebase.auth

    fun signOut(onSuccess: () -> Unit) {
        auth.signOut()
        onSuccess()
    }

    fun onDeleteAccountClicked(email: String, password: String, onSuccess: () -> Unit) {
        val credential = EmailAuthProvider.getCredential(email, password)
        auth.currentUser?.reauthenticate(credential)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                auth.currentUser?.delete()?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("MyLog", "Account was deleted")
                        onSuccess()
                    } else {
                        Log.d("MyLog", "Failure delete account")
                    }
                }
            } else {
                Log.d("MyLog", "Failure reathenticate")
            }
        }

    }
}