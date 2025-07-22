package com.example.livingtogether.data.model

import com.google.firebase.firestore.DocumentId

data class Family(
    @DocumentId val id: String = "",
    val name: String = "",
    val password: String = "",
)
