package com.example.citationeapp.ui.screens.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.citationeapp.R
import com.example.citationeapp.ui.theme.components.TextBottomBar
import com.example.citationeapp.ui.theme.primary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@Composable
fun Profile(
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current


    // équivalent de NgOnInit
    LaunchedEffect(Unit) {}

    TextBottomBar(textId = R.string.profile_bottom_bar, color = primary)

}

data class ProfileUIState(
    val name: String = "",
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    // call repositories
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUIState())
    val uiState: StateFlow<ProfileUIState> = _uiState
}