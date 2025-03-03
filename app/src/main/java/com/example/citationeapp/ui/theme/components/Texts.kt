package com.example.citationeapp.ui.theme.components

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.citationeapp.ui.theme.black

@Composable
fun TextBottomBar(
    modifier: Modifier = Modifier,
    text: String = "",
    @StringRes textId: Int = -1,
    color: Color = black,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = (if (textId == -1) text else stringResource(id = textId)).uppercase(),
        modifier = modifier,
        color = color,
        maxLines = maxLines,
        textAlign = textAlign
    )
}