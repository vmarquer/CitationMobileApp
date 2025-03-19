package com.example.citationeapp.ui.screens.portal

import ButtonPrimary
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.citationeapp.R
import com.example.citationeapp.ui.theme.components.AuthTextField
import com.example.citationeapp.ui.theme.components.TextBody1Bold
import com.example.citationeapp.ui.theme.components.TextBody1Regular
import com.example.citationeapp.ui.theme.fail
import com.example.citationeapp.ui.theme.padding16
import com.example.citationeapp.ui.theme.spacing24

@Composable
fun Validation(
    modifier: Modifier = Modifier,
    goLogin: () -> Unit
) {
    var activationCode by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<Int?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(padding16)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = spacing24, alignment = Alignment.CenterVertically
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            TextBody1Bold(textId = R.string.portal_activation_code)

            AuthTextField(
                value = activationCode,
                onValueChange = { activationCode = it },
                label = R.string.portal_activation_code,
                isPassword = false
            )

            errorMessage?.let {
                TextBody1Regular(
                    textId = it,
                    color = fail,
                )
            }

            ButtonPrimary(
                onClick = {
                    if (activationCode.length == 6) {
                        isLoading = true
                        // verifyActivationCode(activationCode)
                    } else {
                        errorMessage = R.string.portal_activation_code_error_incorrect_length
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                textId = R.string.portal_activation_code_submit
            )
        }
    }
}
