package com.example.citationeapp.ui.screens.toast

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.citationeapp.ui.theme.padding24
import com.example.citationeapp.ui.theme.white
import com.example.citationeapp.utils.ToastManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GlobalToast() {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val message by remember { derivedStateOf { ToastManager.message } }
    val toastColor by remember { derivedStateOf { ToastManager.color } }

    LaunchedEffect(message) {
        if (message != null) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message!!)
                delay(5000)
                ToastManager.clearMessage()
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = padding24),
            snackbar = { snackbarData: SnackbarData ->
                Snackbar(
                    snackbarData = snackbarData,
                    containerColor = toastColor,
                    contentColor = white,
                )
            }
        )
    }
}
