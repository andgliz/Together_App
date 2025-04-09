package com.example.livingtogether.ui

import com.example.livingtogether.R
import com.example.livingtogether.data.Housework
import com.example.livingtogether.data.User
import com.example.livingtogether.data.UsersHousework
import com.example.livingtogether.ui.housework.HouseworkDestination
import com.example.livingtogether.ui.navigation.NavigationDestination
import com.example.livingtogether.ui.profile.ProfileDestination
import com.example.livingtogether.ui.rating.RatingDestination
import com.example.livingtogether.ui.today.TodayDestination

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

data class LoginUiState(
    val emailState: String = "",
    val passwordState: String = "",
    val errorState: String = ""
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

sealed class BottomMenuItem(
    val navigation: NavigationDestination,
    val icon: Int
) {
    object RatingItem : BottomMenuItem(
        navigation = RatingDestination,
        icon = R.drawable.rating
    )
    object TodayItem : BottomMenuItem(
        navigation = TodayDestination,
        icon = R.drawable.today
    )
    object HouseworkItem : BottomMenuItem(
        navigation = HouseworkDestination,
        icon = R.drawable.list
    )
    object ProfileItem : BottomMenuItem(
        navigation = ProfileDestination,
        icon = R.drawable.profile
    )
}

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

