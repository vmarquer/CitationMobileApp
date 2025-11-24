package com.example.citationeapp.data.remote.repositories

import com.example.citationeapp.R
import com.example.citationeapp.data.domain.mapper.toCitation
import com.example.citationeapp.data.domain.mapper.updateWithResponse
import com.example.citationeapp.data.models.Citation
import com.example.citationeapp.data.models.CitationVersion
import com.example.citationeapp.data.preferences.UserPreferences
import com.example.citationeapp.data.remote.api.CitationApiService
import com.example.citationeapp.data.remote.dto.CitationAnswerRequestDTO
import com.example.citationeapp.data.remote.dto.CitationAnswerResponseDTO
import com.example.citationeapp.data.remote.dto.CitationLightDto
import com.example.citationeapp.ui.screens.home.GameMode
import com.example.citationeapp.ui.screens.play.PlayUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

interface CitationRepositoryInterface {
    val uiState: StateFlow<PlayUiState>
    val quizSize: StateFlow<Int>
    val version: StateFlow<CitationVersion>
    fun setGameMode(mode: GameMode)
    suspend fun updateQuizSize(size: Int)
    suspend fun updateVersion(version: CitationVersion)
    suspend fun getRandomCitation()
    suspend fun submitAnswer(citationId: Int, answerId: Int)
    fun resetValues()
}

class CitationRepository @Inject constructor(
    private val apiService: CitationApiService,
    private val authRepository: AuthRepositoryInterface,
    private val userPreferences: UserPreferences
) : CitationRepositoryInterface {
    private val _uiState = MutableStateFlow<PlayUiState>(PlayUiState.Loading)
    override val uiState = _uiState.asStateFlow()
    private val _quizSize = MutableStateFlow(5)
    override val quizSize: StateFlow<Int> = _quizSize.asStateFlow()
    private val _version = MutableStateFlow(CitationVersion.VF)
    override val version: StateFlow<CitationVersion> = _version.asStateFlow()
    private val _currentCitation = MutableStateFlow<Citation?>(null)
    private val _gameMode = MutableStateFlow<GameMode>(GameMode.ALL)
    private var currentIndex = 0
    private val usedCitations = mutableListOf<Citation>()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            userPreferences.quizSize.collect { size ->
                _quizSize.value = size
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            userPreferences.version.collect { versionString ->
                _version.value = CitationVersion.fromString(versionString)
            }
        }
    }

    override fun setGameMode(mode: GameMode) {
        _gameMode.value = mode
    }

    override suspend fun updateQuizSize(size: Int) {
        userPreferences.saveQuizSize(size)
    }

    override suspend fun updateVersion(version: CitationVersion) {
        userPreferences.saveVersion(version)
    }

    override suspend fun getRandomCitation() {
        val size = quizSize.value
        if (currentIndex == size) {
            _uiState.value = PlayUiState.Result(
                usedCitations = usedCitations,
                quizSize = size
            )
            return
        }
        _uiState.value = PlayUiState.Loading
        try {
            var citation: Citation? = null
            var retries = 0
            val maxRetries = 10
            while (citation == null && retries < maxRetries) {
                retries++
                val response: Response<CitationLightDto> = when (_gameMode.value) {
                    GameMode.ALL -> apiService.getRandomCitation()
                    GameMode.FILMS -> apiService.getRandomCitationByKind("movie")
                    GameMode.SERIES -> apiService.getRandomCitationByKind("serie")
                }
                if (!response.isSuccessful) {
                    val messageId = when (response.code()) {
                        401 -> R.string.error_unauthorized
                        403 -> R.string.error_forbidden
                        404 -> R.string.error_not_found
                        500 -> R.string.error_server
                        else -> R.string.error_unknown
                    }
                    _uiState.value = PlayUiState.Error(messageId)
                    return
                }
                response.body()?.let { dto ->
                    val newCitation = dto.toCitation()
                    if (usedCitations.any { it.id == newCitation.id }) {
                        return@let
                    }
                    citation = newCitation
                }
            }
            if (citation == null) {
                _uiState.value = PlayUiState.Error(R.string.error_no_current_citation)
                return
            }
            currentIndex++
            _currentCitation.value = citation
            _uiState.value = PlayUiState.Question(
                citation = citation,
                currentIndex = currentIndex,
                quizSize = size
            )
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
        val size = quizSize.value
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
                    val existingIndex = usedCitations.indexOfFirst { it.id == updatedCitation.id }
                    if (existingIndex >= 0) {
                        usedCitations[existingIndex] = updatedCitation
                    } else {
                        usedCitations.add(updatedCitation)
                    }
                    _uiState.value = PlayUiState.Answer(
                        citation = updatedCitation,
                        currentIndex = currentIndex,
                        quizSize = size
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
        usedCitations.clear()
    }
}

