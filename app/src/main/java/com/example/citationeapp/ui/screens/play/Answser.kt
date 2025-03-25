package com.example.citationeapp.ui.screens.play

import AnswerButton
import ButtonPrimary
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.citationeapp.R
import com.example.citationeapp.data.models.Citation
import com.example.citationeapp.data.models.getDifficultyBackgroundColor
import com.example.citationeapp.data.models.getDifficultyLabel
import com.example.citationeapp.data.models.getKindLabel
import com.example.citationeapp.ui.theme.components.TextBody1Regular
import com.example.citationeapp.ui.theme.components.TextBody2Bold
import com.example.citationeapp.ui.theme.components.TextH3
import com.example.citationeapp.ui.theme.customBoxHeightAnswer
import com.example.citationeapp.ui.theme.customBoxHeightAnswerSecondHalf
import com.example.citationeapp.ui.theme.fail
import com.example.citationeapp.ui.theme.failSuccessLogoHeight
import com.example.citationeapp.ui.theme.padding16
import com.example.citationeapp.ui.theme.padding2
import com.example.citationeapp.ui.theme.padding20
import com.example.citationeapp.ui.theme.padding24
import com.example.citationeapp.ui.theme.padding32
import com.example.citationeapp.ui.theme.padding8
import com.example.citationeapp.ui.theme.primary
import com.example.citationeapp.ui.theme.spacing8
import com.example.citationeapp.ui.theme.success
import com.example.citationeapp.ui.theme.white
import com.example.citationeapp.viewmodel.CitationVersion

@Composable
fun Answer(
    modifier: Modifier = Modifier,
    version: CitationVersion,
    citation: Citation,
    onPlayAgain: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(primary.copy(alpha = 0.8f), shape = RoundedCornerShape(bottomEnd = padding32, bottomStart = padding32))
                .height(customBoxHeightAnswer),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = padding8, start = padding8, end = padding8),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextBody2Bold(
                        modifier = modifier
                            .background(citation.getDifficultyBackgroundColor(), RoundedCornerShape(50))
                            .padding(horizontal = padding8, vertical = padding2),
                        text = citation.getDifficultyLabel(),
                        color = white,
                    )
                    TextBody2Bold(
                        text = citation.getKindLabel(),
                        color = white,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    //verticalAlignment = Alignment.CenterVertically
                ) {
                    TextH3(
                        modifier = Modifier.padding(horizontal = padding20),
                        text = if (version == CitationVersion.VF) citation.quoteVF else citation.quoteVO,
                        color = white,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier)
            }
        }

        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = padding16)
                .padding(top = padding16)
                .background(primary.copy(alpha = 0.5f), shape = RoundedCornerShape(padding32)),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = modifier.fillMaxSize().padding(padding24),
                verticalArrangement = Arrangement.spacedBy(
                    space = spacing8, alignment = Alignment.CenterVertically)
            ) {
                citation.choices.forEach { movie ->
                    AnswerButton(
                        text = if (version == CitationVersion.VF) movie.titleVF else movie.titleVO,
                        onClick = {},
                        enabled = false,
                        backgroundColor = if (movie.id == citation.answerId) success
                        else if (movie.titleVO == citation.userGuessMovieVO ||
                            movie.titleVF == citation.userGuessMovieVF) fail.copy(0.5f)
                        else primary.copy(0.5f),
                        borderColor = if (movie.id == citation.answerId) success
                        else if (movie.titleVO == citation.userGuessMovieVO ||
                            movie.titleVF == citation.userGuessMovieVF) fail else primary,
                    )
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
                        Icon(
                            modifier = Modifier.height(failSuccessLogoHeight),
                            imageVector = if (citation.result == true) Icons.Outlined.CheckCircle else Icons.Outlined.Cancel,
                            tint = if (citation.result == true) success else fail,
                            contentDescription = null,
                        )
                    }
                    Column (
                        modifier = Modifier.weight(3f),
                    ) {
                        TextH3(
                            textId = if(citation.result == true) R.string.play_answer_good_answer
                            else R.string.play_answer_bad_answer,
                            color = if(citation.result == true) success else fail
                        )
                        TextBody1Regular(
                            text = "${citation.actor} - ${citation.caracter}",
                            color = if(citation.result == true) success else fail
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier.padding(padding16
            )
        ) {
            ButtonPrimary(
                modifier = Modifier.fillMaxWidth(),
                textId = R.string.play_answer_go_next_quote,
                onClick = {
                    onPlayAgain()
                }
            )
        }
    }
}