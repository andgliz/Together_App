package com.example.livingtogether.ui.housework

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.livingtogether.R
import com.example.livingtogether.ui.HouseworkViewData

@Composable
fun Dialog(
    onValueChanged: (HouseworkViewData) -> Unit,
    houseworkInput: HouseworkViewData,
    dialogTitle: String,
    buttonText: String,
    enabled: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = dialogTitle
                )
                HouseworkEntryOrEdit(
                    onValueChanged = onValueChanged,
                    houseworkInput = houseworkInput
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.cancel)
                        )
                    }
                    TextButton(
                        onClick = { onConfirmation() },
                        enabled = enabled,
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(
                            text = buttonText
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HouseworkEntryOrEdit(
    onValueChanged: (HouseworkViewData) -> Unit,
    houseworkInput: HouseworkViewData

) {
    OutlinedTextField(
        value = houseworkInput.name,
        onValueChange = { onValueChanged(houseworkInput.copy(name = it)) },
        label = { Text(stringResource(R.string.housework)) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        singleLine = true
    )

    OutlinedTextField(
        value = houseworkInput.cost,
        onValueChange = { onValueChanged(houseworkInput.copy(cost = it)) },
        label = { Text(stringResource(R.string.cost)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        singleLine = true
    )
}

