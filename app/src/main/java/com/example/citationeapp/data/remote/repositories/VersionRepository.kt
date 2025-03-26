package com.example.citationeapp.data.remote.repositories

import com.example.citationeapp.data.models.CitationVersion
import com.example.citationeapp.data.preferences.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface VersionRepositoryInterface {
    suspend fun saveVersion(version: CitationVersion)
}

class VersionRepository @Inject constructor(
    private val userPreferences: UserPreferences
) : VersionRepositoryInterface {

    val versionFlow: Flow<CitationVersion> = userPreferences.version.map { versionString ->
        CitationVersion.fromString(versionString)
    }

    override suspend fun saveVersion(version: CitationVersion) {
        userPreferences.saveVersion(version)
    }
}