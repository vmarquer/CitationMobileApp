package com.example.citationeapp.designsystem.componentsGallery

import AnswerButton
import ButtonPrimary
import IconButton
import ProfileButton
import RoundedIconButton
import TextIconButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.citationeapp.R
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.components.TextH3Bold
import com.example.citationeapp.ui.theme.grey
import com.example.citationeapp.ui.theme.primary
import com.example.citationeapp.ui.theme.spacing10
import com.example.citationeapp.ui.theme.white


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

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RoundedIconButton(
                iconId = R.drawable.ic_back,
                color = white,
                backgroundColor = primary,
                onClick = {},
            )

            RoundedIconButton(
                iconId = R.drawable.ic_next,
                color = white,
                backgroundColor = primary,
                onClick = {},
            )

            RoundedIconButton(
                iconId = R.drawable.ic_trophy,
                color = white,
                backgroundColor = primary,
                onClick = {},
            )

            RoundedIconButton(
                iconId = R.drawable.ic_settings,
                color = white,
                backgroundColor = primary,
                onClick = {},
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                iconId = R.drawable.ic_back,
                color = white,
                backgroundColor = primary,
                onClick = {},
            )

            IconButton(
                iconId = R.drawable.ic_next,
                color = white,
                backgroundColor = primary,
                onClick = {},
            )

            IconButton(
                iconId = R.drawable.ic_trophy,
                color = white,
                backgroundColor = primary,
                onClick = {},
            )

            IconButton(
                iconId = R.drawable.ic_settings,
                color = white,
                backgroundColor = primary,
                onClick = {},
            )
        }

        TextIconButton(
            onClick = {},
            text = "TextIconButton",
            iconId = R.drawable.ic_next
        )

        ProfileButton(
            modifier = Modifier.fillMaxWidth(),
            text = "ProfileButton",
            onClick = {},
            iconId = R.drawable.ic_profile,
            colorIcon = grey
        )

        AnswerButton(
            text = "AnswerButton",
            onClick = {},
            enabled = true,
            backgroundColor = primary.copy(0.7f),
            borderColor = black
        )
    }
}