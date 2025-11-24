package com.example.citationeapp.data.remote.api

import com.example.citationeapp.data.remote.dto.ActivationRequestDTO
import com.example.citationeapp.data.remote.dto.AskNewPasswordDTO
import com.example.citationeapp.data.remote.dto.AskUtilisateurInfos
import com.example.citationeapp.data.remote.dto.AuthRequestDTO
import com.example.citationeapp.data.remote.dto.AuthResponseDTO
import com.example.citationeapp.data.remote.dto.ModifyPasswordDTO
import com.example.citationeapp.data.remote.dto.NewPasswordRequestDTO
import com.example.citationeapp.data.remote.dto.RefreshTokenRequestDTO
import com.example.citationeapp.data.remote.dto.RegisterRequestDTO
import com.example.citationeapp.data.remote.dto.UtilisateurInfosDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiService {

    @POST("register")
    suspend fun register(@Body request: RegisterRequestDTO): Response<Void>

    @POST("activation")
    suspend fun activate(@Body code: ActivationRequestDTO): Response<Void>

    @POST("connection")
    suspend fun login(@Body request: AuthRequestDTO): Response<AuthResponseDTO>

    @POST("disconnection")
    suspend fun logout(): Response<Void>

    @POST("modify-password")
    suspend fun modifyPassword(@Body request: ModifyPasswordDTO): Response<Void>

    @POST("ask-new-password")
    suspend fun askNewPassword(@Body request: AskNewPasswordDTO): Response<Void>

    @POST("send-new-password")
    suspend fun sendNewPassword(@Body request: NewPasswordRequestDTO): Response<Void>

    @POST("refresh-token")
    suspend fun refreshToken(@Body request: RefreshTokenRequestDTO): Response<AuthResponseDTO>

    @POST("user-infos")
    suspend fun getUserInfos(@Body request: AskUtilisateurInfos): Response<UtilisateurInfosDTO>
}
