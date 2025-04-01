package com.example.livingtogether.ui.today

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.livingtogether.R
import com.example.livingtogether.data.Housework
import com.example.livingtogether.ui.AppViewModelProvider
import com.example.livingtogether.ui.TogetherTopBar
import com.example.livingtogether.ui.UsersHouseworkViewData
import com.example.livingtogether.ui.navigation.NavigationDestination

object TodayDestination : NavigationDestination {
    override val route = "today"
    override val titleRes = R.string.today
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TodayScreen(
    viewModel: TodayViewModel = viewModel(factory = AppViewModelProvider.Factory),
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
            val todayUiState by viewModel.uiState.collectAsState()
            val total = viewModel.total

            DoneList(
                modifier = Modifier.padding(innerPadding),
                housework = todayUiState.housework,
                total = total
            )

        }
    }
}

@Composable
fun DoneList(
    housework: List<UsersHouseworkViewData>,
    total: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        Row {
            Text(
                text = "Выполнено:",
                modifier = Modifier.weight(4f)
            )
            IconButton(
                onClick = {},
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null
                )
            }
        }

        LazyColumn(
            modifier = Modifier
        ) {
            items(housework) { housework ->
                Row {
                    Text(
                        text = housework.housework.name,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = housework.housework.cost,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

        }
        Row {
            Text(
                text = "Total:",
                modifier = modifier.weight(4f)
            )
            Text(
                text = total.toString(),
                modifier = modifier.weight(1f)
            )
        }
    }

}
