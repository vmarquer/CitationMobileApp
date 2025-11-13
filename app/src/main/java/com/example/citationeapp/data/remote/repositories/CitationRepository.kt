package com.example.citationeapp.data.remote.repositories

import com.example.citationeapp.R
import com.example.citationeapp.data.domain.mapper.toCitation
import com.example.citationeapp.data.domain.mapper.updateWithResponse
import com.example.citationeapp.data.models.Citation
import com.example.citationeapp.data.remote.api.CitationApiService
import com.example.citationeapp.data.remote.dto.CitationAnswerRequestDTO
import com.example.citationeapp.data.remote.dto.CitationAnswerResponseDTO
import com.example.citationeapp.data.remote.dto.CitationLightDto
import com.example.citationeapp.ui.screens.home.GameMode
import com.example.citationeapp.ui.screens.play.PlayUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Response
import javax.inject.Inject

interface CitationRepositoryInterface {
    val uiState: StateFlow<PlayUiState>
    fun setGameMode(mode: GameMode)
    suspend fun getRandomCitation()
    suspend fun submitAnswer(citationId: Int, answerId: Int)
    fun resetValues()
}

class CitationRepository @Inject constructor(
    private val apiService: CitationApiService,
    private val authRepository: AuthRepositoryInterface
) : CitationRepositoryInterface {

    companion object {
        private const val QUIZ_SIZE = 2
    }
    private val _uiState = MutableStateFlow<PlayUiState>(PlayUiState.Loading)
    override val uiState = _uiState.asStateFlow()
    private val _currentCitation = MutableStateFlow<Citation?>(null)
    private val _gameMode = MutableStateFlow<GameMode>(GameMode.ALL)
    private var currentIndex = 0

    override fun setGameMode(mode: GameMode) {
        _gameMode.value = mode
    }

    override suspend fun getRandomCitation() {
        if(currentIndex == QUIZ_SIZE) {
            _uiState.value = PlayUiState.Result(
                currentIndex = currentIndex,
                quizSize = QUIZ_SIZE
            )
            return
        }
        currentIndex ++
        _uiState.value = PlayUiState.Loading
        try {
            val response: Response<CitationLightDto> = when (_gameMode.value) {
                GameMode.ALL -> apiService.getRandomCitation()
                GameMode.FILMS -> apiService.getRandomCitationByKind("movie")
                GameMode.SERIES -> apiService.getRandomCitationByKind("serie")
            }
            if (response.isSuccessful) {
                response.body()?.let { dto ->
                    val citation = dto.toCitation()
                    _currentCitation.value = citation
                    _uiState.value = PlayUiState.Question(
                        citation = citation,
                        currentIndex = currentIndex,
                        quizSize = QUIZ_SIZE
                    )
                } ?: run {
                    _uiState.value = PlayUiState.Error(R.string.error_empty_answer_from_server)
                }
            } else {
                val messageId = when (response.code()) {
                    401 -> R.string.error_unauthorized
                    403 -> R.string.error_forbidden
                    404 -> R.string.error_not_found
                    500 -> R.string.error_server
                    else -> R.string.error_unknown
                }
                _uiState.value = PlayUiState.Error(messageId)
            }

        } catch (e: Exception) {
            val messageId = when (e) {
                is java.net.UnknownHostException -> R.string.error_no_internet
                is java.net.SocketTimeoutException -> R.string.error_timeout
                else -> R.string.error_unknown
            }
            _uiState.value = PlayUiState.Error(messageId)
        }

    }

    override suspend fun submitAnswer(citationId: Int, userAnswerId: Int) {
        val currentCitation = _currentCitation.value
        if (currentCitation == null) {
            _uiState.value = PlayUiState.Error(R.string.error_no_current_citation)
            return
        }
        val email = authRepository.extractEmailFromToken()
        if (email == null) {
            _uiState.value = PlayUiState.Error(R.string.error_no_email)
            return
        }
        _uiState.value = PlayUiState.Loading
        try {
            val response: Response<CitationAnswerResponseDTO> =
                apiService.postCitationAnswer(citationId, CitationAnswerRequestDTO(userAnswerId, email))
            if (response.isSuccessful) {
                response.body()?.let { answerResponse ->
                    val userGuessMovie = currentCitation.choices.find { it.id == userAnswerId }
                    val updatedCitation =
                        currentCitation.updateWithResponse(answerResponse, userGuessMovie)
                    _currentCitation.value = updatedCitation
                    _uiState.value = PlayUiState.Answer(
                        citation = updatedCitation,
                        currentIndex = currentIndex,
                        quizSize = QUIZ_SIZE
                    )
                } ?: run {
                    _uiState.value = PlayUiState.Error(R.string.error_empty_answer_from_server)
                }
            } else {
                val messageId = when (response.code()) {
                    401 -> R.string.error_unauthorized
                    403 -> R.string.error_forbidden
                    404 -> R.string.error_not_found
                    500 -> R.string.error_server
                    else -> R.string.error_unknown
                }
                _uiState.value = PlayUiState.Error(messageId)
            }
        } catch (e: Exception) {
            val messageId = when (e) {
                is java.net.UnknownHostException -> R.string.error_no_internet
                is java.net.SocketTimeoutException -> R.string.error_timeout
                else -> R.string.error_unknown
            }
            _uiState.value = PlayUiState.Error(messageId)
        }
    }

    override fun resetValues() {
        _gameMode.value = GameMode.ALL
        currentIndex = 0
    }
}

