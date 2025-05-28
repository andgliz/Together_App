package com.example.livingtogether.data.model

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId val id: String = "",
    val email: String = "",
    val userName: String = "",
    val family: String = ""
)
