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
import com.example.livingtogether.R
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
                    onRatingClick = { navController.navigate(RatingDestination.route) },
                    onTodayClick = { navController.navigate(TodayDestination.route) },
                    onHouseworkClick = { navController.navigate(HouseworkDestination.route) },
                    onProfileClick = { navController.navigate(ProfileDestination.route) }
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
    onRatingClick: () -> Unit,
    onTodayClick: () -> Unit,
    onHouseworkClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar {
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = onRatingClick,
                modifier = Modifier.weight(1F)
            ) {
                Icon(
                    painter = painterResource(R.drawable.rating),
                    contentDescription = stringResource(R.string.rating)
                )
            }
            IconButton(
                onClick = onTodayClick,
                modifier = Modifier.weight(1F)
            ) {
                Icon(
                    painter = painterResource(R.drawable.today),
                    contentDescription = stringResource(R.string.today)
                )
            }
            IconButton(
                onClick = onHouseworkClick,
                modifier = Modifier.weight(1F)
            ) {
                Icon(
                    painter = painterResource(R.drawable.list),
                    contentDescription = stringResource(R.string.housework)
                )
            }
            IconButton(
                onClick = onProfileClick,
                modifier = Modifier.weight(1F)
            ) {
                Icon(
                    painter = painterResource(R.drawable.profile),
                    contentDescription = stringResource(R.string.profile)
                )
            }
        }
    }


}