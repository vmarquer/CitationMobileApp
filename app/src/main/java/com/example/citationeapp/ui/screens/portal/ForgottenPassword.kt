package com.example.citationeapp.ui.screens.portal

import ButtonPrimary
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Password
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citationeapp.R
import com.example.citationeapp.data.remote.repositories.AuthRepositoryInterface
import com.example.citationeapp.ui.theme.components.AuthTextField
import com.example.citationeapp.ui.theme.components.TextBody1Bold
import com.example.citationeapp.ui.theme.components.TextBody1Regular
import com.example.citationeapp.ui.theme.components.TextBody2Regular
import com.example.citationeapp.ui.theme.fail
import com.example.citationeapp.ui.theme.padding16
import com.example.citationeapp.ui.theme.spacing24
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun ForgottenPassword(
    modifier: Modifier = Modifier,
    goLogin: () -> Unit,
    viewModel: ForgottenPasswordViewModel = hiltViewModel()
) {

    var email by remember { mutableStateOf("") }
    var activationCode by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }

    val askNewPasswordState by viewModel.askNewPasswordState.collectAsState()
    val passwordActivationCodeState by viewModel.passwordActivationCodeState.collectAsState()

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
        TextBody1Bold(textId = R.string.portal_forgotten_password_title)

        if (askNewPasswordState is AskNewPasswordState.Loading || askNewPasswordState is AskNewPasswordState.Loading) {
            CircularProgressIndicator()
        } else {
            if (askNewPasswordState !is AskNewPasswordState.Success) {
                TextBody2Regular(text = "Entrez votre email")
                AuthTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = R.string.portal_label_email,
                    icon = Icons.Rounded.Email
                )
                if (askNewPasswordState is AskNewPasswordState.Error) {
                    TextBody1Regular(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        textId = (askNewPasswordState as AskNewPasswordState.Error).messageId,
                        color = fail,
                    )
                }
                ButtonPrimary(
                    onClick = {
                        viewModel.askNewPassword(email)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    text = "Demander un nouveau mot de passe"
                )
            } else {
                TextBody2Regular(text = "Code d'activation et nouveau mot de passe")
                AuthTextField(
                    value = activationCode,
                    onValueChange = { activationCode = it },
                    label = R.string.portal_activation_code,
                    isPassword = false
                )

                AuthTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = R.string.settings_modify_password_new_password_label,
                    isPassword = true,
                    icon = Icons.Rounded.Password
                )
                AuthTextField(
                    value = confirmNewPassword,
                    onValueChange = { confirmNewPassword = it },
                    label = R.string.settings_modify_password_confirm_new_password_label,
                    isPassword = true,
                    icon = Icons.Rounded.Password
                )

                if (passwordActivationCodeState is PasswordActivationCodeState.Error) {
                    TextBody1Regular(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        textId = (passwordActivationCodeState as PasswordActivationCodeState.Error).messageId,
                        color = fail,
                    )
                }

                ButtonPrimary(
                    onClick = { viewModel.sendNewPassword(email, activationCode, newPassword, confirmNewPassword) },
                    modifier = Modifier.fillMaxWidth(),
                    text = "Envoyer le nouveau mot de passe"
                )
            }
        }

        ButtonPrimary(
            onClick = goLogin,
            modifier = Modifier.fillMaxWidth(),
            textId = R.string.portal_back_to_connection
        )
    }

    LaunchedEffect(passwordActivationCodeState) {
        if (passwordActivationCodeState is PasswordActivationCodeState.Success) {
            goLogin()
        }
    }
}

@HiltViewModel
class ForgottenPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepositoryInterface
) : ViewModel() {

    private val _askNewPasswordState = MutableStateFlow<AskNewPasswordState>(AskNewPasswordState.Idle)
    val askNewPasswordState: StateFlow<AskNewPasswordState> = _askNewPasswordState

    private val _passwordActivationCodeState = MutableStateFlow<PasswordActivationCodeState>(PasswordActivationCodeState.Idle)
    val passwordActivationCodeState: StateFlow<PasswordActivationCodeState> = _passwordActivationCodeState

    fun askNewPassword(email: String) {
        if (email.isBlank()) {
            _askNewPasswordState.value = AskNewPasswordState.Error(R.string.portal_error_empty_field)
            return
        }
        _askNewPasswordState.value = AskNewPasswordState.Loading
        viewModelScope.launch {
            val success = authRepository.askNewPassword(email)
            if (success) {
                _askNewPasswordState.value = AskNewPasswordState.Success
            } else {
                _askNewPasswordState.value = AskNewPasswordState.Error(R.string.portal_forgotten_password_error_ask)
            }
        }
    }

    fun sendNewPassword(email: String, activationCode: String, newPassword: String, confirmNewPassword: String) {
        if (!activationCode.matches(Regex("^\\d{6}$"))) {
            _passwordActivationCodeState.value = PasswordActivationCodeState.Error(R.string.portal_activation_code_error_incorrect_length)
            return
        }
        if (activationCode.isBlank() || newPassword.isBlank() || confirmNewPassword.isBlank()) {
            _passwordActivationCodeState.value = PasswordActivationCodeState.Error(R.string.portal_error_empty_field)
            return
        }
        _passwordActivationCodeState.value = PasswordActivationCodeState.Loading
        viewModelScope.launch {
            val success = authRepository.sendNewPassword(email, activationCode, newPassword)
            if (success) {
                _passwordActivationCodeState.value = PasswordActivationCodeState.Success
            } else {
                _passwordActivationCodeState.value = PasswordActivationCodeState.Error(R.string.portal_forgotten_password_error_send)
            }
        }
    }
}

sealed class AskNewPasswordState {
    object Idle : AskNewPasswordState()
    object Loading : AskNewPasswordState()
    object Success : AskNewPasswordState()
    data class Error(val messageId: Int) : AskNewPasswordState()
}

sealed class PasswordActivationCodeState {
    object Idle : PasswordActivationCodeState()
    object Loading : PasswordActivationCodeState()
    object Success : PasswordActivationCodeState()
    data class Error(val messageId: Int) : PasswordActivationCodeState()
}
