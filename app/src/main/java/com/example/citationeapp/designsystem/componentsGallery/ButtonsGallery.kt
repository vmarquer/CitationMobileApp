package com.example.citationeapp.designsystem.componentsGallery

import AnswerButton
import ButtonPrimary
import ButtonSecondary
import FloatingButton
import SingleChoiceSegmentedButton
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.components.TextBody2Regular
import com.example.citationeapp.ui.theme.components.TextH3Bold
import com.example.citationeapp.ui.theme.grey
import com.example.citationeapp.ui.theme.padding8
import com.example.citationeapp.ui.theme.primary
import com.example.citationeapp.ui.theme.spacing2
import com.example.citationeapp.ui.theme.white

data class SegmentedButtonOption(val text: String)

@Composable
fun DesignSystemButtons(modifier: Modifier = Modifier) {
    val segmentedButtonOptions = listOf(
        SegmentedButtonOption("Option 1"),
        SegmentedButtonOption("Option 2"),
        SegmentedButtonOption("Option 3")
    )
    var segmentedButtonSelectedOption by remember { mutableStateOf(segmentedButtonOptions[0]) }
    var expanded by remember { mutableStateOf(false) }
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
            TextH3Bold(text = "Buttons")
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
                ButtonPrimary(
                    modifier = Modifier,
                    text = "ButtonPrimary"
                )
                ButtonSecondary(
                    modifier = Modifier,
                    text = "ButtonSecondary"
                )
                AnswerButton(
                    text = "AnswerButton",
                    onClick = {},
                    enabled = true,
                    backgroundColor = primary.copy(0.7f),
                    borderColor = black,
                    textColor = white
                )
                FloatingButton(
                    icon = Icons.Default.Add
                )
                SingleChoiceSegmentedButton(
                    options = segmentedButtonOptions,
                    selectedOption = segmentedButtonSelectedOption,
                    getText = { it.text },
                    onSelectionChanged = { selectedOption ->
                        segmentedButtonSelectedOption = selectedOption
                    }
                )
                TextBody2Regular(text = "Selected: ${segmentedButtonSelectedOption.text}")
            }
        }
    }
}