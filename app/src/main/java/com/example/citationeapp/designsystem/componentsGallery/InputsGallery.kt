package com.example.citationeapp.designsystem.componentsGallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.citationeapp.R
import com.example.citationeapp.ui.theme.components.AuthTextField
import com.example.citationeapp.ui.theme.components.CheckableRow
import com.example.citationeapp.ui.theme.components.TextH3Bold
import com.example.citationeapp.ui.theme.spacing10

@Composable
fun DesignSystemInputs(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing10),
    ) {
        TextH3Bold(text = "Inputs")

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
            label = R.string.portal_label_email,
            icon = Icons.Rounded.Email
        )

    }
}