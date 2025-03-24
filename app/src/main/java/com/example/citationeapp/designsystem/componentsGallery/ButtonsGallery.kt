package com.example.citationeapp.designsystem.componentsGallery

import AnswerButton
import ButtonPrimary
import ProfileButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.components.TextH3Bold
import com.example.citationeapp.ui.theme.grey
import com.example.citationeapp.ui.theme.primary
import com.example.citationeapp.ui.theme.spacing10


@Composable
fun DesignSystemButtons(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing10),
    ) {
        TextH3Bold(text = "Buttons")

        ButtonPrimary(
            modifier = Modifier,
            text = "ButtonPrimary",
            onClick = {}
        )

        AnswerButton(
            text = "AnswerButton",
            onClick = {},
            enabled = true,
            backgroundColor = primary.copy(0.7f),
            borderColor = black
        )

        ProfileButton(
            modifier = Modifier.fillMaxWidth(),
            text = "ProfileButton",
            onClick = {},
            iconId = Icons.Filled.Person,
            colorIcon = grey
        )
    }
}