package com.example.citationeapp.designsystem.componentsGallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.citationeapp.ui.theme.components.CheckableRow
import com.example.citationeapp.ui.theme.components.TextH3Bold
import com.example.citationeapp.ui.theme.components.TextInputWithButton
import com.example.citationeapp.ui.theme.spacing10

@Composable
fun DesignSystemInputs(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing10),
    ) {
        TextH3Bold(text = "Inputs")

        TextInputWithButton(
            onClick = {}
        )

        var isChecked by remember { mutableStateOf(false) }
        CheckableRow(
            text = "CheckableRow",
            isChecked = isChecked,
            onCheckedChange = {
                isChecked = it
            }
        )

    }
}