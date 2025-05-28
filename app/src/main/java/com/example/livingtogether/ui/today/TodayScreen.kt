package com.example.livingtogether.ui.today

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.livingtogether.R
import com.example.livingtogether.ui.AppViewModelProvider
import com.example.livingtogether.ui.HouseworkViewData
import com.example.livingtogether.ui.TogetherTopBar
import com.example.livingtogether.ui.navigation.NavigationDestination

object TodayDestination : NavigationDestination {
    override val route = "today"
    override val titleRes = R.string.today
}

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
            val openHouseworkListDialog = remember { mutableStateOf(false) }
            val showModal = remember { mutableStateOf(false) }

            DoneList(
                modifier = Modifier.padding(innerPadding),
                housework = todayUiState.housework,
                total = total,
                currentDate = todayUiState.selectedDate,
                onPlusButtonClick = {
                    openHouseworkListDialog.value = true
                    viewModel.onPlusButtonClicked()
                },
                onDeleteButtonClick = viewModel::onDeleteClicked,
                onCalendarButtonClick = { showModal.value = true }
            )

            if (showModal.value) {
                DatePickerModal(
                    onDateSelected = viewModel::onChangeDate,
                    onDismiss = {
                        showModal.value = false
                    }
                )
            }

            if (openHouseworkListDialog.value) {
                Dialog(
                    onConfirmation = {
                        viewModel.onHouseworkClicked(it)
                        openHouseworkListDialog.value = false
                    },
                    onDismissRequest = {
                        openHouseworkListDialog.value = false
                    },
                    houseworkList = todayUiState.houseworkList,
                    dialogTitle = stringResource(R.string.add_title),
                    buttonText = stringResource(R.string.add)
                )
            }
        }
    }
}

@Composable
fun DoneList(
    housework: List<HouseworkViewData>,
    total: Int,
    currentDate: String,
    onPlusButtonClick: () -> Unit,
    onDeleteButtonClick: (HouseworkViewData) -> Unit,
    modifier: Modifier = Modifier,
    onCalendarButtonClick: () -> Unit
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        Row {
            Text(
                text = "Выберите дату:"
            )
            OutlinedTextField(
                value = currentDate,
                onValueChange = { },
                label = { Text("DOB") },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { onCalendarButtonClick() }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Select date"
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
            )
        }
        Row {

            Text(
                text = "Выполнено:",
                modifier = Modifier.weight(4f)
            )
            IconButton(
                onClick = { onPlusButtonClick() },
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
                        text = housework.name,
                        modifier = Modifier.weight(2f)
                    )
                    Text(
                        text = housework.cost,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = { onDeleteButtonClick(housework) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = null
                        )
                    }
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
