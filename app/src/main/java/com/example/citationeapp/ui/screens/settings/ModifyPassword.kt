package com.example.citationeapp.ui.screens.settings

import ButtonPrimary
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Password
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citationeapp.R
import com.example.citationeapp.data.remote.repositories.AuthRepositoryInterface
import com.example.citationeapp.ui.theme.components.AuthTextField
import com.example.citationeapp.ui.theme.components.ConfirmationDialog
import com.example.citationeapp.ui.theme.components.TextBody1Regular
import com.example.citationeapp.ui.theme.fail
import com.example.citationeapp.ui.theme.padding12
import com.example.citationeapp.ui.theme.padding8
import com.example.citationeapp.ui.theme.spacing8
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun ModifyPassword(
    modifier: Modifier = Modifier,
    viewModel: ModifyPasswordViewModel = hiltViewModel(),
    goProfile: () -> Unit,
    onForceLogin: () -> Unit,
) {
    val modifyPasswordState by viewModel.modifyPasswordState.collectAsState()
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }
    var showModifyPasswordDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding12)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(spacing8)
    ) {
        if (modifyPasswordState is ModifyPasswordState.Loading) {
            CircularProgressIndicator()
        } else {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = padding8),
                verticalArrangement = Arrangement.spacedBy(spacing8)
            ) {
                AuthTextField(
                    value = currentPassword,
                    onValueChange = { currentPassword = it },
                    label = R.string.field_current_password,
                    isPassword = true,
                    icon = Icons.Rounded.Password
                )
                AuthTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = R.string.field_new_password,
                    isPassword = true,
                    icon = Icons.Rounded.Password
                )
                AuthTextField(
                    value = confirmNewPassword,
                    onValueChange = { confirmNewPassword = it },
                    label = R.string.field_confirm_new_password,
                    isPassword = true,
                    icon = Icons.Rounded.Password
                )
                if (modifyPasswordState is ModifyPasswordState.Error) {
                    TextBody1Regular(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        textId = (modifyPasswordState as ModifyPasswordState.Error).messageId,
                        color = fail,
                    )
                }
                ButtonPrimary(
                    onClick = { showModifyPasswordDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    textId = R.string.button_modify_password
                )
            }
        }

        LaunchedEffect(modifyPasswordState) {
            if (modifyPasswordState is ModifyPasswordState.Success) {
                goProfile()
            }
        }
    }

    if (showModifyPasswordDialog) {
        ConfirmationDialog(
            titleId = R.string.modify_password_dialog_title,
            messageId = R.string.modify_password_dialog_message,
            onConfirm = {
                showModifyPasswordDialog = false
                viewModel.modifyPassword(
                    currentPassword,
                    newPassword,
                    confirmNewPassword,
                    onForceLogin
                )
            },
            onDismiss = { showModifyPasswordDialog = false }
        )
    }
}

@HiltViewModel
class ModifyPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepositoryInterface,
) : ViewModel() {
    private val _modifyPasswordState =
        MutableStateFlow<ModifyPasswordState>(ModifyPasswordState.Idle)
    val modifyPasswordState: StateFlow<ModifyPasswordState> = _modifyPasswordState

    fun modifyPassword(
        oldPassword: String,
        newPassword: String,
        confirmNewPassword: String,
        onForceLogin: () -> Unit
    ) {
        if (oldPassword.isBlank() || newPassword.isBlank() || confirmNewPassword.isBlank()) {
            _modifyPasswordState.value = ModifyPasswordState.Error(R.string.error_empty_field)
            return
        }
        if (newPassword != confirmNewPassword) {
            _modifyPasswordState.value =
                ModifyPasswordState.Error(R.string.error_different_passwords)
            return
        }
        _modifyPasswordState.value = ModifyPasswordState.Loading
        viewModelScope.launch {
            if (authRepository.isBearerTokenExpired(authRepository.getBearerToken())) {
                val response = authRepository.askRefreshToken(authRepository.getRefreshToken())
                if (!response) {
                    authRepository.logout()
                    onForceLogin()
                    return@launch
                }
            }
            val email = authRepository.extractEmailFromToken()
            if (email != null) {
                val response = authRepository.modifyPassword(email, oldPassword, newPassword)
                if (response) {
                    _modifyPasswordState.value = ModifyPasswordState.Success
                }
            }
        }
    }
}

sealed class ModifyPasswordState {
    object Idle : ModifyPasswordState()
    object Loading : ModifyPasswordState()
    object Success : ModifyPasswordState()
    data class Error(val messageId: Int) : ModifyPasswordState()
}