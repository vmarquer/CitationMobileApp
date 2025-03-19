package com.example.citationeapp.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.citationeapp.data.preferences.UserPreferences
import com.example.citationeapp.ui.CitationApp
import com.example.citationeapp.ui.theme.CitationAndroidTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CitationAndroidTheme {
                CitationApp(userPreferences = userPreferences)
            }
        }
    }
}