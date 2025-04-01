package com.example.livingtogether.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TogetherDao {
    @Query("SELECT * FROM users_housework WHERE user = :user")
    suspend fun getUsersData(user: User): List<UsersHousework>

    @Query("SELECT * FROM housework")
    suspend fun getHousework(): List<Housework>

    @Query("SELECT * FROM user ORDER BY total DESC")
    suspend fun getUsersRating(): List<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIntoHousework(housework: Housework)

    @Delete
    suspend fun deleteFromHousework(housework: Housework)

    @Update
    suspend fun updateHousework(housework: Housework)
}