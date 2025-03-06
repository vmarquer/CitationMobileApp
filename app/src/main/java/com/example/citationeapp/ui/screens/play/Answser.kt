package com.example.citationeapp.ui.screens.play

import IconTextButton
import TextIconButton
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.citationeapp.R
import com.example.citationeapp.ui.theme.CustomBox
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.components.TextH3
import com.example.citationeapp.ui.theme.customBoxHeightAnswer
import com.example.citationeapp.ui.theme.customBoxHeightAnswerFirstHalf
import com.example.citationeapp.ui.theme.customBoxHeightAnswerSecondHalf
import com.example.citationeapp.ui.theme.padding32
import com.example.citationeapp.ui.theme.spacing16
import com.example.citationeapp.ui.theme.spacing6
import com.example.citationeapp.ui.theme.spacing8
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
            .padding(padding32)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = spacing16, alignment = Alignment.CenterVertically
        )
    ) {
        uiState.value.currentCitation?.let { currentCitation ->
            CustomBox(
                verticalAlignment = Alignment.Center,
                height = customBoxHeightAnswer
            ) {
                TextH3(
                    text = if (version == CitationVersion.VF) currentCitation.quoteVF else currentCitation.quoteVO,
                    color = black,
                    textAlign = TextAlign.Center
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    spacing16, alignment = Alignment.CenterHorizontally
                )
            ) {
                CustomBox(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.Center,
                    height = customBoxHeightAnswer
                ) {
                    TextH3(
                        text = "Affiche du film",
                        textAlign = TextAlign.Center
                    )
                }

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    CustomBox(
                        verticalAlignment = Alignment.Center,
                        height = customBoxHeightAnswerFirstHalf
                    ) {
                        Column {
                            TextH3(
                                text = if (version == CitationVersion.VF)
                                    currentCitation.movieVF?: ""
                                else currentCitation.movieVO?: "",
                                textAlign = TextAlign.Center
                            )
                            TextH3(
                                text = if (version == CitationVersion.VF)
                                    currentCitation.userGuessMovieVF?: ""
                                else currentCitation.userGuessMovieVO?: "",
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Box(
                        modifier = Modifier.height(customBoxHeightAnswerSecondHalf).fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier.height(80.dp).padding(top = spacing6),
                            painter = painterResource(
                                id = if (currentCitation.result == true) R.drawable.ic_success else R.drawable.ic_fail
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                spacing8, alignment = Alignment.CenterHorizontally
            )
        ) {
            IconTextButton(
                onClick = goHome,
                textId = R.string.home_title,
                iconId = R.drawable.ic_home
            )
            TextIconButton(
                onClick = {
                    goToNewCitation()
                    citationViewModel.getRandomCitation()
                          },
                textId = R.string.play,
                iconId = R.drawable.ic_next
            )
        }
    }
}