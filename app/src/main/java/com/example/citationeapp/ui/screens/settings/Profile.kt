package com.example.citationeapp.ui.screens.settings

import ButtonPrimary
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citationeapp.R
import com.example.citationeapp.data.remote.dto.UtilisateurInfosDTO
import com.example.citationeapp.data.remote.repositories.AuthRepositoryInterface
import com.example.citationeapp.ui.theme.components.ConfirmationDialog
import com.example.citationeapp.ui.theme.components.TextBody1Bold
import com.example.citationeapp.ui.theme.components.TextBody1Regular
import com.example.citationeapp.ui.theme.grey
import com.example.citationeapp.ui.theme.iconVeryLargeSize
import com.example.citationeapp.ui.theme.lineHeightLarge
import com.example.citationeapp.ui.theme.lineHeightMedium
import com.example.citationeapp.ui.theme.lineHeightSmall
import com.example.citationeapp.ui.theme.padding12
import com.example.citationeapp.ui.theme.padding2
import com.example.citationeapp.ui.theme.padding24
import com.example.citationeapp.ui.theme.primary
import com.example.citationeapp.ui.theme.profileBoxSize
import com.example.citationeapp.ui.theme.progressColor
import com.example.citationeapp.ui.theme.spacing16
import com.example.citationeapp.ui.theme.spacing8
import com.example.citationeapp.ui.theme.userScoreLargeHeight
import com.example.citationeapp.ui.theme.white
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
    val userInfos by viewModel.userInfos.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (logoutState is LogoutState.Loading) {
        Column(
            modifier = modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(color = primary)
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding12),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(spacing16),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(profileBoxSize)
                        .clip(CircleShape)
                        .background(grey),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = white,
                            modifier = Modifier.size(iconVeryLargeSize)
                        )
                        TextBody1Bold(
                            text = userInfos.username,
                            color = white
                        )
                    }

                }
                TextBody1Regular(text = userInfos.email)
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = modifier.size(userScoreLargeHeight)
                ) {
                    val ratio = userInfos.goodAnswers.toFloat() / userInfos.answers.toFloat()
                    CircularProgressIndicator(
                        progress = { 1f },
                        strokeWidth = lineHeightMedium,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding2),
                        color = grey.copy(alpha = 0.3f)
                    )
                    CircularProgressIndicator(
                        progress = { ratio },
                        strokeWidth = lineHeightLarge,
                        modifier = Modifier.fillMaxSize(),
                        color = progressColor(ratio)
                    )
                    TextBody1Bold(
                        text = "${userInfos.goodAnswers}/${userInfos.answers}",
                    )
                }
            }
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = padding24),
                verticalArrangement = Arrangement.spacedBy(spacing8)
            ) {
                ButtonPrimary(
                    onClick = goModifyPassword,
                    modifier = Modifier.fillMaxWidth(),
                    textId = R.string.button_modify_password
                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = padding12),
                    thickness = lineHeightSmall,
                    color = grey
                )
                ButtonPrimary(
                    onClick = { showLogoutDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    textId = R.string.button_logout
                )
            }
        }
        if (showLogoutDialog) {
            ConfirmationDialog(
                titleId = R.string.profile_logout_dialog_title,
                messageId = R.string.profile_logout_dialog_message,
                onConfirm = {
                    showLogoutDialog = false
                    viewModel.logout()
                },
                onDismiss = { showLogoutDialog = false }
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

    val userInfos: StateFlow<UtilisateurInfosDTO> = authRepository.userInfos

    init {
        viewModelScope.launch {
            authRepository.getUserInfos()
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