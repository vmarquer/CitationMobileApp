package com.example.citationeapp.ui.screens.portal

import ButtonPrimary
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.citationeapp.R
import com.example.citationeapp.data.models.Utilisateur
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.components.AuthTextField
import com.example.citationeapp.ui.theme.components.TextBody1Bold
import com.example.citationeapp.ui.theme.components.TextBody1Regular
import com.example.citationeapp.ui.theme.fail
import com.example.citationeapp.ui.theme.lineHeightSmall
import com.example.citationeapp.ui.theme.padding16
import com.example.citationeapp.ui.theme.spacing24

@Composable
fun Register(
    modifier: Modifier = Modifier,
    goValidation: () -> Unit,
    goLogin: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<Int?>(null) }

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
        TextBody1Bold(textId = R.string.portal_create_account)

        AuthTextField(
            value = email,
            onValueChange = { email = it },
            label = R.string.portal_label_email,
        )

        AuthTextField(
            value = username,
            onValueChange = { username = it },
            label = R.string.portal_label_username,
        )

        AuthTextField(
            value = password,
            onValueChange = { password = it },
            label = R.string.portal_label_password,
            isPassword = true
        )

        AuthTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = R.string.portal_label_password_confirm,
            isPassword = true
        )

        errorMessage?.let {
            TextBody1Regular(
                textId = it,
                color = fail,
            )
        }

        ButtonPrimary(
            onClick = {
                if (username.isNotBlank() && email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()) {
                    if (password == confirmPassword) {
                        val utilisateur = Utilisateur(username = username, email = email, password = password)
                        // register(utilisateur)
                    } else {
                        errorMessage = R.string.portal_error_different_passwords
                    }
                } else {
                    errorMessage = R.string.portal_error_empty_field
                }
            },
            modifier = Modifier.fillMaxWidth(),
            textId = R.string.portal_sign_up
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            thickness = lineHeightSmall,
            color = black
        )

        ButtonPrimary(
            onClick = goLogin,
            modifier = Modifier.fillMaxWidth(),
            textId = R.string.portal_back_to_connection
        )
    }
}
