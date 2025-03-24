package com.example.citationeapp.ui.screens.profile

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citationeapp.R
import com.example.citationeapp.data.remote.repositories.AuthRepositoryInterface
import com.example.citationeapp.ui.theme.ExpandableSection
import com.example.citationeapp.ui.theme.components.AuthTextField
import com.example.citationeapp.ui.theme.components.TextBody1Regular
import com.example.citationeapp.ui.theme.fail
import com.example.citationeapp.ui.theme.padding12
import com.example.citationeapp.ui.theme.spacing8
import com.example.citationeapp.ui.theme.success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun Profile(
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    goPortal: () -> Unit
) {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }

    val logoutState by profileViewModel.logoutState.collectAsState()
    val modifyPasswordState by profileViewModel.modifyPasswordState.collectAsState()
    val email by profileViewModel.email.collectAsState()
    val username by profileViewModel.username.collectAsState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding12)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(spacing8)
    ) {
        if (logoutState is LogoutState.Loading || modifyPasswordState is ModifyPasswordState.Loading) {
            CircularProgressIndicator()
        } else {
            TextBody1Regular(text = "Nom d'utilisateur: ${username ?: "Non disponible"}")
            TextBody1Regular(text = "Email: ${email ?: "Non disponible"}")
            ExpandableSection(
                textId = R.string.settings_modify_password_label
            ) {
                AuthTextField(
                    value = oldPassword,
                    onValueChange = { oldPassword = it },
                    label = R.string.settings_modify_password_old_password_label,
                    isPassword = true,
                    icon = Icons.Rounded.Password
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

                if (modifyPasswordState is ModifyPasswordState.Success) {
                    TextBody1Regular(
                        text = "Mot de passe modifié !",
                        color = success,
                    )
                }

                ButtonPrimary(
                    onClick = {
                        profileViewModel.modifyPassword(oldPassword, newPassword, confirmNewPassword)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    text = "Modifier son mot de passe"
                )
            }

            if (modifyPasswordState is ModifyPasswordState.Error) {
                TextBody1Regular(
                    textId = (modifyPasswordState as ModifyPasswordState.Error).messageId,
                    color = fail,
                )
            }

            if (logoutState is LogoutState.Error) {
                TextBody1Regular(
                    textId = (logoutState as LogoutState.Error).messageId,
                    color = fail,
                )
            }

            ButtonPrimary(
                onClick = {
                    profileViewModel.logout()
                },
                modifier = Modifier.fillMaxWidth(),
                text = "Se déconnecter"
            )
        }
        LaunchedEffect(logoutState) {
            if (logoutState is LogoutState.Success) {
                goPortal()
            }
        }
    }
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepositoryInterface
) : ViewModel() {

    private val _logoutState = MutableStateFlow<LogoutState>(LogoutState.Idle)
    val logoutState: StateFlow<LogoutState> = _logoutState

    private val _modifyPasswordState = MutableStateFlow<ModifyPasswordState>(ModifyPasswordState.Idle)
    val modifyPasswordState: StateFlow<ModifyPasswordState> = _modifyPasswordState

    private val _email = MutableStateFlow<String?>(null)
    val email: StateFlow<String?> = _email

    private val _username = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> = _username

    init {
        viewModelScope.launch {
            val token = authRepository.getAuthToken()
            _email.value = token?.let { authRepository.extractEmailFromToken(it) }
            _username.value = token?.let { authRepository.extractUsernameFromToken(it) }
        }
    }

    fun logout() {
        _logoutState.value = LogoutState.Loading
        viewModelScope.launch {
            try {
                authRepository.logout()
                _logoutState.value = LogoutState.Success
            } catch (e: Exception) {
                _logoutState.value = LogoutState.Error(R.string.settings_error_logout_fail)
            }
        }
    }

    fun modifyPassword(oldPassword: String, newPassword: String, confirmNewPassword: String) {
        if (oldPassword.isBlank() || newPassword.isBlank() || confirmNewPassword.isBlank()) {
            _modifyPasswordState.value = ModifyPasswordState.Error(R.string.portal_error_empty_field)
            return
        }
        if (newPassword != confirmNewPassword) {
            _modifyPasswordState.value = ModifyPasswordState.Error(R.string.portal_error_different_passwords)
            return
        }
        _modifyPasswordState.value = ModifyPasswordState.Loading
        viewModelScope.launch {
            try {
                val success = authRepository.modifyPassword(oldPassword, newPassword)
                if (success) {
                    _modifyPasswordState.value = ModifyPasswordState.Success
                } else {
                    _modifyPasswordState.value = ModifyPasswordState.Error(R.string.settings_modify_password_error_old_password_invalid)
                }
            } catch (e: Exception) {
                _modifyPasswordState.value = ModifyPasswordState.Error(R.string.portal_error_login_netword_issue)
            }
        }
    }
}

sealed class LogoutState {
    object Idle : LogoutState()
    object Loading : LogoutState()
    object Success : LogoutState()
    data class Error(val messageId: Int) : LogoutState()
}

sealed class ModifyPasswordState {
    object Idle : ModifyPasswordState()
    object Loading : ModifyPasswordState()
    object Success : ModifyPasswordState()
    data class Error(val messageId: Int) : ModifyPasswordState()
}