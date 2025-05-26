package com.example.livingtogether.ui.rating

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.livingtogether.R
import com.example.livingtogether.ui.AppViewModelProvider
import com.example.livingtogether.ui.TogetherTopBar
import com.example.livingtogether.ui.UserToRatingData
import com.example.livingtogether.ui.navigation.NavigationDestination
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object RatingDestination : NavigationDestination {
    override val route = "rating"
    override val titleRes = R.string.rating
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
            var showModal by remember { mutableStateOf(false) }
            val uiState by viewModel.uiState.collectAsState()

            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                OutlinedTextField(
                    value = convertMillisToDate(uiState.startDate.toString()) + " - " + convertMillisToDate(
                        uiState.endDate.toString()
                    ),
                    onValueChange = { },
                    label = { Text("DOB") },
                    placeholder = { Text("MM/DD/YYYY") },
                    trailingIcon = {
                        Icon(Icons.Default.DateRange, contentDescription = "Select date")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .pointerInput(uiState.startDate) {
                            awaitEachGesture {
                                awaitFirstDown(pass = PointerEventPass.Initial)
                                val upEvent =
                                    waitForUpOrCancellation(pass = PointerEventPass.Initial)
                                if (upEvent != null) {
                                    showModal = true
                                }
                            }
                        }
                )
                if (showModal) {
                    DateRangePickerModal(
                        onDateRangeSelected = {
                            viewModel.changeDate(Date(it.first), Date(it.second))
                        },
                        onDismiss = { showModal = false }
                    )
                }
                Text(
                    text = "Rating:"
                )

                RatingList(
                    ratingList = uiState.userToRatings
                )
            }
        }
    }
}

@Composable
fun RatingList(
    ratingList: List<UserToRatingData>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 80.dp, vertical = 8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(ratingList) { user ->
            UserItem(
                userToRatings = user,
                modifier = modifier
            )
        }
    }
}

@Composable
fun UserItem(
    userToRatings: UserToRatingData,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(vertical = 2.dp)
    ) {
        Text(
            text = userToRatings.userName,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = userToRatings.totalRating.toString(),
            modifier = Modifier.weight(1f)
        )
    }
}

fun convertMillisToDate(millis: String): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}
