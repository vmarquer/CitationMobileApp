package com.example.citationeapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun CustomBox(
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment = Alignment.TopStart,
    height: Dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(white, shape = RoundedCornerShape(padding8))
            .border(lineHeightMedium, primary, shape = RoundedCornerShape(padding8))
            .padding(padding16)
            .height(height),
        contentAlignment = verticalAlignment,
    ) {
        content()
    }
}
