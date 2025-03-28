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
import com.example.citationeapp.data.domain.mapper.toCitation
import com.example.citationeapp.data.domain.mapper.updateWithResponse
import com.example.citationeapp.data.models.Citation
import com.example.citationeapp.data.models.CitationVersion
import com.example.citationeapp.data.remote.repositories.AuthRepository
import com.example.citationeapp.data.remote.repositories.AuthRepositoryInterface
import com.example.citationeapp.data.remote.repositories.CitationRepositoryInterface
import com.example.citationeapp.data.remote.repositories.VersionRepository
import com.example.citationeapp.ui.theme.components.TextBody1Regular
import com.example.citationeapp.ui.theme.fail
import com.example.citationeapp.ui.theme.primary
import com.example.citationeapp.ui.theme.spacing24
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun Play(
    modifier: Modifier = Modifier,
    viewModel: PlayViewModel = hiltViewModel(),
    onForceLogin: () -> Unit,
) {
    val playState = viewModel.playState.collectAsState().value
    val citation = viewModel.citation.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.loadRandomCitation(onForceLogin)
    }

    when (playState) {
        is PlayState.Loading ->
            Column(
                modifier = modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(color = primary)
            }

        is PlayState.Question -> citation?.let {
            Question(
                citation = it,
                version = viewModel.version,
                onSubmitAnswer = { citationId, userAnswerId ->
                    viewModel.submitAnswer(citationId, userAnswerId, onForceLogin)
                }
            )
        }

        is PlayState.Answer -> citation?.let {
            Answer(
                citation = it,
                version = viewModel.version,
                onPlayAgain = { viewModel.loadRandomCitation(onForceLogin) }
            )
        }

        is PlayState.Error -> {
            Column(
                modifier = modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextBody1Regular(
                    text = playState.message,
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

    private val _citation = MutableStateFlow<Citation?>(null)
    val citation: StateFlow<Citation?> = _citation

    private var _playState = MutableStateFlow<PlayState>(PlayState.Loading)
    val playState: StateFlow<PlayState> = _playState

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
        _playState.value = PlayState.Loading
        viewModelScope.launch {
            try {
                if (authRepository.isBearerTokenExpired(authRepository.getBearerToken())) {
                    val response = authRepository.askRefreshToken(authRepository.getRefreshToken())
                    if (!response) {
                        authRepository.logout()
                        onForceLogin()
                        return@launch
                    }
                }
                val response = citationRepository.getRandomCitation()
                if (response.isSuccessful) {
                    response.body()?.let { citationLight ->
                        val newCitation = citationLight.toCitation()
                        _citation.value = newCitation
                        _playState.value = PlayState.Question
                    }
                } else {
                    _playState.value = PlayState.Error("${response.code()} : ${response.message()}")
                }
            } catch (e: Exception) {
                _playState.value = PlayState.Error("Exception : ${e.message}")
            }
        }
    }

    fun submitAnswer(citationId: Int, userAnswerId: Int, onForceLogin: () -> Unit) {
        viewModelScope.launch {
            _citation.value?.let { currentCitation ->
                try {
                    if (authRepository.isBearerTokenExpired(authRepository.getBearerToken())) {
                        val response = authRepository.askRefreshToken(authRepository.getRefreshToken())
                        if (!response) {
                            onForceLogin
                            return@launch
                        }
                    }
                    val response = citationRepository.postAnswer(citationId, userAnswerId)
                    if (response.isSuccessful) {
                        response.body()?.let { answerResponse ->
                            val userGuessMovie = currentCitation.choices.find { it.id == userAnswerId }
                            _citation.value = currentCitation.updateWithResponse(answerResponse, userGuessMovie)
                            _playState.value = PlayState.Answer
                        }
                    } else {
                        _playState.value = PlayState.Error("${response.code()} : ${response.message()}")
                    }
                } catch (e: Exception) {
                    _playState.value = PlayState.Error("Exception : ${e.message}")
                }
            }
        }
    }
}

sealed class PlayState {
    object Loading : PlayState()
    object Question : PlayState()
    object Answer : PlayState()
    data class Error(val message: String) : PlayState()
}