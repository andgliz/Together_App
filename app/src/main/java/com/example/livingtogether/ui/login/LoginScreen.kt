package com.example.livingtogether.ui.login

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.livingtogether.R
import com.example.livingtogether.ui.AppViewModelProvider
import com.example.livingtogether.ui.TogetherTopBar
import com.example.livingtogether.ui.navigation.NavigationDestination

object LoginDestination : NavigationDestination {
    override val route = "login"
    override val titleRes = R.string.login
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory),
    title: Int,
    onSuccess: () -> Unit,
    onRegistrationButtonClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TogetherTopBar(
                title = stringResource(title),
                modifier = Modifier,
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            val uiState by viewModel.uiState.collectAsState()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                TextField(
                    value = uiState.emailState,
                    label = { Text("Enter your email address") },
                    onValueChange = viewModel::onEmailChange,
                )
                Spacer(
                    modifier = Modifier.height(10.dp),
                )
                TextField(
                    value = uiState.passwordState,
                    label = { Text("Enter your password") },
                    onValueChange = viewModel::onPasswordChange,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
                if (uiState.errorState.isNotBlank()) {
                    Text(
                        text = uiState.errorState,
                        color = Color(0xFFAF0F1A),
                        modifier = Modifier
                            .padding(bottom = 8.dp),
                    )
                }
                Button(
                    onClick = {
                        viewModel.signIn(
                            email = uiState.emailState,
                            password = uiState.passwordState,
                            onSuccess = onSuccess,
                        )
                    },
                ) {
                    Text(
                        text = "Sign In",
                    )
                }
                TextButton(
                    onClick = {
                        onRegistrationButtonClick()
                    },
                ) {
                    Text(
                        text = "Create a new account",
                        modifier = Modifier,
                    )
                }
            }
        }
    }
}