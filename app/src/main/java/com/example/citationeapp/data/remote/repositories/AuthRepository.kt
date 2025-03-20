package com.example.citationeapp.data.remote.repositories

import com.example.citationeapp.data.preferences.UserPreferences
import com.example.citationeapp.data.remote.api.AuthApiService
import com.example.citationeapp.data.remote.dto.AuthRequestDTO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

interface AuthRepositoryInterface {
    suspend fun login(username: String, password: String): Boolean
    suspend fun logout()
    fun getAuthToken(): String?
}

class AuthRepository @Inject constructor(
    private val authApiService: AuthApiService,
    private val userPreferences: UserPreferences
) : AuthRepositoryInterface {

    override suspend fun login(username: String, password: String): Boolean {
        return try {
            val response = authApiService.login(AuthRequestDTO(username, password))
            if (response.isSuccessful) {
                val authResponse = response.body()
                authResponse?.let {
                    userPreferences.saveAuthToken(it.bearer, it.refresh)
                    return true
                } ?: run {
                    false
                }
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }


    override suspend fun logout() {
        authApiService.logout()
        userPreferences.clearAuthToken()
    }

    override fun getAuthToken(): String? {
        return runBlocking { userPreferences.authToken.first() }
    }
}

