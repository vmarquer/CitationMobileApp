package com.example.citationeapp.ui.screens.portal

import ButtonPrimary
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
fun Validation(
    modifier: Modifier = Modifier,
    goLogin: () -> Unit,
    viewModel: ValidationViewModel = hiltViewModel()
) {
    var activationCode by remember { mutableStateOf("") }

    val validationState by viewModel.validationState.collectAsState()

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
        if (validationState is ValidationState.Loading) {
            CircularProgressIndicator()
        } else {
            TextBody1Bold(textId = R.string.portal_activation_code)

            AuthTextField(
                value = activationCode,
                onValueChange = { activationCode = it },
                label = R.string.portal_activation_code,
                isPassword = false
            )

            if (validationState is ValidationState.Error) {
                TextBody1Regular(
                    textId = (validationState as ValidationState.Error).messageId,
                    color = fail,
                )
            }

            ButtonPrimary(
                onClick = { viewModel.activate(activationCode) },
                modifier = Modifier.fillMaxWidth(),
                textId = R.string.portal_activation_code_submit
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

    LaunchedEffect(validationState) {
        if (validationState is ValidationState.Success) {
            goLogin()
        }
    }
}

@HiltViewModel
class ValidationViewModel @Inject constructor(
    private val authRepository: AuthRepositoryInterface
) : ViewModel() {

    private val _validationState = MutableStateFlow<ValidationState>(ValidationState.Idle)
    val validationState: StateFlow<ValidationState> = _validationState

    fun activate(code: String) {
        if (!code.matches(Regex("^\\d{6}$"))) {
            _validationState.value = ValidationState.Error(R.string.portal_activation_code_error_incorrect_length)
            return
        }
        _validationState.value = ValidationState.Loading
        viewModelScope.launch {
            val success = authRepository.activate(code)
            if (success) {
                _validationState.value = ValidationState.Success
            } else {
                _validationState.value = ValidationState.Error(R.string.portal_activation_code_error_invalid)
            }
        }
    }
}

sealed class ValidationState {
    object Idle : ValidationState()
    object Loading : ValidationState()
    object Success : ValidationState()
    data class Error(val messageId: Int) : ValidationState()
}
