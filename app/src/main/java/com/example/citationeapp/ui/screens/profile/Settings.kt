package com.example.citationeapp.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.components.TextH2Bold
import com.example.citationeapp.ui.theme.components.TextH3
import com.example.citationeapp.ui.theme.grey
import com.example.citationeapp.ui.theme.padding16
import com.example.citationeapp.ui.theme.padding8
import com.example.citationeapp.ui.theme.primary
import com.example.citationeapp.ui.theme.spacing16
import com.example.citationeapp.ui.theme.white
import com.example.citationeapp.viewmodel.CitationVersion
import com.example.citationeapp.viewmodel.VersionViewModel

@Composable
fun Settings(
    modifier: Modifier = Modifier,
    versionViewModel: VersionViewModel,
) {
    val version by versionViewModel.version.collectAsState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding16)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(spacing16)
    ) {

        TextH2Bold(
            text = "Choix de la version",
            color = grey
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TextH3(
                text = "Version Originale",
                modifier = Modifier.padding(start = padding8)
            )
            Checkbox(
                checked = version == CitationVersion.VO,
                onCheckedChange = { isChecked ->
                    if (isChecked) {
                        versionViewModel.toggleVersion(CitationVersion.VO)
                    }
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = primary,
                )
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TextH3(
                text = "Version Française",
                modifier = Modifier.padding(start = padding8)
            )
            Checkbox(
                checked = version == CitationVersion.VF,
                onCheckedChange = { isChecked ->
                    if (isChecked) {
                        versionViewModel.toggleVersion(CitationVersion.VF)
                    }
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = primary,
                )
            )
        }
    }
}