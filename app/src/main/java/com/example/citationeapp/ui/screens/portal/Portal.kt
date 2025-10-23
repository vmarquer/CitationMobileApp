package com.example.citationeapp.ui.screens.portal

import ButtonPrimary
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citationeapp.R
import com.example.citationeapp.data.remote.repositories.AuthRepositoryInterface
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.lineHeightSmall
import com.example.citationeapp.ui.theme.padding16
import com.example.citationeapp.ui.theme.spacing12
import com.example.citationeapp.ui.theme.spacing24
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun Portal(
    modifier: Modifier = Modifier,
    goLogin: () -> Unit,
    goRegister: () -> Unit,
    goHome: () -> Unit,
    portalViewModel: PortalViewModel = hiltViewModel()
) {

    val isLoading = portalViewModel.isLoading.value

    LaunchedEffect(Unit) {
        portalViewModel.checkAuthentication(goHome)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(padding16)
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = spacing24, alignment = Alignment.CenterVertically
                )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_app),
                    contentDescription = null,
                    modifier = Modifier
                        .height(250.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Fit
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = spacing12, alignment = Alignment.CenterVertically
                    )
                ) {
                    ButtonPrimary(
                        onClick = goLogin,
                        modifier = Modifier.fillMaxWidth(0.7f),
                        textId = R.string.button_login
                    )
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(0.5f),
                        thickness = lineHeightSmall,
                        color = black
                    )
                    ButtonPrimary(
                        onClick = goRegister,
                        modifier = Modifier.fillMaxWidth(0.7f),
                        textId = R.string.button_register
                    )
                }
            }
            Image(
                painter = painterResource(id = R.drawable.popcorn),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .height(150.dp)
                    .padding(padding16),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@HiltViewModel
class PortalViewModel @Inject constructor(
    private val authRepository: AuthRepositoryInterface
) : ViewModel() {

    var isLoading = mutableStateOf(true)

    fun checkAuthentication(goHome: () -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            val isAuthenticated = authRepository.checkAuthentication()
            isLoading.value = false
            if (isAuthenticated) {
                goHome()
            }
        }
    }
}
