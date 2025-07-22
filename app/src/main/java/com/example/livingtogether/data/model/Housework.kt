package com.example.livingtogether.data.model

import com.google.firebase.firestore.DocumentId

data class Housework(
    @DocumentId val id: String = "",
    val name: String = "",
    val cost: Int = 0,
    val family: String = "",
)
