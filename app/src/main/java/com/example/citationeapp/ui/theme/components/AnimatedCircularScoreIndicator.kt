package com.example.citationeapp.ui.theme.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.citationeapp.ui.theme.grey
import com.example.citationeapp.ui.theme.lineHeightLarge
import com.example.citationeapp.ui.theme.lineHeightMedium
import com.example.citationeapp.ui.theme.padding2
import com.example.citationeapp.ui.theme.progressColor

@Composable
fun AnimatedCircularScoreIndicator(
    goodAnswers: Int,
    totalAnswers: Int,
    modifier: Modifier = Modifier
) {
    val ratio = if (totalAnswers > 0) {
        (goodAnswers.toFloat() / totalAnswers.toFloat()).coerceIn(0f, 1f)
    } else 0f
    var animatedTarget by remember { mutableStateOf(0f) }
    LaunchedEffect(ratio) {
        animatedTarget = ratio
    }
    val animatedProgress by animateFloatAsState(
        targetValue = animatedTarget,
        animationSpec = tween(
            durationMillis = 700,
            easing = FastOutSlowInEasing
        ),
        label = "scoreAnim"
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        CircularProgressIndicator(
            progress = { 1f },
            strokeWidth = lineHeightMedium,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding2),
            color = grey.copy(alpha = 0.3f)
        )
        CircularProgressIndicator(
            progress = { animatedProgress },
            strokeWidth = lineHeightLarge,
            modifier = Modifier.fillMaxSize(),
            color = progressColor(ratio)
        )
        TextBody1Bold(
            text = "$goodAnswers/$totalAnswers",
        )
    }
}
