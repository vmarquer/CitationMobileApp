package com.example.citationeapp.ui.theme.components

import ButtonPrimary
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.citationeapp.ui.theme.padding16
import com.example.citationeapp.ui.theme.white

@Composable
fun ConfirmationDialog(
    modifier: Modifier = Modifier,
    titleId: Int,
    messageId: Int,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            TextBody1Bold(
                modifier = modifier.fillMaxWidth(),
                textId = titleId,
                textAlign = TextAlign.Center
            )
        },
        text = {
            TextBody1Regular(
                modifier = modifier.fillMaxWidth(),
                textId = messageId,
                textAlign = TextAlign.Center
            )
               },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(padding16)
            ) {
                ButtonPrimary(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f),
                    text = "Annuler"
                )
                ButtonPrimary(
                    onClick = {
                        onDismiss()
                        onConfirm()
                    },
                    modifier = Modifier.weight(1f),
                    text = "Oui"
                )
            }
        },
        containerColor = white
    )
}