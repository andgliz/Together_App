package com.example.livingtogether.data.model

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class Rating(
    @DocumentId val id: String = "",
    val userId: String = "",
    val total: Int = 0,
    val date: Date = Date()
)