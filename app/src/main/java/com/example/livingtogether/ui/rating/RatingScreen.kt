package com.example.livingtogether.ui.rating

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.livingtogether.R
import com.example.livingtogether.ui.AppViewModelProvider
import com.example.livingtogether.ui.TogetherTopBar
import com.example.livingtogether.ui.UserViewData
import com.example.livingtogether.ui.navigation.NavigationDestination

object RatingDestination : NavigationDestination {
    override val route = "rating"
    override val titleRes = R.string.rating
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingScreen(
    viewModel: RatingViewModel = viewModel(factory = AppViewModelProvider.Factory),
    title: Int
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
                    text = "Rating:"
                )

                RatingList(
                    users = uiState.usersList
                )
            }
        }
    }
}

@Composable
fun RatingList(
    users: List<UserViewData>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 80.dp, vertical = 8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(users) { user ->
            UserItem(
                user,
                modifier = modifier
            )
        }
    }
}

@Composable
fun UserItem(
    user: UserViewData,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(vertical = 2.dp)
    ) {
        Text(
            text = user.name,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = user.total,
            modifier = Modifier.weight(1f)
        )
    }
}
