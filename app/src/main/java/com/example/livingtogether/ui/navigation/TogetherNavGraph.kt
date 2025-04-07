package com.example.livingtogether.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.livingtogether.ui.BottomMenuItem
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TogetherNavGraph(
    navController: NavHostController = rememberNavController(),
    isAuth: () -> Boolean,
    modifier: Modifier = Modifier
) {
    Scaffold(
        bottomBar = {
            if (isAuth()) {
                NavigationBar(
                    items = listOf(
                        BottomMenuItem.RatingItem,
                        BottomMenuItem.TodayItem,
                        BottomMenuItem.HouseworkItem,
                        BottomMenuItem.ProfileItem
                    ),
                    onIconClick = { navController.navigate(it) }
                )
            }
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = if (isAuth()) RatingDestination.route else LoginDestination.route,
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
                            isAuth()
                        }
                    )
                }
                composable(route = LoginDestination.route) {
                    LoginScreen(
                        title = LoginDestination.titleRes,
                        onSuccess = {
                            navController.navigate(RatingDestination.route)
                            isAuth()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun NavigationBar(
    items: List<BottomMenuItem>,
    onIconClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier.fillMaxWidth()
    ) {
        items.forEach { item ->
            Row(
                modifier = modifier
                    .weight(1f)
            ) {
                BottomBarItem(
                    title = item.navigation.titleRes,
                    icon = item.icon,
                    route = item.navigation.route,
                    onIconClick = onIconClick
                )
            }
        }

    }
}

@Composable
fun BottomBarItem(
    onIconClick: (String) -> Unit,
    route: String,
    title: Int,
    icon: Int,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = { onIconClick(route) },
        modifier = Modifier
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = stringResource(title)
        )
    }
}
