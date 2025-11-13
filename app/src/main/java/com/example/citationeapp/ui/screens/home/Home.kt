package com.example.citationeapp.ui.screens.home

import ButtonPrimary
import FloatingButton
import SingleChoiceSegmentedButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SettingsSuggest
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citationeapp.R
import com.example.citationeapp.data.models.CitationVersion
import com.example.citationeapp.data.remote.repositories.CitationRepositoryInterface
import com.example.citationeapp.data.remote.repositories.VersionRepository
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.components.CheckableRow
import com.example.citationeapp.ui.theme.components.TextBody1Bold
import com.example.citationeapp.ui.theme.components.TextH2
import com.example.citationeapp.ui.theme.components.TextH3
import com.example.citationeapp.ui.theme.components.TextH3Bold
import com.example.citationeapp.ui.theme.grey
import com.example.citationeapp.ui.theme.padding12
import com.example.citationeapp.ui.theme.padding16
import com.example.citationeapp.ui.theme.padding8
import com.example.citationeapp.ui.theme.primary
import com.example.citationeapp.ui.theme.spacing10
import com.example.citationeapp.ui.theme.spacing16
import com.example.citationeapp.ui.theme.spacing2
import com.example.citationeapp.ui.theme.spacing4
import com.example.citationeapp.ui.theme.white
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    modifier: Modifier = Modifier,
    goProfile: () -> Unit,
    goDesignSystem: () -> Unit,
    launchGame: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState(pageCount = { viewModel.modeCount })
    val coroutineScope = rememberCoroutineScope()
    val modes = viewModel.modes
    var showSettings by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val version by viewModel.versionFlow.collectAsState(initial = CitationVersion.VF)

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(spacing16, Alignment.CenterVertically)
        ) {
            TextBody1Bold(textId = R.string.home_mode_selection_title)
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
            ) { page ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            spacing16,
                            Alignment.CenterHorizontally
                        )
                    ) {
                        if (page > 0) {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                imageVector = Icons.Filled.ChevronLeft,
                                contentDescription = null,
                                tint = black
                            )
                        }
                        Image(
                            painter = painterResource(id = modes[page].iconRes),
                            contentDescription = null,
                            modifier = Modifier
                                .height(200.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Fit
                        )
                        if (page < 2) {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                imageVector = Icons.Filled.ChevronRight,
                                contentDescription = null,
                                tint = black
                            )
                        }
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                modes.forEachIndexed { index, _ ->
                    val isSelected = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .padding(padding12)
                            .size(if (isSelected) padding16 else padding8)
                            .background(
                                color = if (isSelected) primary
                                else grey,
                                shape = RoundedCornerShape(50)
                            )
                    )
                }
            }
            ButtonPrimary(
                modifier = Modifier,
                textId = R.string.home_launch_game,
                onClick = launchGame
            )
        }
        FloatingButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(padding16),
            icon = Icons.Default.Settings,
            onClick = { showSettings = true }
        )
        FloatingButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(padding16),
            icon = Icons.Default.Person,
            onClick = goProfile
        )
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

    if (showSettings) {
        ModalBottomSheet(
            onDismissRequest = { showSettings = false },
            containerColor = white,
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(spacing4)
            ) {
                TextH3Bold(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textId = R.string.settings_title
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = {
                                showSettings = false
                                goDesignSystem()
                            }
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextH3(
                        textId = R.string.design_system_title,
                        color = grey
                    )
                    Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = Icons.Filled.ChevronRight,
                        contentDescription = null,
                        tint = black
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextH3(
                        textId = R.string.settings_version_label,
                        color = grey
                    )
                    SingleChoiceSegmentedButton(
                        options = CitationVersion.entries,
                        selectedOption = version,
                        getText = { stringResource(id = it.displayNameRes) },
                        onSelectionChanged = { selectedVersion ->
                            viewModel.toggleVersion(selectedVersion)
                        }
                    )
                }
            }
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        viewModel.onModeChanged(pagerState.currentPage)
    }
}

enum class GameMode(val iconRes: Int) {
    ALL(R.drawable.ic_all),
    FILMS(R.drawable.ic_films),
    SERIES(R.drawable.ic_series)
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val citationRepository: CitationRepositoryInterface,
    private val versionRepository: VersionRepository
) : ViewModel() {
    val modes = GameMode.entries
    private val _currentMode = mutableIntStateOf(0)
    val modeCount: Int get() = modes.size
    var version by mutableStateOf(CitationVersion.VF)
        private set
    val versionFlow = versionRepository.versionFlow
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
    fun onModeChanged(index: Int) {
        _currentMode.intValue = index
        citationRepository.setGameMode(modes[index])
    }
}
