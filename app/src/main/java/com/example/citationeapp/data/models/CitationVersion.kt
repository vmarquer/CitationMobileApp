package com.example.citationeapp.data.models

import androidx.annotation.StringRes
import com.example.citationeapp.R

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