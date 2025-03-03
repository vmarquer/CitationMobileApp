package com.example.citationeapp.designsystem.componentsGallery

import RoundedIconButton
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
        TextH1(text = "Buttons")

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
    }
}