package com.example.citationeapp.data.remote.repositories

import android.util.Base64
import com.example.citationeapp.data.preferences.UserPreferences
import com.example.citationeapp.data.remote.api.AuthApiService
import com.example.citationeapp.data.remote.dto.ActivationRequestDTO
import com.example.citationeapp.data.remote.dto.AskNewPasswordDTO
import com.example.citationeapp.data.remote.dto.AuthRequestDTO
import com.example.citationeapp.data.remote.dto.ModifyPasswordDTO
import com.example.citationeapp.data.remote.dto.NewPasswordRequestDTO
import com.example.citationeapp.data.remote.dto.RefreshTokenRequestDTO
import com.example.citationeapp.data.remote.dto.RegisterRequestDTO
import com.example.citationeapp.data.remote.dto.UtilisateurInfosDTO
import com.example.citationeapp.ui.theme.fail
import com.example.citationeapp.ui.theme.success
import com.example.citationeapp.utils.ToastManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.util.Date
import javax.inject.Inject

interface AuthRepositoryInterface {
    val userInfos: StateFlow<UtilisateurInfosDTO>
    suspend fun register(username: String, email: String, password: String): Boolean
    suspend fun activate(code: String): Boolean
    suspend fun login(username: String, password: String): Boolean
    suspend fun askNewPassword(email: String): Boolean
    suspend fun sendNewPassword(email: String, newPassword: String, code: String): Boolean
    suspend fun modifyPassword(email: String, oldPassword: String, newPassword: String): Boolean
    suspend fun logout(): Boolean
    fun getBearerToken(): String?
    fun getRefreshToken(): String?
    fun saveAuthTokens(token: String, refreshToken: String)
    fun extractEmailFromToken(): String?
    fun extractUsernameFromToken(): String?
    suspend fun checkAuthentication(): Boolean
    suspend fun askRefreshToken(refreshToken: String?): Boolean
    suspend fun getUserInfos(): Boolean
    fun isBearerTokenExpired(token: String?): Boolean
}

