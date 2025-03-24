package com.example.citationeapp.ui.screens.portal

import ButtonPrimary
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.HorizontalDivider
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
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.components.AuthTextField
import com.example.citationeapp.ui.theme.components.TextBody1Bold
import com.example.citationeapp.ui.theme.components.TextBody1Regular
import com.example.citationeapp.ui.theme.fail
import com.example.citationeapp.ui.theme.lineHeightSmall
import com.example.citationeapp.ui.theme.padding16
import com.example.citationeapp.ui.theme.primary
import com.example.citationeapp.ui.theme.spacing24
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun Login(
    modifier: Modifier = Modifier,
    goRegister: () -> Unit,
    goHome: () -> Unit,
    goForgottenPassword: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginState by viewModel.loginState.collectAsState()

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

        if (loginState is LoginState.Loading) {
            CircularProgressIndicator()
        } else {
            TextBody1Bold(textId = R.string.portal_login)

            AuthTextField(
                value = email,
                onValueChange = { email = it },
                label = R.string.portal_label_email,
                icon = Icons.Rounded.Email
            )

            AuthTextField(
                value = password,
                onValueChange = { password = it },
                label = R.string.portal_label_password,
                isPassword = true,
                icon = Icons.Rounded.Password
            )

            if (loginState is LoginState.Error) {
                TextBody1Regular(
                    textId = (loginState as LoginState.Error).messageId,
                    color = fail,
                )
            }

            TextBody1Regular(
                modifier = modifier.fillMaxWidth().clickable{ goForgottenPassword() },
                text = "Mot de passe oublié ?",
                color = primary,
                textAlign = TextAlign.Center
            )

            ButtonPrimary(
                onClick = {
                    viewModel.login(email, password)
                },
                modifier = Modifier.fillMaxWidth(),
                textId = R.string.portal_login
            )

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                thickness = lineHeightSmall,
                color = black
            )

            ButtonPrimary(
                onClick = goRegister,
                modifier = Modifier.fillMaxWidth(),
                textId = R.string.portal_create_account
            )
        }
    }

    LaunchedEffect(loginState) {
        if (loginState is LoginState.Success) {
            goHome()
        }
    }
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepositoryInterface
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _loginState.value = LoginState.Error(R.string.portal_error_empty_field)
            return
        }
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            try {
                val success = authRepository.login(email, password)
                if (success) {
                    _loginState.value = LoginState.Success
                } else {
                    _loginState.value = LoginState.Error(R.string.portal_error_login_invalid_credentials)
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(R.string.portal_error_login_netword_issue)
            }
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val messageId: Int) : LoginState()
}


