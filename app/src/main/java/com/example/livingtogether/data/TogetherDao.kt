package com.example.livingtogether.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TogetherDao {
    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun getUser(email: String?): User

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIntoFamily(family: Family)

    @Query("UPDATE user SET family = :family WHERE email = :email")
    suspend fun addFamilyForUser(family: Family?, email: String?)

    @Query("SELECT * FROM family WHERE family_name = :familyName AND family_password = :inputPassword")
    suspend fun getFamilyData(familyName:String, inputPassword: String) : Family?

    @Query("SELECT * FROM users_housework WHERE user = :user")
    suspend fun getUsersData(user: User): List<UsersHousework>

    @Query("SELECT * FROM housework WHERE family = :family")
    suspend fun getHousework(family: Family): List<Housework>

    @Query("SELECT * FROM user WHERE family = :family")
    suspend fun getUsersRating(family: Family): List<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIntoUser(user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIntoHousework(housework: Housework)

    @Delete
    suspend fun deleteFromHousework(housework: Housework)

    @Update
    suspend fun updateHousework(housework: Housework)

    @Query("UPDATE user SET name = :name")
    suspend fun updateUserName(name: String)
}