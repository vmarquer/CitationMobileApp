package com.example.citationeapp.ui.screens.play

import ButtonPrimary
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.example.citationeapp.ui.theme.components.TextBody2Bold
import com.example.citationeapp.ui.theme.components.TextH1Bold
import com.example.citationeapp.ui.theme.padding32
import com.example.citationeapp.ui.theme.padding64
import com.example.citationeapp.ui.theme.primary
import com.example.citationeapp.ui.theme.spacing32

@Composable
fun Result(
    modifier: Modifier = Modifier,
    usedCitations: List<Citation>,
    quizSize: Int,
    goHome: () -> Unit
) {
    val successCount = usedCitations.count { it.result == true }
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(padding32),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = spacing32, alignment = Alignment.CenterVertically
        )
    ) {
        TextH1Bold(textId = R.string.play_result_title)
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.size(80.dp)
        ) {
            CircularProgressIndicator(
                progress = { successCount.toFloat() / quizSize.toFloat() },
                strokeWidth = 8.dp,
                modifier = Modifier.fillMaxSize(),
                color = primary
            )
            TextBody2Bold(
                text = "${successCount}/${quizSize}",
            )
        }
        ButtonPrimary(
            modifier = Modifier.fillMaxWidth().padding(horizontal = padding64),
            textId = R.string.home_title,
            onClick = {
                goHome()
            }
        )
    }
}