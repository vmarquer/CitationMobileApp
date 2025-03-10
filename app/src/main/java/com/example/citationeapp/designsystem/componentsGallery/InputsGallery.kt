package com.example.citationeapp.designsystem.componentsGallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.citationeapp.ui.theme.components.TextH1
import com.example.citationeapp.ui.theme.components.TextH2
import com.example.citationeapp.ui.theme.components.TextInputWithButton
import com.example.citationeapp.ui.theme.spacing10

@Composable
fun DesignSystemInputs(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing10),
    ) {
        TextH2(text = "Inputs")

        TextInputWithButton(
            onClick = {}
        )
    }
}