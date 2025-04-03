package com.example.livingtogether

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.livingtogether.ui.AppViewModelProvider
import com.example.livingtogether.ui.navigation.TogetherNavGraph

@Composable
fun TogetherApp(
    viewModel: TogetherViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    TogetherNavGraph(
        isAuth = viewModel.isAuth
    )
}
