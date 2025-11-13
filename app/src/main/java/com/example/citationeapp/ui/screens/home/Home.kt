package com.example.citationeapp.ui.screens.home

import ButtonPrimary
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.citationeapp.R
import com.example.citationeapp.data.remote.repositories.CitationRepositoryInterface
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.components.TextBody1Bold
import com.example.citationeapp.ui.theme.grey
import com.example.citationeapp.ui.theme.padding12
import com.example.citationeapp.ui.theme.padding16
import com.example.citationeapp.ui.theme.padding8
import com.example.citationeapp.ui.theme.primary
import com.example.citationeapp.ui.theme.spacing16
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@Composable
fun Home(
    modifier: Modifier = Modifier,
    goSettings: () -> Unit,
    launchGame: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState(pageCount = { homeViewModel.modeCount })
    val coroutineScope = rememberCoroutineScope()
    val modes = homeViewModel.modes

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
            ButtonPrimary(
                modifier = Modifier,
                textId = R.string.settings_title,
                onClick = goSettings
            )
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

    LaunchedEffect(pagerState.currentPage) {
        homeViewModel.onModeChanged(pagerState.currentPage)
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
) : ViewModel() {
    val modes = GameMode.entries
    private val _currentMode = mutableIntStateOf(0)
    val modeCount: Int get() = modes.size
    fun onModeChanged(index: Int) {
        _currentMode.intValue = index
        citationRepository.setGameMode(modes[index])
    }
}
