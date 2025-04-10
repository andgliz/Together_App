package com.example.livingtogether.data

import androidx.room.TypeConverter

class UserConverter {
    @TypeConverter
    fun fromUsers(user: User): String {
        return user.id.toString() + "," + user.name + "," + user.email + "," + user.family
    }

    @TypeConverter
    fun toUsers(data: String): User {
        val list = data.split(",")
        return User(
            id = list[0].toInt(),
            name = list[1],
            email = list[2],
            family = if (list[3] != "null") Family(id = list[3].toInt(), name = list[4], password = list[5]) else null
        )
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
    fun fromFamily(family: Family?): String {
        return if (family != null) {
            family.id.toString() + "," + family.name + "," + family.password
        } else {
            "null"
        }
    }

    @TypeConverter
    fun toFamily(data: String): Family? {
        val list = data.split(",")
        return if (list[0] != "null") Family(id = list[0].toInt(), name = list[1], password = list[2]) else null
    }
}