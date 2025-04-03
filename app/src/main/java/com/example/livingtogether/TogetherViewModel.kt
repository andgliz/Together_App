package com.example.livingtogether

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.livingtogether.data.TogetherRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class TogetherViewModel(
    private val togetherRepository: TogetherRepository
) : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    val isAuth by mutableStateOf(auth.currentUser != null)
}