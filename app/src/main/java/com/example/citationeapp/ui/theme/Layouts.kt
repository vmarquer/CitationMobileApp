package com.example.citationeapp.ui.theme

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.example.citationeapp.ui.theme.components.TextBody1Regular

@Composable
fun ExpandableSection(
    modifier: Modifier = Modifier,
    text: String = "",
    @StringRes textId: Int = -1,
    content: @Composable () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded }
            .background(primary.copy(0.2f), shape = RoundedCornerShape(padding8))
            .clip(RoundedCornerShape(padding8)),
        verticalArrangement = Arrangement.spacedBy(spacing8)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding12),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextBody1Regular(
                text = (if (textId == -1) text else stringResource(id = textId)),
            )
            Icon(
                imageVector = if (isExpanded) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown,
                tint = black,
                contentDescription = null
            )
        }
    }

    AnimatedVisibility(
        visible = isExpanded,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = padding8),
            verticalArrangement = Arrangement.spacedBy(spacing8)
        ) {
            content()
        }
    }
}
