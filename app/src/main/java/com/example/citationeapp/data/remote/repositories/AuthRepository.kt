package com.example.citationeapp.data.remote.repositories

import android.util.Base64
import com.example.citationeapp.data.preferences.UserPreferences
import com.example.citationeapp.data.remote.api.AuthApiService
import com.example.citationeapp.data.remote.dto.ActivationRequestDTO
import com.example.citationeapp.data.remote.dto.AskNewPasswordDTO
import com.example.citationeapp.data.remote.dto.AuthRequestDTO
import com.example.citationeapp.data.remote.dto.ModifyPasswordDTO
import com.example.citationeapp.data.remote.dto.NewPasswordRequestDTO
import com.example.citationeapp.data.remote.dto.RegisterRequestDTO
import com.example.citationeapp.ui.theme.fail
import com.example.citationeapp.ui.theme.success
import com.example.citationeapp.utils.ToastManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import javax.inject.Inject

interface AuthRepositoryInterface {
    suspend fun register(username: String, email: String, password: String): Boolean
    suspend fun activate(code: String): Boolean
    suspend fun login(username: String, password: String): Boolean
    suspend fun askNewPassword(email: String): Boolean
    suspend fun sendNewPassword(email: String, newPassword: String, code: String): Boolean
    suspend fun modifyPassword(oldPassword: String, newPassword: String): Boolean
    suspend fun logout(): Boolean
    fun getAuthToken(): String?
    suspend fun checkAuthentication(): Boolean
    fun extractEmailFromToken(token: String?): String?
    fun extractUsernameFromToken(token: String?): String?
}

class AuthRepository @Inject constructor(
    private val authApiService: AuthApiService,
    private val userPreferences: UserPreferences
) : AuthRepositoryInterface {

    override suspend fun register(username: String, email: String, password: String): Boolean {
        return try {
            val response = authApiService.register(RegisterRequestDTO(email, username, password))
            if (response.isSuccessful) {
                ToastManager.showMessage("Inscription réussie !", success)
                true
            } else {
                ToastManager.showMessage("Erreur d'inscription !", fail)
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
                ToastManager.showMessage("Code invalide !", fail)
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
                    userPreferences.saveAuthToken(it.bearer, it.refresh)
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
                ToastManager.showMessage("Demande rejetée !", fail)
                false
            }
        } catch (e: Exception) {
            ToastManager.showMessage("Exception : ${e.message}", fail)
            false
        }
    }

    override suspend fun sendNewPassword(email: String, newPassword: String, code: String): Boolean {
        return try {
            val response = authApiService.sendNewPassword(NewPasswordRequestDTO(email, newPassword, code))
            if (response.isSuccessful) {
                true
            } else {
                false
            }
        } catch (e: Exception) {
            ToastManager.showMessage("Exception : ${e.message}", fail)
            false
        }
    }

    override suspend fun modifyPassword(oldPassword: String, newPassword: String): Boolean {
        return try {
            val email = getAuthToken()?.let { extractEmailFromToken(it) } ?: return false
            val response = authApiService.modifyPassword(ModifyPasswordDTO(email, oldPassword, newPassword))
            if (response.isSuccessful) {
                ToastManager.showMessage("Mot de passe modifié !", success)
                true
            } else {
                ToastManager.showMessage("Mot de passe non modifié !", fail)
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
            userPreferences.clearAuthToken()
            ToastManager.showMessage("Déconnexion réussie !", success)
            true
        } catch (e: Exception) {
            ToastManager.showMessage("Exception : ${e.message}", fail)
            false
        }
    }

    override fun getAuthToken(): String? {
        return runBlocking { userPreferences.authToken.first() }
    }

    override suspend fun checkAuthentication(): Boolean {
        val token = getAuthToken()
        return token != null && token.isNotEmpty()
    }

    override fun extractEmailFromToken(token: String?): String? {
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

    override fun extractUsernameFromToken(token: String?): String? {
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
}

