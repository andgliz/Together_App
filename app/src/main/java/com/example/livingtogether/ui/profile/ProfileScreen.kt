package com.example.livingtogether.ui.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.livingtogether.R
import com.example.livingtogether.ui.AppViewModelProvider
import com.example.livingtogether.ui.TogetherTopBar
import com.example.livingtogether.ui.navigation.NavigationDestination

object ProfileDestination : NavigationDestination {
    override val route = "profile"
    override val titleRes = R.string.profile
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory),
    title: Int,
    onSuccess: () -> Unit
) {
    Scaffold(
        topBar = {
            TogetherTopBar(
                title = stringResource(title),
                modifier = Modifier
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            val uiState by viewModel.uiState.collectAsState()
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                Text(
                    text = "You"
                )
                Button(
                    onClick = { viewModel.signOut(onSuccess) }
                ) {
                    Text(
                        text = "Sign Out"
                    )
                }
                Button(
                    onClick = { viewModel.onDeleteAccountClicked() }
                ) {
                    Text(
                        text = "Delete Account"
                    )
                }
            }
        }
    }


}