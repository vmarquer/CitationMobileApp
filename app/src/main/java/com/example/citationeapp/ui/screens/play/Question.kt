package com.example.citationeapp.ui.screens.play

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.citationeapp.ui.theme.CustomBox
import com.example.citationeapp.ui.theme.components.TextH3
import com.example.citationeapp.ui.theme.components.TextInputWithButton
import com.example.citationeapp.ui.theme.customBoxHeightQuestion
import com.example.citationeapp.ui.theme.padding32
import com.example.citationeapp.ui.theme.spacing24
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@Composable
fun Question(
    modifier: Modifier = Modifier,
    viewModel: QuestionViewModel = hiltViewModel(),
    goToAnswer: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    // équivalent de NgOnInit
    LaunchedEffect(Unit) {}

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(padding32)
            .verticalScroll(rememberScrollState())
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { focusManager.clearFocus() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = spacing24, alignment = Alignment.CenterVertically
        )
    ) {
        CustomBox(
            verticalAlignment = Alignment.Center,
            height = customBoxHeightQuestion
        ) {
            TextH3(
                text = "Test citation",
                textAlign = TextAlign.Center
            )
        }
        TextInputWithButton (
            onSendClick = {
                focusManager.clearFocus()
                goToAnswer()
            }
        )
    }
}

data class QuestionUIState(
    val name: String = "",
)

@HiltViewModel
class QuestionViewModel @Inject constructor(
    // call repositories
) : ViewModel() {
    private val _uiState = MutableStateFlow(QuestionUIState())
    val uiState: StateFlow<QuestionUIState> = _uiState
}