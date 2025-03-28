package com.example.citationeapp.di

import com.example.citationeapp.data.domain.mapper.toCitationAnswerResponseDto
import com.example.citationeapp.data.domain.mapper.toCitationLightDto
import com.example.citationeapp.data.models.Citation
import com.example.citationeapp.data.models.Film
import com.example.citationeapp.data.models.getAnswer
import com.example.citationeapp.data.remote.dto.CitationAnswerResponseDTO
import com.example.citationeapp.data.remote.dto.CitationLightDto
import com.example.citationeapp.data.remote.repositories.AuthRepository
import com.example.citationeapp.data.remote.repositories.AuthRepositoryInterface
import com.example.citationeapp.data.remote.repositories.CitationRepository
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
        configRepository: CitationRepository
    ): CitationRepositoryInterface

    @Singleton
    @Binds
    fun bindsAuthRepository(
        configRepository: AuthRepository
    ): AuthRepositoryInterface
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

    override suspend fun postAnswer(citationId: Int, answerId: Int): Response<CitationAnswerResponseDTO> {
        val citation = citationsMock.find { it.id == citationId } ?: return Response.error(404, okhttp3.ResponseBody.create(null, "Citation Not Found"))
        val movieAnswer = citation.getAnswer()
        val result = answerId == movieAnswer?.id
        val updatedCitation = citation.copy(
            userGuessMovieVF = movieAnswer?.titleVF,
            userGuessMovieVO = movieAnswer?.titleVO,
            result = result
        )
        return Response.success(updatedCitation.toCitationAnswerResponseDto())
    }
}

