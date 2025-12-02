package com.example.citationeapp.ui.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.citationeapp.data.models.Citation
import com.example.citationeapp.data.models.CitationVersion
import com.example.citationeapp.ui.theme.fail
import com.example.citationeapp.ui.theme.lineHeightSmall
import com.example.citationeapp.ui.theme.padding24
import com.example.citationeapp.ui.theme.padding6
import com.example.citationeapp.ui.theme.spacing4
import com.example.citationeapp.ui.theme.spacing6
import com.example.citationeapp.ui.theme.success
import com.example.citationeapp.ui.theme.white

@Composable
fun ResultCard(
    modifier: Modifier = Modifier,
    citation: Citation,
    version: CitationVersion,
    index: Int
) {
    var expanded by remember { mutableStateOf(false) }
    val answer = citation.choices.find { it.id == citation.answerId }
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = if (citation.result == true) success else fail,
            contentColor = white
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = padding6
        ),
        modifier = modifier
            .fillMaxWidth(),
        onClick = { expanded = !expanded }
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.TopStart)
                    .padding(6.dp)
                    .border(width = lineHeightSmall, color = white, shape = CircleShape)
                    .clip(CircleShape)
            ) {
                TextBody3Regular(
                    text = "$index",
                    modifier = Modifier,
                    color = white
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding6)
                    .padding(start = padding24)
            ) {
                if (expanded) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(spacing6)
                        ) {
                            TextBody2Bold(
                                text = if (version == CitationVersion.VF) "\"${citation.quoteVF}\"" else "\"${citation.quoteVO}\"",
                                color = white
                            )
                            answer?.let { film ->
                                TextBody2Italic(
                                    text = if (version == CitationVersion.VF) film.titleVF else film.titleVO,
                                    color = white
                                )
                            }
                            TextBody3Regular(
                                text = "${citation.caracter}, ${citation.actor}",
                                color = white
                            )
                        }
                        Spacer(modifier = Modifier.width(spacing4))
                        citation.image?.let {
                            Image(
                                bitmap = it,
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = modifier
                                    .width(80.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            )
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextBody2Bold(
                            text = if (version == CitationVersion.VF) "\"${citation.quoteVF}\"" else "\"${citation.quoteVO}\"",
                            color = white
                        )
                        answer?.let { film ->
                            TextBody2Italic(
                                text = if (version == CitationVersion.VF) film.titleVF else film.titleVO,
                                color = white
                            )
                        }
                    }
                }
            }
        }
    }
}
