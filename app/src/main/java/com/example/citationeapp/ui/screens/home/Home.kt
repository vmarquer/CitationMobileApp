package com.example.citationeapp.ui.screens.home

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
fun Home(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current


    // équivalent de NgOnInit
    LaunchedEffect(Unit) {}

    TextBottomBar(textId = R.string.home_bottom_bar, color = primary)
}

data class HomeUIState(
    val name: String = "",
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    // call repositories
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState
}