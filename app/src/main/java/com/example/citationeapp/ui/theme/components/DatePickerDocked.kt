package com.example.citationeapp.ui.theme.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                },
                enabled = datePickerState.selectedDateMillis != null
            ) {
                TextBody2Regular(text="OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                TextBody2Regular(text="Cancel")
            }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    label: String,
    modifier: Modifier = Modifier,
    selectedDateMillis: Long?,
    onDateSelected: (Long?) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    val displayText = selectedDateMillis?.let {
        val date = java.util.Date(it)
        java.text.SimpleDateFormat("MM/dd/yyyy", java.util.Locale.getDefault()).format(date)
    } ?: ""

    OutlinedTextField(
        value = displayText,
        onValueChange = { },
        label = { TextBody2Regular(text=label) },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { showDialog = true }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null
                )
            }
        },
        modifier = modifier.fillMaxWidth()
    )

    if (showDialog) {
        DatePickerModal(
            onDateSelected = { millis ->
                onDateSelected(millis)
            },
            onDismiss = { showDialog = false }
        )
    }
}