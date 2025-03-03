package com.example.citationeapp.ui.screens.profile

import RoundedIconButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.citationeapp.R
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.components.TextScreenTitle
import com.example.citationeapp.ui.theme.padding16
import com.example.citationeapp.ui.theme.spacing16
import com.example.citationeapp.ui.theme.white
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@Composable
fun Settings(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current


    // équivalent de NgOnInit
    LaunchedEffect(Unit) {}

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding16)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(spacing16)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RoundedIconButton(
                iconId = R.drawable.ic_back,
                color = black,
                backgroundColor = white,
                modifier = Modifier.weight(1f),
                onClick = onBack,
            )

            TextScreenTitle(
                textId = R.string.settings_title,
                color = black,
                modifier = Modifier.weight(3f),
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.weight(1f),
            )
        }
    }
}

data class SettingsUIState(
    val name: String = "",
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    // call repositories
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUIState())
    val uiState: StateFlow<ProfileUIState> = _uiState
}