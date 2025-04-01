package com.example.livingtogether.ui

import com.example.livingtogether.data.Housework
import com.example.livingtogether.data.User
import com.example.livingtogether.data.UsersHousework

data class TodayUiState(
    val housework: List<UsersHouseworkViewData> = listOf()
)

data class HouseworkUiState(
    val houseworkList: List<HouseworkViewData> = listOf(),
    val houseworkInput: HouseworkViewData = HouseworkViewData(),
    val isEnabled: Boolean = false
)

data class RatingUiState(
    val usersList: List<UserViewData> = listOf()
)

data class HouseworkViewData(
    val id: Int = 0,
    val name: String = "",
    val cost: String = ""
)

data class UserViewData(
    val id: Int = 0,
    val name: String = "",
    val total: String = ""
)

data class UsersHouseworkViewData(
    val id: Int = 0,
    val user: UserViewData = UserViewData(),
    val housework: HouseworkViewData = HouseworkViewData()
)



fun HouseworkViewData.toHousework(): Housework = Housework(
    id = id,
    name = name,
    cost = cost.toInt()
)

fun Housework.toHouseworkViewData(): HouseworkViewData = HouseworkViewData(
    id = id,
    name = name,
    cost = cost.toString()
)

fun UserViewData.toUser(): User = User(
    id = id,
    name = name,
    total = total.toInt()
)

fun User.toUserViewData(): UserViewData = UserViewData(
    id = id,
    name = name,
    total = total.toString()
)

fun UsersHouseworkViewData.toUsersHousework(): UsersHousework = UsersHousework(
    id = id,
    user = user.toUser(),
    housework = housework.toHousework()
)

fun UsersHousework.toUsersHouseworkViewData(): UsersHouseworkViewData = UsersHouseworkViewData(
    id = id,
    user = user.toUserViewData(),
    housework = housework.toHouseworkViewData()
)

