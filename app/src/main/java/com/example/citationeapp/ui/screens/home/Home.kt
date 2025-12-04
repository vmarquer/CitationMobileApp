package com.example.citationeapp.ui.screens.home

import ButtonPrimary
import FloatingButton
import SingleChoiceSegmentedButton
import androidx.activity.compose.BackHandler
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
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citationeapp.R
import com.example.citationeapp.data.models.CitationVersion
import com.example.citationeapp.data.remote.repositories.CitationRepositoryInterface
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.components.TextBody1Bold
import com.example.citationeapp.ui.theme.components.TextBody2Regular
import com.example.citationeapp.ui.theme.components.TextH3Bold
import com.example.citationeapp.ui.theme.gameModePagerHeight
import com.example.citationeapp.ui.theme.grey
import com.example.citationeapp.ui.theme.iconLargeSize
import com.example.citationeapp.ui.theme.imageLargeSize
import com.example.citationeapp.ui.theme.imageMediumSize
import com.example.citationeapp.ui.theme.padding12
import com.example.citationeapp.ui.theme.padding16
import com.example.citationeapp.ui.theme.padding24
import com.example.citationeapp.ui.theme.padding8
import com.example.citationeapp.ui.theme.primary
import com.example.citationeapp.ui.theme.spacing16
import com.example.citationeapp.ui.theme.spacing32
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
    BackHandler(enabled = true) {}

    val pagerState = rememberPagerState(pageCount = { viewModel.modeCount })
    val coroutineScope = rememberCoroutineScope()
    val modes = viewModel.modes
    var showSettings by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val version by viewModel.versionFlow.collectAsState(initial = CitationVersion.VF)
    val quizSize by viewModel.quizSizeFlow.collectAsState(initial = 5)

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
                    .height(gameModePagerHeight),
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
                                modifier = Modifier.size(iconLargeSize),
                                imageVector = Icons.Filled.ChevronLeft,
                                contentDescription = null,
                                tint = black
                            )
                        }
                        Image(
                            painter = painterResource(id = modes[page].iconRes),
                            contentDescription = null,
                            modifier = Modifier
                                .height(imageLargeSize)
                                .clip(CircleShape),
                            contentScale = ContentScale.Fit
                        )
                        if (page < 2) {
                            Icon(
                                modifier = Modifier.size(iconLargeSize),
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
                .height(imageMediumSize)
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
                    .padding(padding24)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(spacing32)
            ) {
                TextH3Bold(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textId = R.string.settings_title
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(spacing4)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextBody2Regular(
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextBody2Regular(
                            textId = R.string.settings_quiz_size_label,
                            color = grey
                        )
                        SingleChoiceSegmentedButton(
                            options = viewModel.quizSizeEntries.toList(),
                            selectedOption = quizSize,
                            getText = { it.toString() },
                            onSelectionChanged = { size ->
                                viewModel.updateQuizSize(size)
                            }
                        )
                    }
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
                        TextBody2Regular(
                            textId = R.string.design_system_title,
                            color = grey
                        )
                        Icon(
                            modifier = Modifier.size(iconLargeSize),
                            imageVector = Icons.Filled.ChevronRight,
                            contentDescription = null,
                            tint = black
                        )
                    }
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
    private val citationRepository: CitationRepositoryInterface
) : ViewModel() {
    val modes = GameMode.entries
    private val _currentMode = mutableIntStateOf(0)
    val modeCount: Int get() = modes.size
    val versionFlow = citationRepository.version
    var quizSize by mutableStateOf(5)
        private set
    val quizSizeFlow = citationRepository.quizSize
    val quizSizeEntries = intArrayOf(5, 10, 20)

    init {
        viewModelScope.launch {
            citationRepository.quizSize.collect { size ->
                quizSize = size
            }
        }
    }
    fun toggleVersion(citationVersion: CitationVersion) {
        viewModelScope.launch {
            citationRepository.updateVersion(citationVersion)
        }
    }

    fun updateQuizSize(size: Int) {
        viewModelScope.launch {
            citationRepository.updateQuizSize(size)
        }
    }

    fun onModeChanged(index: Int) {
        _currentMode.intValue = index
        citationRepository.setGameMode(modes[index])
    }
}
