package com.example.citationeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citationeapp.data.domain.mapper.toCitation
import com.example.citationeapp.data.models.Citation
import com.example.citationeapp.data.remote.dto.CitationAnswerRequestDTO
import com.example.citationeapp.data.remote.repositories.CitationRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CitationUIState(
    val currentCitation: Citation? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)

@HiltViewModel
class CitationViewModel @Inject constructor(
    private val repository: CitationRepositoryInterface
) : ViewModel() {

    private val _uiState = MutableStateFlow(CitationUIState())
    val uiState: StateFlow<CitationUIState> = _uiState

    fun getRandomCitation() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val response = repository.getRandomCitation()
                if (response.isSuccessful) {
                    response.body()?.let { citationResponse ->
                        val citation = citationResponse.toCitation()
                        _uiState.value = CitationUIState(currentCitation = citation)
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        currentCitation = null,
                        isError = true,
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isError = true,
                )
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun sendAnwser(id: Int, answer: CitationAnswerRequestDTO) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val currentCitation = _uiState.value.currentCitation ?: return@launch
            val selectedMovie = currentCitation.choices.find { it.id == answer.userAnswerId }
            if (selectedMovie == null) {
                _uiState.value = _uiState.value.copy(isError = true, isLoading = false)
                return@launch
            }
            try {
                val response = repository.postAnswer(id, answer)
                if (response.isSuccessful) {
                    response.body()?.let { citationAnswerResponse ->
                        _uiState.value = _uiState.value.copy(
                            currentCitation = currentCitation.copy(
                                userGuessMovieVO = selectedMovie.titleVO,
                                userGuessMovieVF = selectedMovie.titleVF,
                                caracter = citationAnswerResponse.caracter,
                                actor = citationAnswerResponse.actor,
                                answerId = citationAnswerResponse.answerId,
                                result = citationAnswerResponse.result
                            )
                        )
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        isError = true,
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isError = true,
                )
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}
