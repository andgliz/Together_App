package com.example.livingtogether.data.model

import com.google.firebase.firestore.DocumentId

data class UsersHousework(
    @DocumentId val id: String = "",
    val userId: String = "",
    val houseworkId: String = ""
)
