package com.example.livingtogether

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.livingtogether.ui.housework.HouseworkDestination
import com.example.livingtogether.ui.profile.ProfileDestination
import com.example.livingtogether.ui.rating.RatingDestination
import com.example.livingtogether.ui.today.TodayDestination
import com.example.livingtogether.ui.navigation.NavigationBar
import com.example.livingtogether.ui.navigation.TogetherNavGraph

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TogetherApp(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = {
            NavigationBar(
                onRatingClick = { navController.navigate(RatingDestination.route) },
                onTodayClick = { navController.navigate(TodayDestination.route) },
                onHouseworkClick = { navController.navigate(HouseworkDestination.route) },
                onProfileClick = { navController.navigate(ProfileDestination.route) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            TogetherNavGraph(
                navController = navController
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TogetherTopAppBar(
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) },
        modifier = modifier
    )
}