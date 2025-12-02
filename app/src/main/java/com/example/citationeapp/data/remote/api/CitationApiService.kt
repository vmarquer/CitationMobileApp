package com.example.citationeapp.data.remote.api

import com.example.citationeapp.data.remote.dto.CitationAnswerRequestDTO
import com.example.citationeapp.data.remote.dto.CitationAnswerResponseDTO
import com.example.citationeapp.data.remote.dto.CitationLightDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CitationApiService {

    @GET("citation")
    suspend fun getAllCitations(): Response<List<CitationLightDto>>

    @GET("citation/{id}")
    suspend fun getCitationById(@Path("id") id: Int): Response<CitationLightDto>

    @GET("citation/start-quiz")
    suspend fun startQuiz(
        @Query("quizSize") quizSize: Int,
        @Query("gameMode") gameMode: String
    ): Response<List<CitationLightDto>>

    @POST("citation/{id}")
    suspend fun postCitationAnswer(
        @Path("id") id: Int,
        @Body citationAnswer: CitationAnswerRequestDTO
    ): Response<CitationAnswerResponseDTO>

    @GET("img/{id}")
    suspend fun getFilmImage(@Path("id") id: Int): Response<ResponseBody>
}