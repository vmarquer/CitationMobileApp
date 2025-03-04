package com.example.citationeapp.ui.theme.components

import IconButton
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
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
    onSendClick: (String) -> Unit
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
            onValueChange = { text = it },
            modifier = Modifier
                .weight(1f)
                .height(heightTextField),
            placeholder = { TextH3(textId = R.string.play_question_place_holder, color = grey) },
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
            onClick = { onSendClick(text) },
        )
    }
}
