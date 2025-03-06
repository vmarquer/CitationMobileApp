package com.example.citationeapp.ui.screens.play

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import com.example.citationeapp.data.remote.dto.CitationAnswerRequestDTO
import com.example.citationeapp.ui.theme.CustomBox
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.components.TextH3
import com.example.citationeapp.ui.theme.components.TextInputWithButton
import com.example.citationeapp.ui.theme.customBoxHeightQuestion
import com.example.citationeapp.ui.theme.padding32
import com.example.citationeapp.ui.theme.spacing24
import com.example.citationeapp.viewmodel.CitationVersion
import com.example.citationeapp.viewmodel.CitationViewModel
import com.example.citationeapp.viewmodel.VersionViewModel

@Composable
fun Question(
    modifier: Modifier = Modifier,
    citationViewModel: CitationViewModel,
    versionViewModel: VersionViewModel,
    goToAnswer: () -> Unit,
    goHome: () -> Unit,
) {
    val uiState = citationViewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val version by versionViewModel.version.collectAsState()

    LaunchedEffect(uiState.value.isError) {
        if (uiState.value.isError) {
            goHome()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(padding32)
            .verticalScroll(rememberScrollState())
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { focusManager.clearFocus() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = spacing24, alignment = Alignment.CenterVertically
        )
    ) {
        if (uiState.value.currentCitation == null) {
            CircularProgressIndicator()
        } else {
            CustomBox(
                verticalAlignment = Alignment.Center,
                height = customBoxHeightQuestion
            ) {
                uiState.value.currentCitation?.let {
                    TextH3(
                        text = if (version == CitationVersion.VF) it.quoteVF else it.quoteVO,
                        color = black,
                        textAlign = TextAlign.Center
                    )
                }
            }
            TextInputWithButton(
                onClick = { userGuess ->
                    focusManager.clearFocus()
                    goToAnswer()
                    uiState.value.currentCitation?.id?.let { citationId ->
                        citationViewModel.sendAnwser(
                            citationId,
                            CitationAnswerRequestDTO(
                                movieVO = if (version == CitationVersion.VO) userGuess else "",
                                movieVF = if (version == CitationVersion.VF) userGuess else "",
                            )
                        )
                    }
                }
            )
        }
    }
}