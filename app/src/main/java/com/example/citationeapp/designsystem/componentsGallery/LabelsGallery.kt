package com.example.citationeapp.designsystem.componentsGallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.components.TextBody1Bold
import com.example.citationeapp.ui.theme.components.TextBody1Regular
import com.example.citationeapp.ui.theme.components.TextBody2Bold
import com.example.citationeapp.ui.theme.components.TextBody2Regular
import com.example.citationeapp.ui.theme.components.TextBottomBar
import com.example.citationeapp.ui.theme.components.TextH1
import com.example.citationeapp.ui.theme.components.TextH1Bold
import com.example.citationeapp.ui.theme.components.TextH2
import com.example.citationeapp.ui.theme.components.TextH2Bold
import com.example.citationeapp.ui.theme.components.TextH3
import com.example.citationeapp.ui.theme.components.TextH3Bold
import com.example.citationeapp.ui.theme.components.TextScreenTitle
import com.example.citationeapp.ui.theme.padding16
import com.example.citationeapp.ui.theme.spacing10
import com.example.citationeapp.ui.theme.spacing2

@Composable
fun DesignSystemLabels(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = padding16),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing10),
    ) {
        TextH3Bold(text = "Labels")

        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(spacing2),
            horizontalAlignment = Alignment.Start,
        ) {
            TextBottomBar(text = "TextBottomBar", color = black)
            TextScreenTitle(
                text = "TextScreenTitle",
                color = black,
            )
            TextH1(text = "TextH1")
            TextH2(text = "TextH2")
            TextH3(text = "TextH3")
            TextH1Bold(text = "TextH1Bold")
            TextH2Bold(text = "TextH2Bold")
            TextH3Bold(text = "TextH3Bold")
            TextBody1Regular(text = "TextBody1Regular")
            TextBody2Regular(text = "TextBody2Regular")
            TextBody1Bold(text = "TextBody1Bold")
            TextBody2Bold(text = "TextBody1Bold")
        }
    }
}