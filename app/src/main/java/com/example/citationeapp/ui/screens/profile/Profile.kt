package com.example.citationeapp.ui.screens.profile

import ButtonPrimary
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.citationeapp.data.remote.repositories.AuthRepositoryInterface
import com.example.citationeapp.ui.theme.components.ConfirmationDialog
import com.example.citationeapp.ui.theme.components.TextBody1Regular
import com.example.citationeapp.ui.theme.padding12
import com.example.citationeapp.ui.theme.spacing8
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun Profile(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    goModifyPassword: () -> Unit,
    goPortal: () -> Unit
) {
    val logoutState by viewModel.logoutState.collectAsState()
    val email by viewModel.email.collectAsState()
    val username by viewModel.username.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding12)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(spacing8)
    ) {
        if (logoutState is LogoutState.Loading) {
            CircularProgressIndicator()
        } else {
            TextBody1Regular(text = "Nom d'utilisateur: ${username ?: "Non disponible"}")
            TextBody1Regular(text = "Email: ${email ?: "Non disponible"}")

            ButtonPrimary(
                onClick = goModifyPassword,
                modifier = Modifier.fillMaxWidth(),
                text = "Modifier son mot de passe"
            )

            ButtonPrimary(
                onClick = { showLogoutDialog = true },
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

    if (showLogoutDialog) {
        ConfirmationDialog(
            title = "Déconnexion",
            message = "Êtes-vous sûr de vouloir vous déconnecter ?",
            onConfirm = {
                showLogoutDialog = false
                viewModel.logout()
            },
            onDismiss = { showLogoutDialog = false }
        )
    }
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepositoryInterface
) : ViewModel() {

    private val _logoutState = MutableStateFlow<LogoutState>(LogoutState.Idle)
    val logoutState: StateFlow<LogoutState> = _logoutState

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
            val response = authRepository.logout()
            if (response) {
                _logoutState.value = LogoutState.Success
            }
        }
    }
}

sealed class LogoutState {
    object Idle : LogoutState()
    object Loading : LogoutState()
    object Success : LogoutState()
}