package com.example.citationeapp.ui.screens.play

import AnswerButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.citationeapp.data.remote.dto.CitationAnswerRequestDTO
import com.example.citationeapp.ui.theme.CustomBox
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.components.TextBody1Regular
import com.example.citationeapp.ui.theme.components.TextH3
import com.example.citationeapp.ui.theme.customBoxHeightQuestion
import com.example.citationeapp.ui.theme.fail
import com.example.citationeapp.ui.theme.padding32
import com.example.citationeapp.ui.theme.padding8
import com.example.citationeapp.ui.theme.primary
import com.example.citationeapp.ui.theme.spacing12
import com.example.citationeapp.ui.theme.spacing2
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
    val version by versionViewModel.version.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(padding32, vertical = padding8)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = spacing12, alignment = Alignment.CenterVertically
        )
    ) {
        if (uiState.value.isLoading) {
            CircularProgressIndicator()
        } else if (uiState.value.isError){
            TextBody1Regular(text = "Erreur réseau", color = fail)
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

            uiState.value.currentCitation?.let{ citation ->
                Column(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(
                        space = spacing2, alignment = Alignment.CenterVertically)
                ) {
                    citation.choices.forEach { movie ->
                        AnswerButton(
                            text = if (version == CitationVersion.VF) movie.titleVF else movie.titleVO,
                            onClick = {
                                goToAnswer()
                                citationViewModel.sendAnwser(
                                    citation.id,
                                    CitationAnswerRequestDTO(
                                        userAnswerId = movie.id
                                    )
                                )
                            },
                            enabled = true,
                            backgroundColor = primary.copy(0.7f),
                            borderColor = black
                        )
                    }
                }
            }
        }
    }
}