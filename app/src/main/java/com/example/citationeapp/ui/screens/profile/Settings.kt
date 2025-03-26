package com.example.citationeapp.ui.screens.profile

import ProfileButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DesignServices
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citationeapp.R
import com.example.citationeapp.data.models.CitationVersion
import com.example.citationeapp.data.remote.repositories.VersionRepository
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.components.CheckableRow
import com.example.citationeapp.ui.theme.components.TextH3Bold
import com.example.citationeapp.ui.theme.grey
import com.example.citationeapp.ui.theme.padding12
import com.example.citationeapp.ui.theme.spacing2
import com.example.citationeapp.ui.theme.spacing8
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun Settings(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
    showProfile: () -> Unit,
    showDesignSystem: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding12)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(spacing8)
    ) {
        ProfileButton(
            modifier = Modifier.fillMaxWidth(),
            textId = R.string.profile_title,
            onClick = showProfile,
            iconId = Icons.Filled.Person,
            colorIcon = black
        )

        ProfileButton(
            modifier = Modifier.fillMaxWidth(),
            textId = R.string.design_system_title,
            onClick = showDesignSystem,
            iconId = Icons.Filled.DesignServices,
            colorIcon = black
        )

        Column(
            modifier = modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(spacing2)
        ) {
            TextH3Bold(
                textId = R.string.settings_version_label,
                color = grey
            )
            CheckableRow(
                textId = CitationVersion.VO.displayNameRes,
                isChecked = viewModel.version == CitationVersion.VO,
                onCheckedChange = { isChecked ->
                    if (isChecked) {
                        viewModel.toggleVersion(CitationVersion.VO)
                    }
                }
            )
            CheckableRow(
                textId = CitationVersion.VF.displayNameRes,
                isChecked = viewModel.version == CitationVersion.VF,
                onCheckedChange = { isChecked ->
                    if (isChecked) {
                        viewModel.toggleVersion(CitationVersion.VF)
                    }
                }
            )
        }
    }
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val versionRepository: VersionRepository
) : ViewModel() {

    var version: CitationVersion = CitationVersion.VF
        private set

    init {
        viewModelScope.launch {
            versionRepository.versionFlow.collect { newVersion ->
                version = newVersion
            }
        }
    }

    fun toggleVersion(citationVersion: CitationVersion) {
        viewModelScope.launch {
            versionRepository.saveVersion(citationVersion)
        }
    }
}
