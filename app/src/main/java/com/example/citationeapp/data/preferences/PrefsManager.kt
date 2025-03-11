package com.example.citationeapp.data.preferences


import android.content.Context
import android.content.SharedPreferences
import com.example.citationeapp.viewmodel.CitationVersion

class PrefsManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "app_prefs"
        private const val KEY_VERSION = "citation_version"
    }

    fun saveVersion(version: CitationVersion) {
        prefs.edit().putString(KEY_VERSION, version.name).apply()
    }

    fun getVersion(): CitationVersion {
        val versionString = prefs.getString(KEY_VERSION, CitationVersion.VF.name)
        return CitationVersion.fromString(versionString!!)
    }
}
