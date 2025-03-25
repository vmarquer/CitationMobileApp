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
import androidx.compose.material.icons.rounded.PermIdentity
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
import com.example.citationeapp.ui.theme.spacing24
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun Register(
    modifier: Modifier = Modifier,
    goValidation: () -> Unit,
    goLogin: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val registerState by viewModel.registerState.collectAsState()

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
        if (registerState is RegisterState.Loading) {
            CircularProgressIndicator()
        } else {
            TextBody1Bold(textId = R.string.portal_create_account)

            AuthTextField(
                value = email,
                onValueChange = { email = it },
                label = R.string.portal_label_email,
                icon = Icons.Rounded.Email
            )

            AuthTextField(
                value = username,
                onValueChange = { username = it },
                label = R.string.portal_label_username,
                icon = Icons.Rounded.PermIdentity
            )

            AuthTextField(
                value = password,
                onValueChange = { password = it },
                label = R.string.portal_label_password,
                isPassword = true,
                icon = Icons.Rounded.Password
            )

            AuthTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = R.string.portal_label_password_confirm,
                isPassword = true,
                icon = Icons.Rounded.Password
            )

            if (registerState is RegisterState.Error) {
                TextBody1Regular(
                    textId = (registerState as RegisterState.Error).messageId,
                    color = fail,
                )
            }

            ButtonPrimary(
                onClick = { viewModel.register(email, username, password, confirmPassword) },
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

        LaunchedEffect(registerState) {
            if (registerState is RegisterState.Success) {
                goValidation()
            }
        }
    }
}

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepositoryInterface
) : ViewModel() {

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState

    fun register(email: String, username: String, password: String, confirmPassword: String) {
        if (username.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            _registerState.value = RegisterState.Error(R.string.portal_error_empty_field)
            return
        }
        if (password != confirmPassword) {
            _registerState.value = RegisterState.Error(R.string.portal_error_different_passwords)
            return
        }
        _registerState.value = RegisterState.Loading
        viewModelScope.launch {
            val success = authRepository.register(username, email, password)
            if (success) {
                _registerState.value = RegisterState.Success
            } else {
                _registerState.value = RegisterState.Error(R.string.portal_error_register)
            }
        }
    }
}

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    object Success : RegisterState()
    data class Error(val messageId: Int) : RegisterState()
}
