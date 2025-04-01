package com.example.livingtogether.data

import androidx.room.TypeConverter
import java.util.Arrays
import java.util.stream.Collectors

class UserConverter {
    @TypeConverter
    fun fromUsers(user: User): String {
        return user.id.toString() + "," + user.name + "," + user.total.toString()
    }

    @TypeConverter
    fun toUsers(data: String): User {
        val list = data.split(",")
        return User(id = list[0].toInt(), name = list[1], total = list[2].toInt() )
    }
}

class HouseworkConverter {
    @TypeConverter
    fun fromHousework(housework: Housework): String {
        return housework.id.toString() + "," + housework.name + "," + housework.cost.toString()
    }

    @TypeConverter
    fun toHousework(data: String): Housework {
        val list = data.split(",")
        return Housework(id = list[0].toInt(), name = list[1], cost = list[2].toInt())
    }
}