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
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citationeapp.R
import com.example.citationeapp.data.remote.repositories.AuthRepositoryInterface
import com.example.citationeapp.ui.theme.components.TextBody1Regular
import com.example.citationeapp.ui.theme.fail
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
    profileViewModel: ProfileViewModel = hiltViewModel(),
    goPortal: () -> Unit
) {

    val profileState by profileViewModel.profileState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding12)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(spacing8)
    ) {
        if (profileState is ProfileState.Loading) {
            CircularProgressIndicator()
        } else {
            ButtonPrimary(
                onClick = {
                    profileViewModel.logout()
                },
                modifier = Modifier.fillMaxWidth(),
                text = "Se déconnecter"
            )

            if (profileState is ProfileState.Error) {
                val errorMessageId = (profileState as ProfileState.Error).messageId
                TextBody1Regular(
                    textId = errorMessageId,
                    color = fail,
                )
            }
        }
        LaunchedEffect(profileState) {
            if (profileState is ProfileState.Success) {
                goPortal()
            }
        }
    }
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepositoryInterface
) : ViewModel() {

    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Idle)
    val profileState: StateFlow<ProfileState> = _profileState

    fun logout() {
        _profileState.value = ProfileState.Loading
        viewModelScope.launch {
            try {
                authRepository.logout()
                _profileState.value = ProfileState.Success
            } catch (e: Exception) {
                _profileState.value = ProfileState.Error(R.string.settings_error_logout_fail)
            }
        }
    }
}

sealed class ProfileState {
    object Idle : ProfileState()
    object Loading : ProfileState()
    object Success : ProfileState()
    data class Error(val messageId: Int) : ProfileState()
}