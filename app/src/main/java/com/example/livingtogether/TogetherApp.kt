package com.example.livingtogether

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.livingtogether.ui.AppViewModelProvider
import com.example.livingtogether.ui.BottomMenuItem
import com.example.livingtogether.ui.navigation.NavigationBar
import com.example.livingtogether.ui.navigation.TogetherNavGraph

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TogetherApp(
    navController: NavHostController = rememberNavController(),
    viewModel: TogetherViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val isUserAuthorized = viewModel.isUserAuthorized
    val isUserInFamily = viewModel.isUserInFamily

    val items = listOf(
        BottomMenuItem.RatingItem,
        BottomMenuItem.TodayItem,
        BottomMenuItem.HouseworkItem,
        BottomMenuItem.ProfileItem
    )

    Scaffold(
        bottomBar = {
            if (isUserAuthorized && isUserInFamily) {
                NavigationBar(
                    items = items,
                    navController = navController,
                )
            }
        },
    ) {
        TogetherNavGraph(
            navController = navController,
            isAuth = isUserAuthorized,
            isUserInFamily = isUserInFamily,
        )
    }
}
