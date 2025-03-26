package com.example.citationeapp.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citationeapp.R
import com.example.citationeapp.data.preferences.UserPreferences
import com.example.citationeapp.viewmodel.CitationVersion.entries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VersionViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _version = MutableStateFlow(CitationVersion.VF)
    val version: StateFlow<CitationVersion> = _version

    init {
        viewModelScope.launch {
            userPreferences.version.collect { versionString ->
                _version.value = CitationVersion.fromString(versionString)
            }
        }
    }

    fun toggleVersion(version: CitationVersion) {
        _version.value = version
        viewModelScope.launch {
            userPreferences.saveVersion(version)
        }
    }
}

enum class CitationVersion(@StringRes val displayNameRes: Int) {
    VO(R.string.settings_version_original),
    VF(R.string.settings_version_french);

    companion object {
        fun fromString(value: String): CitationVersion {
            return entries.find { it.name.equals(value, ignoreCase = true) }
                ?: throw IllegalArgumentException("Version non valide: $value")
        }
    }
}
