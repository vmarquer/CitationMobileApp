package com.example.citationeapp.data.remote.repositories

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.example.citationeapp.R
import com.example.citationeapp.data.domain.mapper.toCitation
import com.example.citationeapp.data.domain.mapper.updateWithImage
import com.example.citationeapp.data.domain.mapper.updateWithResponse
import com.example.citationeapp.data.models.Citation
import com.example.citationeapp.data.models.CitationVersion
import com.example.citationeapp.data.preferences.UserPreferences
import com.example.citationeapp.data.remote.api.CitationApiService
import com.example.citationeapp.data.remote.dto.CitationAnswerRequestDTO
import com.example.citationeapp.data.remote.dto.CitationAnswerResponseDTO
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
    suspend fun startQuiz()
    suspend fun submitAnswer(citationId: Int, answerId: Int)
    fun goToNextCitation()
    suspend fun fetchImage(filmId: Int): ImageBitmap?
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
    private val _quizCitations = MutableStateFlow<List<Citation>>(emptyList())
    val quizCitations = _quizCitations.asStateFlow()
    private val _gameMode = MutableStateFlow<GameMode>(GameMode.ALL)
    private var currentIndex = 0

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

    override suspend fun startQuiz() {
        _uiState.value = PlayUiState.Loading
        try {
            val response = apiService.startQuiz(
                quizSize = quizSize.value,
                gameMode = _gameMode.value.name.uppercase()
            )
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
            val body = response.body()
            if (body == null) {
                _uiState.value = PlayUiState.Error(R.string.error_empty_answer_from_server)
                return
            }
            _quizCitations.value = body.map { it.toCitation() }
            _uiState.value = PlayUiState.Question(
                citation = _quizCitations.value[currentIndex],
                currentIndex = currentIndex + 1,
                quizSize = quizSize.value
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


    override suspend fun submitAnswer(citationId: Int, answerId: Int) {
        val size = quizSize.value
        val currentCitation = _quizCitations.value[currentIndex]
        val email = authRepository.extractEmailFromToken()
        if (email == null) {
            _uiState.value = PlayUiState.Error(R.string.error_no_email)
            return
        }
        _uiState.value = PlayUiState.Loading
        try {
            val response: Response<CitationAnswerResponseDTO> =
                apiService.postCitationAnswer(citationId, CitationAnswerRequestDTO(answerId, email))
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
            val answerResponse = response.body()
            if (answerResponse == null) {
                _uiState.value = PlayUiState.Error(R.string.error_empty_answer_from_server)
                return
            }
            val userGuessMovie = currentCitation.choices.find { it.id == answerId }
            val citation = currentCitation.updateWithResponse(answerResponse, userGuessMovie)
            val imageBitmap = fetchImage(answerResponse.answerId)
            val updatedCitation = citation.updateWithImage(imageBitmap)
            val updatedList = _quizCitations.value.toMutableList().also {
                it[currentIndex] = updatedCitation
            }
            _quizCitations.value = updatedList
            _uiState.value = PlayUiState.Answer(
                citation = _quizCitations.value[currentIndex],
                currentIndex = currentIndex + 1,
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

    override fun goToNextCitation() {
        val size = _quizCitations.value.size
        if (currentIndex + 1 >= size) {
            _uiState.value = PlayUiState.Result(
                usedCitations = _quizCitations.value,
                quizSize = size
            )
        } else {
            currentIndex++
            _uiState.value = PlayUiState.Question(
                citation = _quizCitations.value[currentIndex],
                currentIndex = currentIndex + 1,
                quizSize = size
            )
        }
    }


    override suspend fun fetchImage(filmId: Int): ImageBitmap? {
        return try {
            val response = apiService.getFilmImage(filmId)

            if (!response.isSuccessful) return null

            val bytes = response.body()?.bytes() ?: return null

            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            bitmap.asImageBitmap()
        } catch (e: Exception) {
            null
        }
    }

    override fun resetValues() {
        _gameMode.value = GameMode.ALL
        currentIndex = 0
        _quizCitations.value = emptyList()
    }
}

