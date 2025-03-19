package com.example.citationeapp.ui.theme.components

import IconButton
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.citationeapp.R
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.grey
import com.example.citationeapp.ui.theme.heightTextField
import com.example.citationeapp.ui.theme.lineHeightMedium
import com.example.citationeapp.ui.theme.padding8
import com.example.citationeapp.ui.theme.primary
import com.example.citationeapp.ui.theme.white

@Composable
fun TextInputWithButton(
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(white, RoundedCornerShape(padding8))
            .border(lineHeightMedium, primary, RoundedCornerShape(padding8)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
                            },
            modifier = Modifier
                .weight(1f)
                .height(heightTextField),
            placeholder = { TextBody1Regular(textId = R.string.play_question_place_holder, color = grey) },
            singleLine = true,
            colors = TextFieldDefaults.colors().copy(
                focusedTextColor = black,
                unfocusedTextColor = black,
                focusedContainerColor = white,
                unfocusedContainerColor = white,
                cursorColor = black,
                textSelectionColors = TextSelectionColors(
                    handleColor = black, backgroundColor = black.copy(alpha = 0.3f)
                ),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        IconButton(
            modifier = Modifier.size(heightTextField),
            iconId = R.drawable.ic_next,
            color = white,
            backgroundColor = primary,
            onClick = { onClick(text) },
        )
    }
}

@Composable
fun CheckableRow(
    text: String = "",
    @StringRes textId: Int = -1,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!isChecked) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        TextBody2Regular(
            text = (if (textId == -1) text else stringResource(id = textId)),
            modifier = Modifier.weight(3f).padding(start = padding8)
        )
        Checkbox(
            checked = isChecked,
            onCheckedChange = { onCheckedChange(it) },
            colors = CheckboxDefaults.colors(
                checkedColor = primary,
            )
        )
    }
}

@Composable
fun AuthTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    @StringRes label: Int = -1,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { TextBody2Regular(textId = label) },
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = primary.copy(alpha = 0.1f),
            focusedContainerColor = primary.copy(alpha = 0.1f),
            unfocusedIndicatorColor = black,
            focusedIndicatorColor = primary,
            cursorColor = black
        ),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}

