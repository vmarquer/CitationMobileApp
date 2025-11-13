package com.example.citationeapp.data.remote.api

import com.example.citationeapp.data.remote.dto.CitationAnswerRequestDTO
import com.example.citationeapp.data.remote.dto.CitationAnswerResponseDTO
import com.example.citationeapp.data.remote.dto.CitationLightDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CitationApiService {

    @GET("citation")
    suspend fun getAllCitations(): Response<List<CitationLightDto>>

    @GET("citation/{id}")
    suspend fun getCitationById(@Path("id") id: Int): Response<CitationLightDto>

    @POST("citation/{id}")
    suspend fun postCitationAnswer(
        @Path("id") id: Int,
        @Body citationAnswer: CitationAnswerRequestDTO
    ): Response<CitationAnswerResponseDTO>

    @GET("citation/random")
    suspend fun getRandomCitation(): Response<CitationLightDto>

    @GET("citation/random/{kind}")
    suspend fun getRandomCitationByKind( @Path("kind") kind: String): Response<CitationLightDto>
}