package com.example.citationeapp.di

import com.example.citationeapp.data.domain.mapper.toCitationAnswerResponseDto
import com.example.citationeapp.data.domain.mapper.toCitationLightDto
import com.example.citationeapp.data.models.Citation
import com.example.citationeapp.data.remote.dto.CitationAnswerRequestDTO
import com.example.citationeapp.data.remote.dto.CitationAnswerResponseDTO
import com.example.citationeapp.data.remote.dto.CitationLightDto
import com.example.citationeapp.data.remote.repositories.CitationRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface HiltModule {
    @Singleton
    @Binds
    fun bindsCitationRepository(
        configRepository: FakeCitationRepository
    ): CitationRepositoryInterface
}

val citationsMock: MutableList<Citation> = citations

class FakeCitationRepository @Inject constructor() : CitationRepositoryInterface {

    override suspend fun getRandomCitation(): Response<CitationLightDto> {
        return Response.success(citationsMock.random().toCitationLightDto());
    }

    override suspend fun postAnswer(id: Int, answer: CitationAnswerRequestDTO): Response<CitationAnswerResponseDTO> {
        val citation = citationsMock[id].copy(
            userGuessMovieVF = answer.movieVF,
            userGuessMovieVO = answer.movieVO,
            result = answer.movieVO == citationsMock[id].movieVO || answer.movieVF == citationsMock[id].movieVF
        )
        citationsMock[id] = citation
        return Response.success(citation.toCitationAnswerResponseDto())
    }
}

