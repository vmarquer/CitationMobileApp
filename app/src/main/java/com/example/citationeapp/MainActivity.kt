package com.example.citationeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.citationeapp.ui.CitationApp
import com.example.citationeapp.ui.theme.CitationAndroidTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CitationAndroidTheme {
                CitationApp()
            }
        }
    }
}

