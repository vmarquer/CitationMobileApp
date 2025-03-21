package com.example.citationeapp.ui.screens.play

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citationeapp.data.domain.mapper.toCitation
import com.example.citationeapp.data.domain.mapper.updateWithResponse
import com.example.citationeapp.data.models.Citation
import com.example.citationeapp.data.remote.repositories.CitationRepositoryInterface
import com.example.citationeapp.data.remote.repositories.VersionRepository
import com.example.citationeapp.ui.theme.components.TextBody1Regular
import com.example.citationeapp.ui.theme.fail
import com.example.citationeapp.ui.theme.padding16
import com.example.citationeapp.ui.theme.spacing16
import com.example.citationeapp.ui.theme.spacing24
import com.example.citationeapp.viewmodel.CitationVersion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun Play(
    modifier: Modifier = Modifier,
    viewModel: PlayViewModel = hiltViewModel(),
) {
    val playState = viewModel.playState.collectAsState().value
    val citation = viewModel.citation.collectAsState().value

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(padding16)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = spacing16, alignment = Alignment.CenterVertically
        )
    ) {

        when (playState) {
            is PlayState.Loading -> CircularProgressIndicator()

            is PlayState.Question -> citation?.let {
                Question(
                    citation = it,
                    version = viewModel.version,
                    onSubmitAnswer = { citationId, userAnswerId,  ->
                        viewModel.submitAnswer(citationId, userAnswerId)
                    }
                )
            }

            is PlayState.Answer -> citation?.let {
                Answer(
                    citation = it,
                    version = viewModel.version,
                    onPlayAgain = { viewModel.playAgain() }
                )
            }

            is PlayState.Error -> {
                TextBody1Regular(
                    text = "Erreur : ${playState.message}",
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
    private val versionRepository: VersionRepository
) : ViewModel() {

    private val _citation = MutableStateFlow<Citation?>(null)
    val citation: StateFlow<Citation?> = _citation

    private var _playState = MutableStateFlow<PlayState>(PlayState.Loading)
    val playState: StateFlow<PlayState> = _playState

    var version: CitationVersion = CitationVersion.VF
        private set

    init {
        loadRandomCitation()
        viewModelScope.launch {
            versionRepository.versionFlow.collect { newVersion ->
                version = newVersion
            }
        }
    }

    fun loadRandomCitation() {
        _playState.value = PlayState.Loading
        viewModelScope.launch {
            try {
                val response = citationRepository.getRandomCitation()
                if (response.isSuccessful) {
                    response.body()?.let { citationLight ->
                        val newCitation = citationLight.toCitation()
                        _citation.value = newCitation
                        _playState.value = PlayState.Question
                    }
                } else {
                    _playState.value = PlayState.Error("Erreur ${response.code()} : ${response.message()}")
                }
            } catch (e: Exception) {
                _playState.value = PlayState.Error("Exception : ${e.message}")
            }
        }
    }

    fun submitAnswer(citationId: Int, userAnswerId: Int) {
        viewModelScope.launch {
            _citation.value?.let { currentCitation ->
                try {
                    val response = citationRepository.postAnswer(citationId, userAnswerId)
                    if (response.isSuccessful) {
                        response.body()?.let { answerResponse ->
                            val userGuessMovie = currentCitation.choices.find { it.id == userAnswerId }
                            _citation.value = currentCitation.updateWithResponse(answerResponse, userGuessMovie)
                            _playState.value = PlayState.Answer
                        }
                    } else {
                        _playState.value = PlayState.Error("Erreur lors de l'envoi de la réponse.")
                    }
                } catch (e: Exception) {
                    _playState.value = PlayState.Error("Une erreur s'est produite : ${e.message}")
                }
            }
        }
    }

    fun playAgain() {
        loadRandomCitation()
    }
}

sealed class PlayState {
    object Loading : PlayState()
    object Question : PlayState()
    object Answer : PlayState()
    data class Error(val message: String) : PlayState()
}