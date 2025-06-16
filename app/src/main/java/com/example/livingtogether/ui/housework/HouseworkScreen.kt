package com.example.livingtogether.ui.housework

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.livingtogether.R
import com.example.livingtogether.ui.AppViewModelProvider
import com.example.livingtogether.ui.HouseworkViewData
import com.example.livingtogether.ui.TogetherTopBar
import com.example.livingtogether.ui.navigation.NavigationDestination

object HouseworkDestination : NavigationDestination {
    override val route = "housework"
    override val titleRes = R.string.housework
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HouseworkScreen(
    viewModel: HouseworkViewModel = viewModel(factory = AppViewModelProvider.Factory),
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
            val openAddDialog = remember { mutableStateOf(false) }
            val openUpdateDialog = remember { mutableStateOf(false) }

            val uiState by viewModel.uiState.collectAsState()

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Top,
            ) {
                Row(
                    modifier = Modifier,
                ) {
                    Text(
                        text = "Name of housework",
                        modifier = Modifier.weight(3.5f)
                    )
                    Text(
                        text = "Cost",
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                HouseworkList(
                    housework = uiState.houseworkList,
                    onDropButtonClick = viewModel::onDeleteHouseworkClicked,
                    onClickHousework = {
                        viewModel.updateUiState(it)
                        openUpdateDialog.value = true
                    },
                    modifier = Modifier.height(624.dp)
                )
                AddButton(
                    onClickAddButton = { openAddDialog.value = true },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 8.dp)
                )
            }
            if (openAddDialog.value) {
                Dialog(
                    onValueChanged = viewModel::updateUiState,
                    onConfirmation = {
                        viewModel.onAddHouseworkClicked()
                        openAddDialog.value = false
                    },
                    onDismissRequest = {
                        openAddDialog.value = false
                        viewModel.updateUiState(HouseworkViewData())
                    },
                    houseworkInput = uiState.houseworkInput,
                    enabled = uiState.isEnabled,
                    dialogTitle = stringResource(R.string.add_title),
                    buttonText = stringResource(R.string.add)
                )
            }

            if (openUpdateDialog.value) {
                Dialog(
                    onValueChanged = viewModel::updateUiState,
                    onConfirmation = {
                        viewModel.onHouseworkClicked()
                        openUpdateDialog.value = false
                    },
                    onDismissRequest = {
                        openUpdateDialog.value = false
                        viewModel.updateUiState(HouseworkViewData())
                    },
                    houseworkInput = uiState.houseworkInput,
                    enabled = uiState.isEnabled,
                    dialogTitle = stringResource(R.string.update_title),
                    buttonText = stringResource(R.string.update)
                )
            }
        }
    }
}

@Composable
fun HouseworkList(
    housework: List<HouseworkViewData>,
    onDropButtonClick: (HouseworkViewData) -> Unit,
    onClickHousework: (HouseworkViewData) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(housework) { housework ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(
                    onClick = { onClickHousework(housework) },
                    modifier = Modifier.weight(4.5f)
                ) {
                    Text(
                        text = housework.name,
                        modifier = Modifier.weight(3.5f),
                        fontSize = 16.sp
                    )
                    Text(
                        text = housework.cost,
                        modifier = Modifier.weight(1f),
                        fontSize = 16.sp,
                    )
                }

                IconButton(
                    onClick = { onDropButtonClick(housework) },
                    modifier = Modifier.weight(0.5f)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
fun AddButton(
    modifier: Modifier = Modifier,
    onClickAddButton: () -> Unit
) {
    Row(modifier = modifier) {
        FloatingActionButton(
            onClick = onClickAddButton
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Добавить"
            )
        }
    }
}
