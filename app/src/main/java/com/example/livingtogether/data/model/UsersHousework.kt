package com.example.livingtogether.data.model

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class UsersHousework(
    @DocumentId val id: String = "",
    val userId: String = "",
    val houseworkId: String = "",
    val date: Date = Date(),
)
