package com.example.citationeapp.ui.theme.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.padding8
import com.example.citationeapp.ui.theme.primary

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
            modifier = Modifier
                .weight(3f)
                .padding(start = padding8)
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
    icon: ImageVector? = null,
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
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        leadingIcon = icon?.let {
            {
                Icon(
                    imageVector = icon,
                    tint = black,
                    contentDescription = null
                )
            }
        }
    )
}

