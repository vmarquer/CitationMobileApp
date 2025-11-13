package com.example.citationeapp.ui.theme.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.dimZero
import com.example.citationeapp.ui.theme.lineHeightMedium
import com.example.citationeapp.ui.theme.padding6
import com.example.citationeapp.ui.theme.white

@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = white,
    contentColor: Color = black,
    borderColor: Color = Color.Transparent,
    content: @Composable () -> Unit
) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = padding6
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .border(
                width = if (borderColor != Color.Transparent) lineHeightMedium else dimZero,
                color = borderColor,
                shape = RoundedCornerShape(padding6)
            )
    ) {
        content()
    }
}