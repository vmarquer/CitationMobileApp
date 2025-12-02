package com.example.citationeapp.ui.screens.play

import ButtonPrimary
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.citationeapp.R
import com.example.citationeapp.data.models.Citation
import com.example.citationeapp.data.models.CitationVersion
import com.example.citationeapp.ui.theme.components.ResultCard
import com.example.citationeapp.ui.theme.components.TextBody2Bold
import com.example.citationeapp.ui.theme.components.TextH1Bold
import com.example.citationeapp.ui.theme.grey
import com.example.citationeapp.ui.theme.padding24
import com.example.citationeapp.ui.theme.padding6
import com.example.citationeapp.ui.theme.progressColor
import com.example.citationeapp.ui.theme.spacing10
import com.example.citationeapp.ui.theme.spacing6

@Composable
fun Result(
    modifier: Modifier = Modifier,
    version: CitationVersion,
    usedCitations: List<Citation>,
    quizSize: Int,
    playAgain: () -> Unit,
    goHome: () -> Unit
) {
    val successCount = usedCitations.count { it.result == true }
    val ratio = successCount.toFloat() / quizSize.toFloat()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(padding24),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing6)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing10, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextH1Bold(textId = R.string.play_result_title)

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(60.dp)
            ) {
                CircularProgressIndicator(
                    progress = { 1f },
                    strokeWidth = 4.dp,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp),
                    color = grey.copy(alpha = 0.3f)
                )
                CircularProgressIndicator(
                    progress = { ratio },
                    strokeWidth = 8.dp,
                    modifier = Modifier.fillMaxSize(),
                    color = progressColor(ratio)
                )
                TextBody2Bold(text = "$successCount/$quizSize")
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
        ) {
            usedCitations.forEachIndexed { index, citation ->
                ResultCard(
                    modifier = Modifier.padding(vertical = padding6),
                    citation = citation,
                    version = version,
                    index = index + 1
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing10, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ButtonPrimary(
                modifier = Modifier,
                textId = R.string.home_title,
                onClick = { goHome() }
            )
            ButtonPrimary(
                modifier = Modifier,
                textId = R.string.button_play_again,
                onClick = { playAgain() }
            )
        }
    }
}