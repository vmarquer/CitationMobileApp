package com.example.citationeapp.di

import com.example.citationeapp.data.domain.mapper.toCitationAnswerResponseDto
import com.example.citationeapp.data.domain.mapper.toCitationLightDto
import com.example.citationeapp.data.models.Citation
import com.example.citationeapp.data.models.Film
import com.example.citationeapp.data.models.getAnswer
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
val moviesMock: List<Film> = movies

class FakeCitationRepository @Inject constructor() : CitationRepositoryInterface {

    override suspend fun getRandomCitation(): Response<CitationLightDto> {
        val citation = citationsMock.random()
        val choices = moviesMock
            .filter { it.kind == citation.kind && it.id != citation.answerId }
            .take(3)
            .toMutableList()
        moviesMock.find { it.id == citation.answerId }?.let { choices.add(it) }
        val citationWithChoices = citation.copy(choices = choices.shuffled())
        return Response.success(citationWithChoices.toCitationLightDto())
    }

    override suspend fun postAnswer(id: Int, answer: CitationAnswerRequestDTO): Response<CitationAnswerResponseDTO> {
        val citation = citationsMock.find { it.id == id } ?: citationsMock.first()
        val movieAnswer = citation.getAnswer()
        val result = answer.userAnswerId == citation.answerId
        val updatedCitation = citation.copy(
            userGuessMovieVF = movieAnswer?.titleVF,
            userGuessMovieVO = movieAnswer?.titleVO,
            result = result
        )
        citationsMock[citationsMock.indexOf(citation)] = updatedCitation
        return Response.success(updatedCitation.toCitationAnswerResponseDto())
    }
}

