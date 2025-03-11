package com.example.citationeapp.designsystem.componentsGallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.components.TextBody1Bold
import com.example.citationeapp.ui.theme.components.TextBody2Regular
import com.example.citationeapp.ui.theme.components.TextH2
import com.example.citationeapp.ui.theme.components.TextH3Bold
import com.example.citationeapp.ui.theme.dark_blue
import com.example.citationeapp.ui.theme.fail
import com.example.citationeapp.ui.theme.grey
import com.example.citationeapp.ui.theme.primary
import com.example.citationeapp.ui.theme.spacing10
import com.example.citationeapp.ui.theme.success
import com.example.citationeapp.ui.theme.white
import kotlin.math.floor
import kotlin.math.sqrt

@Composable
fun DesignSystemColors(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing10),
    ) {
        TextH3Bold(text = "Colors")

        val colors = listOf(
            Pair("Primary", primary),
            Pair("White", white),
            Pair("Black", black),
            Pair("Grey", grey),
            Pair("DarkBlue", dark_blue),
            Pair("Success", success),
            Pair("Fail", fail),
        )

        // Couleurs dans des carrés
        val spacerSize = 80.dp
        val circleRadius = with(LocalDensity.current) { (spacerSize / 2).toPx() }
        val colorPerLine: Int = floor(sqrt(colors.size.toFloat())).toInt()

        LazyVerticalGrid(
            columns = GridCells.Fixed(colorPerLine),
            modifier = modifier.heightIn(max = 500.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(colors.size) { index ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = modifier
                        .size(width = spacerSize, height = spacerSize)
                        .drawBehind {
                            drawCircle(
                                color = colors[index].second,
                                radius = circleRadius
                            )
                        })

                    TextBody1Bold(text = String.format("#%08X", colors[index].second.toArgb()))
                    TextBody2Regular(text = colors[index].first)
                }
            }
        }
    }
}