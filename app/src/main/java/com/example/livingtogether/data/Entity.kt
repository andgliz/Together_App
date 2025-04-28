package com.example.livingtogether.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "housework")
data class Housework(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "cost")
    val cost: Int = 0,
    @ColumnInfo(name = "family")
    val family: Family
)

@Entity(tableName = "user", indices = [Index(value = ["email"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "family")
    val family: Family? = null
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
    val name: String,
    @ColumnInfo(name = "family_password")
    val password: String
)

//@Entity
//data class Rating(
//    @PrimaryKey(autoGenerate = true)
//    val id: Int = 0,
//    @ColumnInfo(name = "user")
//    val user: User
//)
