package com.example.citationeapp.ui.screens.play

import ButtonPrimary
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citationeapp.R
import com.example.citationeapp.data.models.Citation
import com.example.citationeapp.data.models.CitationVersion
import com.example.citationeapp.data.remote.repositories.AuthRepositoryInterface
import com.example.citationeapp.data.remote.repositories.CitationRepositoryInterface
import com.example.citationeapp.ui.theme.components.TextBody1Regular
import com.example.citationeapp.ui.theme.components.TextBody2Regular
import com.example.citationeapp.ui.theme.fail
import com.example.citationeapp.ui.theme.primary
import com.example.citationeapp.ui.theme.spacing24
import com.example.citationeapp.ui.theme.white
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun Play(
    modifier: Modifier = Modifier,
    viewModel: PlayViewModel = hiltViewModel(),
    goHome: () -> Unit,
    onForceLogin: () -> Unit,
) {
    var showExitDialog by remember { mutableStateOf(false) }
    BackHandler(enabled = true) {
        showExitDialog = true
    }

    val playState = viewModel.uiState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.resetValues()
        viewModel.startQuiz(onForceLogin)
    }

    when (playState) {
        is PlayUiState.Loading ->
            Column(
                modifier = modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(color = primary)
            }

        is PlayUiState.Question ->  {
            Question(
                citation = playState.citation,
                version = viewModel.version,
                currentIndex = playState.currentIndex,
                quizSize = playState.quizSize,
                onSubmitAnswer = { citationId, userAnswerId ->
                    viewModel.submitAnswer(citationId, userAnswerId, onForceLogin)
                }
            )
        }

        is PlayUiState.Answer -> {
            Answer(
                citation = playState.citation,
                version = viewModel.version,
                currentIndex = playState.currentIndex,
                quizSize = playState.quizSize,
                onPlayAgain = { viewModel.goToNextCitation() }
            )
        }

        is PlayUiState.Result -> {
            Result(
                version = viewModel.version,
                usedCitations = playState.usedCitations,
                quizSize = playState.quizSize,
                playAgain = {
                    viewModel.resetValues()
                    viewModel.startQuiz(onForceLogin)
                },
                goHome = {
                    viewModel.resetValues()
                    goHome()
                }
            )
        }

        is PlayUiState.Error -> {
            Column(
                modifier = modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextBody1Regular(
                    textId = playState.messageId,
                    color = fail,
                    modifier = Modifier.padding(spacing24)
                )
            }
        }
    }
    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null
                )
            },
            title = {
                TextBody2Regular(textId = R.string.play_exit_dialog_title)
            },
            text = {
                TextBody2Regular(textId = R.string.play_exit_dialog_message)
            },
            confirmButton = {
                ButtonPrimary(
                    modifier = Modifier,
                    textId = R.string.button_yes,
                    onClick = {
                        showExitDialog = false
                        viewModel.resetValues()
                        goHome()
                    }
                )
            },
            dismissButton = {
                ButtonPrimary(
                    modifier = Modifier,
                    textId = R.string.button_no,
                    onClick = { showExitDialog = false }
                )
            },
            containerColor = white
        )
    }
}

@HiltViewModel
class PlayViewModel @Inject constructor(
    private val citationRepository: CitationRepositoryInterface,
    private val authRepository: AuthRepositoryInterface
) : ViewModel() {
    val uiState: StateFlow<PlayUiState> = citationRepository.uiState
    var version: CitationVersion = CitationVersion.VF
        private set

    var quizSize by mutableStateOf(5)
        private set

    init {
        viewModelScope.launch {
            citationRepository.version.collect { newVersion ->
                version = newVersion
            }
        }
        viewModelScope.launch {
            citationRepository.quizSize.collect { size ->
                quizSize = size
            }
        }
    }

    fun startQuiz(onForceLogin: () -> Unit) {
        viewModelScope.launch {
            if (authRepository.isBearerTokenExpired(authRepository.getBearerToken())) {
                val refreshed = authRepository.askRefreshToken(authRepository.getRefreshToken())
                if (!refreshed) {
                    authRepository.logout()
                    onForceLogin()
                    return@launch
                }
            }
            citationRepository.startQuiz()
        }
    }

    fun submitAnswer(citationId: Int, userAnswerId: Int, onForceLogin: () -> Unit) {
        viewModelScope.launch {
            if (authRepository.isBearerTokenExpired(authRepository.getBearerToken())) {
                val refreshed = authRepository.askRefreshToken(authRepository.getRefreshToken())
                if (!refreshed) {
                    authRepository.logout()
                    onForceLogin()
                    return@launch
                }
            }
            citationRepository.submitAnswer(citationId, userAnswerId)
        }
    }

    fun goToNextCitation() {
        citationRepository.goToNextCitation()
    }

    fun resetValues() {
        citationRepository.resetValues()
    }
}

sealed class PlayUiState {
    object Loading : PlayUiState()
    data class Question(
        val citation: Citation,
        val currentIndex: Int,
        val quizSize: Int
    ) : PlayUiState()
    data class Answer(
        val citation: Citation,
        val currentIndex: Int,
        val quizSize: Int
    ) : PlayUiState()
    data class Result(val usedCitations: List<Citation>, val quizSize: Int) : PlayUiState()
    data class Error(val messageId: Int) : PlayUiState()
}