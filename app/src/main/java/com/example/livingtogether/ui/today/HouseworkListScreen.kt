package com.example.livingtogether.ui.today

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.livingtogether.R
import com.example.livingtogether.ui.HouseworkViewData

@Composable
fun Dialog(
    houseworkList: List<HouseworkViewData>,
    dialogTitle: String,
    buttonText: String,
    onDismissRequest: () -> Unit,
    onConfirmation: (HouseworkViewData) -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = dialogTitle,
                    modifier = Modifier.weight(0.6f),
                )
                LazyColumn(modifier = Modifier.weight(3.5f)) {
                    items(houseworkList) { housework ->
                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = housework.name,
                                modifier = Modifier.weight(3f),
                            )
                            Text(
                                text = housework.cost,
                                modifier = Modifier.weight(1f),
                            )
                            TextButton(
                                onClick = { onConfirmation(housework) },
                                modifier = Modifier
                                    .weight(1f),
                            ) {
                                Text(
                                    text = buttonText,
                                )
                            }
                        }
                    }
                }
                TextButton(
                    onClick = { onDismissRequest() },
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .weight(1f),
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                        fontSize = 16.sp,
                    )
                }
            }
        }
    }
}
