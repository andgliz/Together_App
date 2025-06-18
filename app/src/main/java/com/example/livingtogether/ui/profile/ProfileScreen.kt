package com.example.livingtogether.ui.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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

object ProfileDestination : NavigationDestination {
    override val route = "profile"
    override val titleRes = R.string.profile
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory),
    title: Int,
    onDeleteAccountSuccess: () -> Unit,
    onChangeFamilySuccess: () -> Unit,
) {
    Scaffold(
        topBar = {
            TogetherTopBar(
                title = stringResource(title),
                modifier = Modifier,
            )
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, end = 16.dp),
            ) {
                FloatingActionButton(
                    onClick = { viewModel.signOut(onDeleteAccountSuccess) },
                    modifier = Modifier,
                    containerColor = Color.Unspecified,
                ) {
                    Icon(
                        imageVector = Icons.Filled.ExitToApp,
                        contentDescription = null,
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { innerPadding ->
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            val profileUiState by viewModel.uiState.collectAsState()
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
            ) {
                NameCard(
                    text = "Your name: ",
                    name = profileUiState.userName,
                )
                NameChangingTextField(
                    text = "Change name",
                    value = profileUiState.user.name,
                    onValueChange = viewModel::onNameInputChanged,
                    onButtonClick = { viewModel.onChangeNameButtonClicked() },
                )
                NameCard(
                    text = "Your family: ",
                    name = profileUiState.familyName,
                )
                NameChangingTextField(
                    text = "Change family name",
                    value = profileUiState.user.family,
                    onValueChange = viewModel::onFamilyNameInputChanged,
                    onButtonClick = { viewModel.onChangeFamilyNameButtonClicked() },
                )
                if (profileUiState.errorState.isNotBlank()) {
                    Text(
                        text = profileUiState.errorState,
                        color = Color(0xFFAF0F1A),
                    )
                }
                Button(
                    onClick = { viewModel.onChangeFamilyClicked(onChangeFamilySuccess) },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(160.dp)
                        .padding(bottom = 8.dp),
                ) {
                    Text(
                        text = "Change family",
                    )
                }
                Button(
                    onClick = { viewModel.onDeleteAccountClicked(onDeleteAccountSuccess) },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(160.dp),
                ) {
                    Text(
                        text = "Delete account",
                    )
                }
            }
        }
    }
}

@Composable
fun NameCard(
    text: String,
    name: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier.padding(bottom = 8.dp),
    ) {
        Text(
            text = text,
        )
        Text(
            text = name,
        )
    }
}

@Composable
fun NameChangingTextField(
    text: String,
    value: String,
    onValueChange: (String) -> Unit,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        TextField(
            value = value,
            label = { Text(text) },
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(3f)
                .padding(end = 8.dp),
        )
        FloatingActionButton(
            onClick = { onButtonClick() },
            modifier = Modifier.weight(0.55f),
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
            )
        }
    }
}
