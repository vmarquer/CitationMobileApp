package com.example.citationeapp.designsystem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.citationeapp.designsystem.componentsGallery.DesignSystemButtons
import com.example.citationeapp.designsystem.componentsGallery.DesignSystemColors
import com.example.citationeapp.designsystem.componentsGallery.DesignSystemInputs
import com.example.citationeapp.designsystem.componentsGallery.DesignSystemLabels
import com.example.citationeapp.ui.theme.padding16
import com.example.citationeapp.ui.theme.spacing16

@Composable
fun DesignSystem(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding16)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(spacing16)
    ) {
        DesignSystemInputs()
        DesignSystemButtons()
        DesignSystemLabels()
        DesignSystemColors()
    }
}