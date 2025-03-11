package com.example.citationeapp.ui.screens.play

import AnswerButton
import ButtonPrimary
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.citationeapp.R
import com.example.citationeapp.ui.theme.CustomBox
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.components.TextBody1Regular
import com.example.citationeapp.ui.theme.components.TextH3
import com.example.citationeapp.ui.theme.customBoxHeightAnswer
import com.example.citationeapp.ui.theme.customBoxHeightAnswerSecondHalf
import com.example.citationeapp.ui.theme.fail
import com.example.citationeapp.ui.theme.failSuccessLogoHeight
import com.example.citationeapp.ui.theme.padding32
import com.example.citationeapp.ui.theme.padding8
import com.example.citationeapp.ui.theme.primary
import com.example.citationeapp.ui.theme.spacing12
import com.example.citationeapp.ui.theme.spacing2
import com.example.citationeapp.ui.theme.spacing8
import com.example.citationeapp.ui.theme.success
import com.example.citationeapp.viewmodel.CitationVersion
import com.example.citationeapp.viewmodel.CitationViewModel
import com.example.citationeapp.viewmodel.VersionViewModel

@Composable
fun Answer(
    modifier: Modifier = Modifier,
    citationViewModel: CitationViewModel,
    versionViewModel: VersionViewModel,
    goToNewCitation: () -> Unit,
    goHome: () -> Unit,
) {
    val uiState = citationViewModel.uiState.collectAsState()
    val version by versionViewModel.version.collectAsState()

    LaunchedEffect(uiState.value.isError) {
        if (uiState.value.isError) {
            goHome()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = padding32, vertical = padding8)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = spacing12, alignment = Alignment.CenterVertically
        )
    ) {
        if (uiState.value.isLoading) {
            CircularProgressIndicator()
        } else {
            uiState.value.currentCitation?.let{ currentCitation ->
                CustomBox(
                    verticalAlignment = Alignment.Center,
                    height = customBoxHeightAnswer
                ) {
                   TextH3(
                       text = if (version == CitationVersion.VF) currentCitation.quoteVF
                       else currentCitation.quoteVO,
                       color = black,
                       textAlign = TextAlign.Center
                   )
                }
                Column(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(
                        space = spacing2, alignment = Alignment.CenterVertically)
                ) {
                    currentCitation.choices.forEach { movie ->
                        AnswerButton(
                            text = if (version == CitationVersion.VF) movie.titleVF else movie.titleVO,
                            onClick = {},
                            enabled = false,
                            backgroundColor = if (movie.id == currentCitation.answerId) success
                            else if (movie.titleVO == currentCitation.userGuessMovieVO ||
                                movie.titleVF == currentCitation.userGuessMovieVF) fail.copy(0.5f)
                            else primary.copy(0.5f),
                            borderColor = if (movie.id == currentCitation.answerId) success
                            else if (movie.titleVO == currentCitation.userGuessMovieVO ||
                                movie.titleVF == currentCitation.userGuessMovieVF) fail else primary,
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        spacing8, alignment = Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Box(
                        modifier = Modifier.height(customBoxHeightAnswerSecondHalf).weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier.height(failSuccessLogoHeight),
                            painter = painterResource(
                                id = if (currentCitation.result == true) R.drawable.ic_success else R.drawable.ic_fail
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.Fit
                        )
                    }
                    Column (
                        modifier = Modifier.weight(3f),
                    ) {
                        TextH3(
                            textId = if(currentCitation.result == true) R.string.play_answer_good_answer
                            else R.string.play_answer_bad_answer,
                            color = if(currentCitation.result == true) success else fail
                        )
                        TextBody1Regular(
                            text = "${currentCitation.actor} - ${currentCitation.caracter}",
                            color = if(currentCitation.result == true) success else fail
                        )
                    }
                }
                ButtonPrimary(
                    modifier = Modifier.fillMaxWidth(),
                    textId = R.string.play_answer_go_next_quote,
                    onClick = {
                        goToNewCitation()
                        citationViewModel.getRandomCitation()
                    }
                )
            }
        }
    }
}