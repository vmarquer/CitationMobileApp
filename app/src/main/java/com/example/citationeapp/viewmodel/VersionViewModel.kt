package com.example.citationeapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.citationeapp.data.preferences.PrefsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class VersionViewModel @Inject constructor(
    private val prefsManager: PrefsManager
) : ViewModel() {
    private val _version = MutableStateFlow(prefsManager.getVersion())
    val version: StateFlow<CitationVersion> = _version

    fun toggleVersion(version: CitationVersion) {
        _version.value = version
        prefsManager.saveVersion(version)
    }
}



enum class CitationVersion(val displayName: String) {
    VO("Version Originale"),
    VF("Version Française");

    companion object {
        fun fromString(value: String): CitationVersion {
            return entries.find { it.name.equals(value, ignoreCase = true) }
                ?: throw IllegalArgumentException("Version non valide: $value")
        }
    }
}
