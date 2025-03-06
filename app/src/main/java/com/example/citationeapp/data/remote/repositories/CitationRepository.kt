package com.example.citationeapp.data.remote.repositories

import com.example.citationeapp.data.remote.api.CitationApiService
import com.example.citationeapp.data.remote.dto.CitationAnswerRequestDTO
import com.example.citationeapp.data.remote.dto.CitationAnswerResponseDTO
import com.example.citationeapp.data.remote.dto.CitationLightDto
import retrofit2.Response
import javax.inject.Inject

interface CitationRepositoryInterface {
    suspend fun getRandomCitation(): Response<CitationLightDto>
    suspend fun postAnswer(id: Int, answer: CitationAnswerRequestDTO): Response<CitationAnswerResponseDTO>
}

class CitationRepository @Inject constructor(
    private val apiService: CitationApiService
) : CitationRepositoryInterface {

    override suspend fun getRandomCitation(): Response<CitationLightDto> {
        return apiService.getRandomCitation()
    }

    override suspend fun postAnswer(id: Int, answer: CitationAnswerRequestDTO): Response<CitationAnswerResponseDTO> {
        return apiService.postCitationAnswer(id, answer)
    }
}

