package com.example.citationeapp.ui.screens.play

import TextIconButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.citationeapp.R
import com.example.citationeapp.ui.theme.padding16
import com.example.citationeapp.ui.theme.spacing24
import com.example.citationeapp.viewmodel.CitationViewModel
import com.example.citationeapp.viewmodel.VersionViewModel

@Composable
fun Play(
    modifier: Modifier = Modifier,
    citationViewModel: CitationViewModel,
    versionViewModel: VersionViewModel,
    launchGame: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(padding16)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = spacing24, alignment = Alignment.CenterVertically
        )
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_app),
            contentDescription = null,
            modifier = Modifier.height(250.dp),
            contentScale = ContentScale.Fit
        )

        TextIconButton(
            onClick = {
                launchGame()
                citationViewModel.getRandomCitation()
                      },
            textId = R.string.play,
            iconId = R.drawable.ic_next
        )
    }
}