package com.example.citationeapp.designsystem.componentsGallery

import ButtonPrimary
import IconButton
import IconTextButton
import RoundedIconButton
import TextIconButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.citationeapp.R
import com.example.citationeapp.ui.theme.components.TextH1
import com.example.citationeapp.ui.theme.components.TextH2
import com.example.citationeapp.ui.theme.components.TextH3
import com.example.citationeapp.ui.theme.padding16
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
        TextH2(text = "Buttons")

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

        IconTextButton(
            onClick = {},
            text = "IconTextButton",
            iconId = R.drawable.ic_next
        )
    }
}