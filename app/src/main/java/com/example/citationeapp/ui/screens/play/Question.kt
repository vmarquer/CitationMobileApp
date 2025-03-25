package com.example.citationeapp.ui.screens.play

import AnswerButton
import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.citationeapp.R
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
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_pattern_randomized),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding16)
                    .clip(RoundedCornerShape(padding16))
                    .height(customBoxHeightQuestion),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_wintery_sunburst),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )

                Box(
                    modifier = modifier
                        .matchParentSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    TextH3(
                        modifier = Modifier.padding(horizontal = padding20),
                        text = if (version == CitationVersion.VF) citation.quoteVF else citation.quoteVO,
                        color = white,
                        textAlign = TextAlign.Center
                    )
                }

                Box(
                    modifier = modifier
                        .matchParentSize()
                        .padding(padding8),
                    contentAlignment = Alignment.TopStart,
                ) {
                    TextBody2Bold(
                        modifier = modifier
                            .background(
                                citation.getDifficultyBackgroundColor(),
                                RoundedCornerShape(50)
                            )
                            .padding(horizontal = padding8, vertical = padding2),
                        text = citation.getDifficultyLabel(),
                        color = white,
                    )
                }
            }

            Box(
                modifier = modifier
                    .fillMaxSize()
                    .weight(1f)
                    .defaultMinSize(minHeight = minHeightChoicesBox),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    modifier = modifier.fillMaxSize().padding(padding32),
                    verticalArrangement = Arrangement.spacedBy(
                        space = spacing8, alignment = Alignment.CenterVertically
                    )
                ) {
                    citation.choices.forEach { movie ->
                        AnswerButton(
                            text = if (version == CitationVersion.VF) movie.titleVF else movie.titleVO,
                            onClick = {
                                onSubmitAnswer(citation.id, movie.id)
                            },
                            enabled = true,
                            backgroundColor = primary,
                            borderColor = primary,
                            textColor = white
                        )
                    }
                }
            }
        }
    }
}