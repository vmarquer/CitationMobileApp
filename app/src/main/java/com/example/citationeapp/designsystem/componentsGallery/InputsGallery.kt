package com.example.citationeapp.designsystem.componentsGallery

import ButtonPrimary
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.citationeapp.R
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.components.AuthTextField
import com.example.citationeapp.ui.theme.components.CheckableRow
import com.example.citationeapp.ui.theme.components.DatePickerField
import com.example.citationeapp.ui.theme.components.TextBody2Regular
import com.example.citationeapp.ui.theme.components.TextH3Bold
import com.example.citationeapp.ui.theme.grey
import com.example.citationeapp.ui.theme.padding8
import com.example.citationeapp.ui.theme.primary
import com.example.citationeapp.ui.theme.spacing2
import com.example.citationeapp.ui.theme.spacing8
import com.example.citationeapp.ui.theme.white

@Composable
fun DesignSystemInputs(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    var checked by remember { mutableStateOf(true) }
    var filterChipSelected by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    var rangeSliderPosition by remember { mutableStateOf(0f..100f) }
    var switched by remember { mutableStateOf(true) }
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing2),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(vertical = padding8),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextH3Bold(text = "Inputs")
            Icon(
                imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = null,
                tint = grey
            )
        }
        AnimatedVisibility(visible = expanded) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(spacing2)
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { checked = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = primary,
                        uncheckedColor = black,
                        checkmarkColor = white,
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(spacing8, Alignment.CenterHorizontally)
                ) {
                    FilterChip(
                        onClick = { filterChipSelected = !filterChipSelected },
                        label = {
                            TextBody2Regular(text="Filter chip")
                        },
                        selected = filterChipSelected,
                        leadingIcon = if (filterChipSelected) {
                            {
                                Icon(
                                    imageVector = Icons.Filled.Done,
                                    contentDescription = null
                                )
                            }
                        } else null,
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = white,
                            labelColor = black,
                            selectedContainerColor = white,
                            selectedLabelColor = white,
                            selectedLeadingIconColor = black
                        )
                    )
                    ElevatedFilterChip(
                        onClick = { filterChipSelected = !filterChipSelected },
                        label = {
                            TextBody2Regular(text="Filter chip")
                        },
                        selected = filterChipSelected,
                        leadingIcon = if (filterChipSelected) {
                            {
                                Icon(
                                    imageVector = Icons.Filled.Done,
                                    contentDescription = null
                                )
                            }
                        } else null,
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = white,
                            labelColor = black,
                            selectedContainerColor = white,
                            selectedLabelColor = white,
                            selectedLeadingIconColor = black
                        )
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(spacing8, Alignment.CenterHorizontally)
                ) {
                    AssistChip(
                        onClick = {},
                        label = { TextBody2Regular(text = "Assist chip") },
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Settings,
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            Icon(
                                Icons.Filled.Settings,
                                contentDescription = null
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = white,
                            labelColor = black,
                            leadingIconContentColor = black,
                            trailingIconContentColor = black
                        )
                    )
                    ElevatedAssistChip(
                        onClick = {},
                        label = { TextBody2Regular(text = "Assist chip") },
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Settings,
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            Icon(
                                Icons.Filled.Settings,
                                contentDescription = null
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = white,
                            labelColor = black,
                            leadingIconContentColor = black,
                            trailingIconContentColor = black
                        )
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(spacing8, Alignment.CenterHorizontally)
                ) {
                    SuggestionChip(
                        onClick = {},
                        icon = {
                            Icon(
                                Icons.Filled.Settings,
                                contentDescription = null
                            )
                        },
                        label = { TextBody2Regular(text="Suggestion chip") },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = white,
                            labelColor = black,
                            iconContentColor = black
                        )
                    )
                    ElevatedSuggestionChip(
                        onClick = {},
                        icon = {
                            Icon(
                                Icons.Filled.Settings,
                                contentDescription = null
                            )
                        },
                        label = { TextBody2Regular(text="Suggestion chip") },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = white,
                            labelColor = black,
                            iconContentColor = black
                        )
                    )
                }
                DatePickerField(
                    label = "Date of Birth",
                    selectedDateMillis = selectedDate,
                    onDateSelected = { millis ->
                        selectedDate = millis
                    }
                )
                TextBody2Regular(
                    text = selectedDate?.let {
                        java.util.Date(it).toString()
                    } ?: "No date selected"
                )
                ButtonPrimary(
                    modifier = Modifier,
                    text = "Show Alert Dialog",
                    onClick = { showDialog = true }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(spacing8, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextBody2Regular(
                        text = "CircularProgressIndicator"
                    )
                    CircularProgressIndicator(
                        color = grey,
                        trackColor = primary,
                    )
                }
                Slider(
                    value = sliderPosition,
                    onValueChange = { sliderPosition = it },
                    colors = SliderDefaults.colors(
                        activeTrackColor = primary,
                        inactiveTrackColor = grey,
                        thumbColor = primary,
                        activeTickColor = primary,
                        inactiveTickColor = grey
                    )
                )
                TextBody2Regular(text = sliderPosition.toString())
                RangeSlider(
                    value = rangeSliderPosition,
                    steps = 10,
                    onValueChange = { range -> rangeSliderPosition = range },
                    valueRange = 0f..100f,
                    onValueChangeFinished = {},
                    colors = SliderDefaults.colors(
                        activeTrackColor = primary,
                        inactiveTrackColor = grey,
                        thumbColor = primary,
                        activeTickColor = primary,
                        inactiveTickColor = grey
                    )
                )
                TextBody2Regular(text = rangeSliderPosition.toString())
                Switch(
                    checked = switched,
                    onCheckedChange = {
                        switched = it
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = white,
                        checkedTrackColor = primary
                    )
                )
                var isChecked by remember { mutableStateOf(false) }
                CheckableRow(
                    text = "CheckableRow",
                    isChecked = isChecked,
                    onCheckedChange = {
                        isChecked = it
                    }
                )

                var text by remember { mutableStateOf("") }
                AuthTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = R.string.field_email,
                    icon = Icons.Rounded.Email
                )
            }
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null
                )
            },
            title = {
                TextBody2Regular(text = "Delete item?")
            },
            text = {
                TextBody2Regular(text = "Are you sure you want to delete this item? This action cannot be undone.")
            },
            confirmButton = {
                ButtonPrimary(
                    modifier = Modifier,
                    text = "Delete",
                    onClick = { showDialog = false }
                )
            },
            dismissButton = {
                ButtonPrimary(
                    modifier = Modifier,
                    text = "Cancel",
                    onClick = { showDialog = false }
                )
            }
        )
    }
}