package com.example.livingtogether.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Housework::class, User::class, UsersHousework::class, Family::class], version = 1, exportSchema = false)
@TypeConverters(HouseworkConverter::class, UserConverter::class, FamilyConverter::class)
abstract class TogetherDatabase : RoomDatabase() {

    abstract fun togetherDao(): TogetherDao

    companion object {
        @Volatile
        private var Instance: TogetherDatabase? = null

        fun getDatabase(context: Context) : TogetherDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TogetherDatabase::class.java, "together")
//                    .createFromAsset("database/together.db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}