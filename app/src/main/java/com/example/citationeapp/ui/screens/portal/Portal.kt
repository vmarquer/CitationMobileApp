package com.example.citationeapp.ui.screens.portal

import ButtonPrimary
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.citationeapp.R
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.lineHeightSmall
import com.example.citationeapp.ui.theme.padding16
import com.example.citationeapp.ui.theme.spacing12
import com.example.citationeapp.ui.theme.spacing24

@Composable
fun Portal(
    modifier: Modifier = Modifier,
    goLogin: () -> Unit,
    goRegister: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(padding16)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = spacing24, alignment = Alignment.CenterVertically
        )
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_app),
            contentDescription = null,
            modifier = Modifier.height(250.dp),
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
                modifier = Modifier
                    .fillMaxWidth(0.7f),
                textId = R.string.portal_login
            )

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(0.5f),
                thickness = lineHeightSmall,
                color = black
            )

            ButtonPrimary(
                onClick = goRegister,
                modifier = Modifier
                    .fillMaxWidth(0.7f),
                textId = R.string.portal_sign_up
            )
        }
    }
}
