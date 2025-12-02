package com.example.citationeapp.ui.theme.components

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.body1Bold
import com.example.citationeapp.ui.theme.body1Italic
import com.example.citationeapp.ui.theme.body1Regular
import com.example.citationeapp.ui.theme.body2Bold
import com.example.citationeapp.ui.theme.body2Italic
import com.example.citationeapp.ui.theme.body2Regular
import com.example.citationeapp.ui.theme.body3Bold
import com.example.citationeapp.ui.theme.body3Italic
import com.example.citationeapp.ui.theme.body3Regular
import com.example.citationeapp.ui.theme.h1
import com.example.citationeapp.ui.theme.h1Bold
import com.example.citationeapp.ui.theme.h2
import com.example.citationeapp.ui.theme.h2Bold
import com.example.citationeapp.ui.theme.h3
import com.example.citationeapp.ui.theme.h3Bold

@Composable
fun TextBottomBar(
    modifier: Modifier = Modifier,
    text: String = "",
    @StringRes textId: Int = -1,
    color: Color = black,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        text = (if (textId == -1) text else stringResource(id = textId)),
        modifier = modifier,
        color = color,
        maxLines = maxLines,
        textAlign = textAlign
    )
}

@Composable
fun TextH1(
    modifier: Modifier = Modifier,
    text: String = "",
    @StringRes textId: Int = -1,
    color: Color = black,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = if (textId == -1) text else stringResource(id = textId),
        modifier = modifier,
        color = color,
        style = h1,
        textAlign = textAlign
    )
}

@Composable
fun TextH2(
    modifier: Modifier = Modifier,
    text: String = "",
    @StringRes textId: Int = -1,
    color: Color = black,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = if (textId == -1) text else stringResource(id = textId),
        modifier = modifier,
        color = color,
        style = h2,
        textAlign = textAlign
    )
}

@Composable
fun TextH3(
    modifier: Modifier = Modifier,
    text: String = "",
    @StringRes textId: Int = -1,
    color: Color = black,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = if (textId == -1) text else stringResource(id = textId),
        modifier = modifier,
        color = color,
        style = h3,
        textAlign = textAlign
    )
}

@Composable
fun TextH1Bold(
    modifier: Modifier = Modifier,
    text: String = "",
    @StringRes textId: Int = -1,
    color: Color = black,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = if (textId == -1) text else stringResource(id = textId),
        modifier = modifier,
        color = color,
        style = h1Bold,
        textAlign = textAlign
    )
}

@Composable
fun TextH2Bold(
    modifier: Modifier = Modifier,
    text: String = "",
    @StringRes textId: Int = -1,
    color: Color = black,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = if (textId == -1) text else stringResource(id = textId),
        modifier = modifier,
        color = color,
        style = h2Bold,
        textAlign = textAlign
    )
}

@Composable
fun TextH3Bold(
    modifier: Modifier = Modifier,
    text: String = "",
    @StringRes textId: Int = -1,
    color: Color = black,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = if (textId == -1) text else stringResource(id = textId),
        modifier = modifier,
        color = color,
        style = h3Bold,
        textAlign = textAlign
    )
}

@Composable
fun TextBody1Regular(
    modifier: Modifier = Modifier,
    text: String = "",
    @StringRes textId: Int = -1,
    color: Color = black,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = if (textId == -1) text else stringResource(id = textId),
        modifier = modifier,
        color = color,
        style = body1Regular,
        textAlign = textAlign
    )
}

@Composable
fun TextBody1Bold(
    modifier: Modifier = Modifier,
    text: String = "",
    @StringRes textId: Int = -1,
    color: Color = black,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = if (textId == -1) text else stringResource(id = textId),
        modifier = modifier,
        color = color,
        style = body1Bold,
        textAlign = textAlign
    )
}

@Composable
fun TextBody1Italic(
    modifier: Modifier = Modifier,
    text: String = "",
    @StringRes textId: Int = -1,
    color: Color = black,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = if (textId == -1) text else stringResource(id = textId),
        modifier = modifier,
        color = color,
        style = body1Italic,
        textAlign = textAlign
    )
}

@Composable
fun TextBody2Regular(
    modifier: Modifier = Modifier,
    text: String = "",
    @StringRes textId: Int = -1,
    color: Color = black,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = if (textId == -1) text else stringResource(id = textId),
        modifier = modifier,
        color = color,
        style = body2Regular,
        textAlign = textAlign
    )
}

@Composable
fun TextBody2Bold(
    modifier: Modifier = Modifier,
    text: String = "",
    @StringRes textId: Int = -1,
    color: Color = black,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = if (textId == -1) text else stringResource(id = textId),
        modifier = modifier,
        color = color,
        style = body2Bold,
        textAlign = textAlign
    )
}

@Composable
fun TextBody2Italic(
    modifier: Modifier = Modifier,
    text: String = "",
    @StringRes textId: Int = -1,
    color: Color = black,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = if (textId == -1) text else stringResource(id = textId),
        modifier = modifier,
        color = color,
        style = body2Italic,
        textAlign = textAlign
    )
}

@Composable
fun TextBody3Regular(
    modifier: Modifier = Modifier,
    text: String = "",
    @StringRes textId: Int = -1,
    color: Color = black,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = if (textId == -1) text else stringResource(id = textId),
        modifier = modifier,
        color = color,
        style = body3Regular,
        textAlign = textAlign
    )
}

@Composable
fun TextBody3Bold(
    modifier: Modifier = Modifier,
    text: String = "",
    @StringRes textId: Int = -1,
    color: Color = black,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = if (textId == -1) text else stringResource(id = textId),
        modifier = modifier,
        color = color,
        style = body3Bold,
        textAlign = textAlign
    )
}

@Composable
fun TextBody3Italic(
    modifier: Modifier = Modifier,
    text: String = "",
    @StringRes textId: Int = -1,
    color: Color = black,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = if (textId == -1) text else stringResource(id = textId),
        modifier = modifier,
        color = color,
        style = body3Italic,
        textAlign = textAlign
    )
}