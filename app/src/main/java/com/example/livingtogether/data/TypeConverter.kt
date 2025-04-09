package com.example.livingtogether.data

import androidx.room.TypeConverter

class UserConverter {
    @TypeConverter
    fun fromUsers(user: User): String {
        return user.id.toString() + "," + user.name + "," + user.total.toString()
    }

    @TypeConverter
    fun toUsers(data: String): User {
        val list = data.split(",")
        return User(id = list[0].toInt(), name = list[1], total = list[2].toInt())
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

class FamilyConverter {
    @TypeConverter
    fun fromFamily(family: Family): String {
        return family.id.toString() + "," + family.name
    }

    @TypeConverter
    fun toFamily(data: String): Family {
        val list = data.split(",")
        return Family(id = list[0].toInt(), name = list[1])
    }
}