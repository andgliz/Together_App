package com.example.livingtogether.ui

import com.example.livingtogether.R
import com.example.livingtogether.data.model.Family
import com.example.livingtogether.data.model.Housework
import com.example.livingtogether.data.model.User
import com.example.livingtogether.ui.housework.HouseworkDestination
import com.example.livingtogether.ui.navigation.NavigationDestination
import com.example.livingtogether.ui.profile.ProfileDestination
import com.example.livingtogether.ui.rating.RatingDestination
import com.example.livingtogether.ui.today.TodayDestination
import java.util.Date

data class TodayUiState(
    val userHouseworkList: List<HouseworkViewData> = listOf(),
    val houseworkList: List<HouseworkViewData> = listOf(),
    val selectedDate: String = "",
)

data class HouseworkUiState(
    val houseworkList: List<HouseworkViewData> = listOf(),
    val houseworkInput: HouseworkViewData = HouseworkViewData(),
    val isEnabled: Boolean = false,
)

data class UserToRatingData(
    val userName: String,
    val totalRating: Int,
    val ratingPlace: Int = 0,
)

data class RatingUiState(
    val userToRatings: List<UserToRatingData> = listOf(),
    val startDate: Date,
    val endDate: Date,
)

data class LoginUiState(
    val emailState: String = "",
    val passwordState: String = "",
    val errorState: String = "",
    val nameState: String = "",
)

data class ProfileUiState(
    val user: UserViewData = UserViewData(),
    val familyName: String = "",
    val userName: String = "",
    val errorState: String = "",
)

data class FamilyUiState(
    val nameState: String = "",
    val passwordState: String = "",
    val errorState: String = "",
)

data class HouseworkViewData(
    val id: String = "",
    val houseworkId: String = "",
    val name: String = "",
    val cost: String = "",
)

data class UserViewData(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val family: String = "",
)

data class FamilyViewData(
    val id: String = "",
    val name: String = "",
    val password: String = "",
)

data class UsersHouseworkViewData(
    val id: String = "",
    val userId: String = "",
    val houseworkId: String = "",
)

sealed class BottomMenuItem(
    val navigation: NavigationDestination,
    val icon: Int,
) {
    object RatingItem : BottomMenuItem(
        navigation = RatingDestination,
        icon = R.drawable.rating,
    )

    object TodayItem : BottomMenuItem(
        navigation = TodayDestination,
        icon = R.drawable.today,
    )

    object HouseworkItem : BottomMenuItem(
        navigation = HouseworkDestination,
        icon = R.drawable.list,
    )

    object ProfileItem : BottomMenuItem(
        navigation = ProfileDestination,
        icon = R.drawable.profile,
    )
}

fun HouseworkViewData.toHousework(family: String): Housework = Housework(
    id = id,
    name = name,
    cost = cost.toInt(),
    family = family,
)

fun Housework.toHouseworkViewData(): HouseworkViewData = HouseworkViewData(
    id = id,
    name = name,
    cost = cost.toString(),
)

fun UserViewData.toUser(): User = User(
    id = id,
    userName = name,
    email = email,
    family = family,
)

fun User.toUserViewData(): UserViewData = UserViewData(
    id = id,
    name = userName,
    email = email,
    family = family,
)

fun Family.toFamilyViewData(): FamilyViewData = FamilyViewData(
    id = id,
    name = name,
    password = password,
)

fun FamilyViewData.toFamily(): Family = Family(
    id = id,
    name = name,
    password = password,
)

//fun Housework.toUsersHouseworkViewData(): UsersHouseworkViewData = UsersHouseworkViewData(
//    id = id,
//    name = name,
//    cost = cost.toString()
//)
