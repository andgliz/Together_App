package com.example.livingtogether.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.livingtogether.ui.BottomMenuItem
import com.example.livingtogether.ui.family.FamilyDestination
import com.example.livingtogether.ui.family.FamilyScreen
import com.example.livingtogether.ui.housework.HouseworkDestination
import com.example.livingtogether.ui.housework.HouseworkScreen
import com.example.livingtogether.ui.login.LoginDestination
import com.example.livingtogether.ui.login.LoginScreen
import com.example.livingtogether.ui.profile.ProfileDestination
import com.example.livingtogether.ui.profile.ProfileScreen
import com.example.livingtogether.ui.rating.RatingDestination
import com.example.livingtogether.ui.rating.RatingScreen
import com.example.livingtogether.ui.today.TodayDestination
import com.example.livingtogether.ui.today.TodayScreen
import com.example.ui.theme.AppTypography

@Composable
fun TogetherNavGraph(
    navController: NavHostController,
    isAuth: Boolean,
    modifier: Modifier = Modifier,
    isUserInFamily: Boolean
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(
            navController = navController,
            startDestination = if (!isAuth) LoginDestination.route else if (isUserInFamily) RatingDestination.route else FamilyDestination.route,
            modifier = modifier
        ) {
            composable(route = TodayDestination.route) {
                TodayScreen(
                    title = TodayDestination.titleRes
                )
            }

            composable(route = HouseworkDestination.route) {
                HouseworkScreen(
                    title = HouseworkDestination.titleRes
                )
            }

            composable(route = RatingDestination.route) {

                RatingScreen(
                    title = RatingDestination.titleRes
                )
            }

            composable(route = ProfileDestination.route) {
                ProfileScreen(
                    title = ProfileDestination.titleRes,
                    onSuccess = {
                        navController.navigate(LoginDestination.route)
                    }
                )
            }

            composable(route = LoginDestination.route) {
                LoginScreen(
                    title = LoginDestination.titleRes,
                    onSuccess = {
                        navController.navigate(if (isUserInFamily) RatingDestination.route else FamilyDestination.route)
                    }
                )
            }

            composable(route = FamilyDestination.route) {
                FamilyScreen(
                    title = FamilyDestination.titleRes,
                    onSuccess = {
                        navController.navigate(RatingDestination.route)
                    }
                )
            }
        }
    }

}

@Composable
fun NavigationBar(
    items: List<BottomMenuItem>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = item.navigation.route == currentRoute,
                onClick = {
                    navController.navigate(item.navigation.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = stringResource(item.navigation.titleRes),
                        modifier = Modifier.size(40.dp)
                    )
                },
                label = {
                    Text(text = stringResource(item.navigation.titleRes),
                        style = AppTypography.titleSmall)
                },
                alwaysShowLabel = false,
            )
        }
    }
}
