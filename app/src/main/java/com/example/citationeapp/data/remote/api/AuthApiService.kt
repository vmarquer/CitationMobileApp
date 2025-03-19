package com.example.citationeapp.data.remote.api

import com.example.citationeapp.data.remote.dto.AuthRequestDTO
import com.example.citationeapp.data.remote.dto.AuthResponseDTO
import com.example.citationeapp.data.remote.dto.NewPasswordRequestDTO
import com.example.citationeapp.data.remote.dto.RefreshTokenRequestDTO
import com.example.citationeapp.data.remote.dto.RegisterRequestDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("register")
    suspend fun register(@Body request: RegisterRequestDTO)

    @POST("activation")
    suspend fun activate(@Body activation: Map<String, String>)

    @POST("connection")
    suspend fun login(@Body request: AuthRequestDTO): Response<AuthResponseDTO>

    @POST("disconnection")
    suspend fun logout(): Response<Void>

    @POST("modify-password")
    suspend fun modifyPassword(@Body parameters: Map<String, String>)

    @POST("new-password")
    suspend fun newPassword(@Body request: NewPasswordRequestDTO)

    @POST("refresh-token")
    suspend fun refreshToken(@Body request: RefreshTokenRequestDTO): AuthResponseDTO
}
