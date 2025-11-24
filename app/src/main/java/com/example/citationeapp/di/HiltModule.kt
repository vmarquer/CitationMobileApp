package com.example.citationeapp.di

import com.example.citationeapp.R
import com.example.citationeapp.data.models.Citation
import com.example.citationeapp.data.models.CitationVersion
import com.example.citationeapp.data.models.Film
import com.example.citationeapp.data.models.getAnswer
import com.example.citationeapp.data.remote.repositories.AuthRepository
import com.example.citationeapp.data.remote.repositories.AuthRepositoryInterface
import com.example.citationeapp.data.remote.repositories.CitationRepository
import com.example.citationeapp.data.remote.repositories.CitationRepositoryInterface
import com.example.citationeapp.ui.screens.home.GameMode
import com.example.citationeapp.ui.screens.play.PlayUiState
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface HiltModule {
    @Singleton
    @Binds
    fun bindsCitationRepository(
        configRepository: CitationRepository
    ): CitationRepositoryInterface

    @Singleton
    @Binds
    fun bindsAuthRepository(
        configRepository: AuthRepository
    ): AuthRepositoryInterface
}

class FakeCitationRepository @Inject constructor() : CitationRepositoryInterface {

    private val _currentCitation = MutableStateFlow<Citation?>(null)

    private val _uiState = MutableStateFlow<PlayUiState>(PlayUiState.Loading)
    override val uiState: StateFlow<PlayUiState> = _uiState.asStateFlow()
    private val _quizSize = MutableStateFlow(5)
    override val quizSize: StateFlow<Int> = _quizSize.asStateFlow()
    private val _version = MutableStateFlow(CitationVersion.VF)
    override val version: StateFlow<CitationVersion> = _version.asStateFlow()

    private val _gameMode = MutableStateFlow<GameMode>(GameMode.ALL)

    private val citationsMock: MutableList<Citation> = citations
    private val moviesMock: List<Film> = movies

    override fun setGameMode(mode: GameMode) {
        _gameMode.value = mode
    }

    override suspend fun updateQuizSize(size: Int) {
       _quizSize.value = size
    }

    override suspend fun updateVersion(version: CitationVersion) {
        _version.value = version
    }

    override suspend fun getRandomCitation() {
        _uiState.value = PlayUiState.Loading
        try {
            val citation = citationsMock.random()
            val choices = moviesMock
                .filter { it.kind == citation.kind && it.id != citation.answerId }
                .take(3)
                .toMutableList()

            moviesMock.find { it.id == citation.answerId }?.let { choices.add(it) }

            val citationWithChoices = citation.copy(choices = choices.shuffled())

            _currentCitation.value = citationWithChoices
            _uiState.value = PlayUiState.Question(
                citation = citationWithChoices,
                currentIndex = 0,
                quizSize = 0
            )
        } catch (e: Exception) {
            _uiState.value = PlayUiState.Error(R.string.error_unknown)
        }
    }

    override suspend fun submitAnswer(citationId: Int, answerId: Int) {
        val current = _currentCitation.value
        if (current == null) {
            _uiState.value = PlayUiState.Error(R.string.error_no_current_citation)
            return
        }

        _uiState.value = PlayUiState.Loading

        try {
            val citation = citationsMock.find { it.id == citationId }
                ?: run {
                    _uiState.value = PlayUiState.Error(R.string.error_not_found)
                    return
                }

            val correctAnswer = citation.getAnswer()
            val isCorrect = correctAnswer?.id == answerId

            val updatedCitation = citation.copy(
                userGuessMovieVF = correctAnswer?.titleVF,
                userGuessMovieVO = correctAnswer?.titleVO,
                result = isCorrect
            )

            _currentCitation.value = updatedCitation
            _uiState.value = PlayUiState.Answer(
                citation = updatedCitation,
                currentIndex = 0,
                quizSize = 0
            )
        } catch (e: Exception) {
            _uiState.value = PlayUiState.Error(R.string.error_unknown)
        }
    }

    override fun resetValues() {
        _gameMode.value = GameMode.ALL
    }
}

