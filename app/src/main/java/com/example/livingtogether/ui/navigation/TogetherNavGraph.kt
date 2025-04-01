package com.example.livingtogether.ui.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.livingtogether.R
import com.example.livingtogether.ui.housework.HouseworkDestination
import com.example.livingtogether.ui.housework.HouseworkScreen
import com.example.livingtogether.ui.profile.ProfileDestination
import com.example.livingtogether.ui.profile.ProfileScreen
import com.example.livingtogether.ui.rating.RatingDestination
import com.example.livingtogether.ui.rating.RatingScreen
import com.example.livingtogether.ui.today.TodayDestination
import com.example.livingtogether.ui.today.TodayScreen

@Composable
fun TogetherNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = RatingDestination.route,
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
                title = ProfileDestination.titleRes
            )
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