class AuthRepository @Inject constructor(
    private val authApiService: AuthApiService,
    private val userPreferences: UserPreferences
) : AuthRepositoryInterface {

    private val _userInfos = MutableStateFlow(UtilisateurInfosDTO("", "", "", 0, 0))
    override val userInfos: StateFlow<UtilisateurInfosDTO> = _userInfos.asStateFlow()

    override suspend fun register(username: String, email: String, password: String): Boolean {
        return try {
            val response = authApiService.register(RegisterRequestDTO(email, username, password))
            if (response.isSuccessful) {
                ToastManager.showMessage("Inscription réussie !", success)
                true
            } else {
                ToastManager.showMessage("${response.code()} : ${response.message()}", fail)
                false
            }
        } catch (e: Exception) {
            ToastManager.showMessage("Exception : ${e.message}", fail)
            false
        }
    }

    override suspend fun activate(code: String): Boolean {
        return try {
            val response = authApiService.activate(ActivationRequestDTO(code))
            if (response.isSuccessful) {
                ToastManager.showMessage("Code validé !", success)
                true
            } else {
                ToastManager.showMessage("${response.code()} : ${response.message()}", fail)
                false
            }
        } catch (e: Exception) {
            ToastManager.showMessage("Exception : ${e.message}", fail)
            false
        }
    }


    override suspend fun login(username: String, password: String): Boolean {
        return try {
            val response = authApiService.login(AuthRequestDTO(username, password))
            if (response.isSuccessful) {
                val authResponse = response.body()
                authResponse?.let {
                    userPreferences.saveAuthTokens(it.bearer, it.refresh)
                    return true
                } ?: run {
                    false
                }
            } else {
                false
            }
        } catch (e: Exception) {
            ToastManager.showMessage("Exception : ${e.message}", fail)
            false
        }
    }

    override suspend fun askNewPassword(email: String): Boolean {
        return try {
            val response = authApiService.askNewPassword(AskNewPasswordDTO(email))
            if (response.isSuccessful) {
                true
            } else {
                ToastManager.showMessage("${response.code()} : ${response.message()}", fail)
                false
            }
        } catch (e: Exception) {
            ToastManager.showMessage("Exception : ${e.message}", fail)
            false
        }
    }

    override suspend fun sendNewPassword(
        email: String,
        newPassword: String,
        code: String
    ): Boolean {
        return try {
            val response =
                authApiService.sendNewPassword(NewPasswordRequestDTO(email, newPassword, code))
            if (response.isSuccessful) {
                true
            } else {
                ToastManager.showMessage("${response.code()} : ${response.message()}", fail)
                false
            }
        } catch (e: Exception) {
            ToastManager.showMessage("Exception : ${e.message}", fail)
            false
        }
    }

    override suspend fun modifyPassword(
        email: String,
        oldPassword: String,
        newPassword: String
    ): Boolean {
        return try {
            val response =
                authApiService.modifyPassword(ModifyPasswordDTO(email, oldPassword, newPassword))
            if (response.isSuccessful) {
                ToastManager.showMessage("Mot de passe modifié !", success)
                true
            } else {
                ToastManager.showMessage("${response.code()} : ${response.message()}", fail)
                false
            }
        } catch (e: Exception) {
            ToastManager.showMessage("Exception : ${e.message}", fail)
            false
        }
    }

    override suspend fun logout(): Boolean {
        return try {
            authApiService.logout()
            userPreferences.clearAuthTokens()
            ToastManager.showMessage("Déconnexion réussie !", success)
            true
        } catch (e: Exception) {
            ToastManager.showMessage("Exception : ${e.message}", fail)
            false
        }
    }

    override fun getBearerToken(): String? {
        return runBlocking { userPreferences.bearerToken.first() }
    }

    override fun getRefreshToken(): String? {
        return runBlocking { userPreferences.refreshToken.first() }
    }

    override fun saveAuthTokens(token: String, refreshToken: String) {
        runBlocking { userPreferences.saveAuthTokens(token, refreshToken) }
    }

    override fun extractEmailFromToken(): String? {
        val token = getBearerToken()
        if (token.isNullOrBlank()) return null
        return try {
            val parts = token.split(".")
            if (parts.size != 3) return null
            val payload = String(Base64.decode(parts[1], Base64.DEFAULT))
            val json = JSONObject(payload)
            json.optString("sub", null)
        } catch (e: Exception) {
            null
        }
    }

    override fun extractUsernameFromToken(): String? {
        val token = getBearerToken()
        if (token.isNullOrBlank()) return null
        return try {
            val parts = token.split(".")
            if (parts.size != 3) return null
            val payload = String(Base64.decode(parts[1], Base64.DEFAULT))
            val json = JSONObject(payload)
            json.optString("name", null)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun checkAuthentication(): Boolean {
        val token = getBearerToken()
        return token != null && token.isNotEmpty()
    }

    override suspend fun askRefreshToken(refreshToken: String?): Boolean {
        if (refreshToken.isNullOrBlank()) return false
        return try {
            val response = authApiService.refreshToken(RefreshTokenRequestDTO(refreshToken))
            if (response.isSuccessful) {
                val authResponse = response.body()
                authResponse?.let {
                    userPreferences.saveAuthTokens(it.bearer, it.refresh)
                    return true
                }
                false
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getUserInfos(): Boolean {
        return try {
            val response = authApiService.getUserInfos()
            if (response.isSuccessful) {
                response.body()?.let { infos ->
                    _userInfos.value = infos
                    true
                } ?: false
            } else {
                println("test")
                ToastManager.showMessage("${response.code()} : ${response.message()}", fail)
                false
            }
        } catch (e: Exception) {
            ToastManager.showMessage("Exception : ${e.message}", fail)
            false
        }
    }

    override fun isBearerTokenExpired(bearerToken: String?): Boolean {
        if (bearerToken.isNullOrBlank()) return true
        return try {
            val parts = bearerToken.split(".")
            if (parts.size != 3) return true
            val payload = String(Base64.decode(parts[1], Base64.DEFAULT))
            val json = JSONObject(payload)
            val exp = json.optLong("exp", 0)
            val expirationDate = Date(exp * 1000)
            expirationDate.before(Date())
        } catch (e: Exception) {
            true
        }
    }
}

