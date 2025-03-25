package com.example.citationeapp.ui.screens.play

import AnswerButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.citationeapp.data.models.Citation
import com.example.citationeapp.data.models.getDifficultyBackgroundColor
import com.example.citationeapp.data.models.getDifficultyLabel
import com.example.citationeapp.data.models.getKindLabel
import com.example.citationeapp.ui.theme.components.TextBody2Bold
import com.example.citationeapp.ui.theme.components.TextH3
import com.example.citationeapp.ui.theme.customBoxHeightQuestion
import com.example.citationeapp.ui.theme.minHeightChoicesBox
import com.example.citationeapp.ui.theme.padding16
import com.example.citationeapp.ui.theme.padding2
import com.example.citationeapp.ui.theme.padding20
import com.example.citationeapp.ui.theme.padding24
import com.example.citationeapp.ui.theme.padding32
import com.example.citationeapp.ui.theme.padding8
import com.example.citationeapp.ui.theme.primary
import com.example.citationeapp.ui.theme.spacing8
import com.example.citationeapp.ui.theme.white
import com.example.citationeapp.viewmodel.CitationVersion

@Composable
fun Question(
    modifier: Modifier = Modifier,
    version: CitationVersion,
    citation: Citation,
    onSubmitAnswer: (Int, Int) -> Unit
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
                .height(customBoxHeightQuestion),
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
                .weight(1f)
                .defaultMinSize(minHeight = minHeightChoicesBox)
                .background(primary.copy(alpha = 0.5f), shape = RoundedCornerShape(topStart = padding32, topEnd = padding32)),
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
                        onClick = {
                            onSubmitAnswer(citation.id, movie.id)
                        },
                        enabled = true,
                        backgroundColor = primary.copy(0.6f),
                        borderColor = primary
                    )
                }
            }
        }
    }
}