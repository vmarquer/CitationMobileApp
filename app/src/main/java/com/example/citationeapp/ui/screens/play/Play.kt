package com.example.citationeapp.ui.screens.play

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citationeapp.data.models.Citation
import com.example.citationeapp.data.models.CitationVersion
import com.example.citationeapp.data.remote.repositories.AuthRepositoryInterface
import com.example.citationeapp.data.remote.repositories.CitationRepositoryInterface
import com.example.citationeapp.data.remote.repositories.VersionRepository
import com.example.citationeapp.ui.theme.components.TextBody1Regular
import com.example.citationeapp.ui.theme.fail
import com.example.citationeapp.ui.theme.primary
import com.example.citationeapp.ui.theme.spacing24
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
    val playState = viewModel.uiState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.loadRandomCitation(onForceLogin)
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
                onPlayAgain = { viewModel.loadRandomCitation(onForceLogin) }
            )
        }

        is PlayUiState.Result -> {
            Result(
                currentIndex = playState.currentIndex,
                quizSize = playState.quizSize,
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
}

@HiltViewModel
class PlayViewModel @Inject constructor(
    private val citationRepository: CitationRepositoryInterface,
    private val authRepository: AuthRepositoryInterface,
    private val versionRepository: VersionRepository
) : ViewModel() {
    val uiState: StateFlow<PlayUiState> = citationRepository.uiState
    var version: CitationVersion = CitationVersion.VF
        private set

    init {
        viewModelScope.launch {
            versionRepository.versionFlow.collect { newVersion ->
                version = newVersion
            }
        }
    }

    fun loadRandomCitation(onForceLogin: () -> Unit) {
        viewModelScope.launch {
            if (authRepository.isBearerTokenExpired(authRepository.getBearerToken())) {
                val refreshed = authRepository.askRefreshToken(authRepository.getRefreshToken())
                if (!refreshed) {
                    authRepository.logout()
                    onForceLogin()
                    return@launch
                }
            }
            citationRepository.getRandomCitation()
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
    data class Result(val currentIndex: Int, val quizSize: Int) : PlayUiState()
    data class Error(val messageId: Int) : PlayUiState()
}