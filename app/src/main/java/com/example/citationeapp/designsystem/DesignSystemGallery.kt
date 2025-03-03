package com.example.citationeapp.designsystem

import RoundedIconButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.citationeapp.R
import com.example.citationeapp.designsystem.componentsGallery.DesignSystemButtons
import com.example.citationeapp.designsystem.componentsGallery.DesignSystemLabels
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.components.TextScreenTitle
import com.example.citationeapp.ui.theme.padding16
import com.example.citationeapp.ui.theme.spacing16
import com.example.citationeapp.ui.theme.white
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DesignSystemViewModel @Inject constructor(
    // call repositories
) : ViewModel() {}

@Composable
fun DesignSystem(
    modifier: Modifier = Modifier,
    designSystemViewModel: DesignSystemViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding16)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(spacing16)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RoundedIconButton(
                iconId = R.drawable.ic_back,
                color = black,
                backgroundColor = white,
                modifier = Modifier.weight(1f),
                onClick = onBack,
            )

            TextScreenTitle(
                textId = R.string.design_system_title,
                color = black,
                modifier = Modifier.weight(3f),
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )
        }

        DesignSystemButtons()
        DesignSystemLabels()
    }
}