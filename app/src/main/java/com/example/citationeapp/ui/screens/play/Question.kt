package com.example.citationeapp.ui.screens.play

import AnswerButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.citationeapp.data.models.Citation
import com.example.citationeapp.ui.theme.CustomBox
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.components.TextBody1Regular
import com.example.citationeapp.ui.theme.customBoxHeightQuestion
import com.example.citationeapp.ui.theme.primary
import com.example.citationeapp.ui.theme.spacing2
import com.example.citationeapp.viewmodel.CitationVersion

@Composable
fun Question(
    modifier: Modifier = Modifier,
    version: CitationVersion,
    citation: Citation,
    onSubmitAnswer: (Int, Int) -> Unit
) {
    CustomBox(
        verticalAlignment = Alignment.Center,
        height = customBoxHeightQuestion
    ) {
        TextBody1Regular(
            text = if (version == CitationVersion.VF) citation.quoteVF else citation.quoteVO,
            color = black,
            textAlign = TextAlign.Center
        )
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(
            space = spacing2, alignment = Alignment.CenterVertically)
    ) {
        citation.choices.forEach { movie ->
            AnswerButton(
                text = if (version == CitationVersion.VF) movie.titleVF else movie.titleVO,
                onClick = {
                    onSubmitAnswer(citation.id, movie.id)
                },
                enabled = true,
                backgroundColor = primary.copy(0.6f),
                borderColor = black
            )
        }
    }
}