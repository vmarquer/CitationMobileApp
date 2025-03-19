package com.example.citationeapp.ui.screens.profile

import ProfileButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citationeapp.R
import com.example.citationeapp.data.remote.repositories.AuthRepositoryInterface
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.components.CheckableRow
import com.example.citationeapp.ui.theme.components.TextH3Bold
import com.example.citationeapp.ui.theme.grey
import com.example.citationeapp.ui.theme.padding12
import com.example.citationeapp.ui.theme.spacing2
import com.example.citationeapp.ui.theme.spacing8
import com.example.citationeapp.viewmodel.CitationVersion
import com.example.citationeapp.viewmodel.VersionViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun Settings(
    modifier: Modifier = Modifier,
    versionViewModel: VersionViewModel,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    showProfile: () -> Unit,
    showDesignSystem: () -> Unit
) {
    val version by versionViewModel.version.collectAsState()

    val logoutState by settingsViewModel.logoutState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding12)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(spacing8)
    ) {
        ProfileButton(
            modifier = Modifier.fillMaxWidth(),
            textId = R.string.settings_profile_title,
            onClick = showProfile,
            iconId = R.drawable.ic_profile,
            colorIcon = black
        )

        ProfileButton(
            modifier = Modifier.fillMaxWidth(),
            textId = R.string.settings_design_system_title,
            onClick = showDesignSystem,
            iconId = R.drawable.ic_settings,
            colorIcon = black
        )

        Column(
            modifier = modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(spacing2)
        ) {
            TextH3Bold(
                textId = R.string.settings_version_title,
                color = grey
            )
            CheckableRow(
                text = CitationVersion.VO.displayName,
                isChecked = version == CitationVersion.VO,
                onCheckedChange = { isChecked ->
                    if (isChecked) {
                        versionViewModel.toggleVersion(CitationVersion.VO)
                    }
                }
            )
            CheckableRow(
                text = CitationVersion.VF.displayName,
                isChecked = version == CitationVersion.VF,
                onCheckedChange = { isChecked ->
                    if (isChecked) {
                        versionViewModel.toggleVersion(CitationVersion.VF)
                    }
                }
            )
        }
    }
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepository: AuthRepositoryInterface
) : ViewModel() {

    private val _logoutState = MutableStateFlow<LogoutState>(LogoutState.Idle)
    val logoutState: StateFlow<LogoutState> = _logoutState

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
}

sealed class LogoutState {
    object Idle : LogoutState()
    object Loading : LogoutState()
    object Success : LogoutState()
    data class Error(val messageId: Int) : LogoutState()
}
