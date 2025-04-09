package com.example.livingtogether.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "housework")
data class Housework(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo(name = "cost")
    val cost: Int = 0
)

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo(name = "total")
    val total: Int = 0
)

@Entity(tableName = "users_housework")
data class UsersHousework(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "user")
    val user: User,
    @ColumnInfo(name = "housework")
    val housework: Housework
)

@Entity(tableName = "family")
data class Family(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "family_name")
    val name: String
)

//@Entity
//data class Rating(
//    @PrimaryKey(autoGenerate = true)
//    val id: Int = 0,
//    @ColumnInfo(name = "user")
//    val user: User
//)
