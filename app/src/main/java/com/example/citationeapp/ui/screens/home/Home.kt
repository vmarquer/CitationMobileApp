package com.example.citationeapp.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@Composable
fun Home(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
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
        TextScreenTitle(
            textId = R.string.home_bottom_bar,
            color = black,
            textAlign = TextAlign.Center
        )
    }
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