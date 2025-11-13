package com.example.citationeapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.citationeapp.R
import com.example.citationeapp.navigation.NavigationHost
import com.example.citationeapp.ui.screens.toast.GlobalToast
import com.example.citationeapp.ui.theme.padding16

@Composable
fun CitationApp(
    appUIState: CitationAppUIState = rememberCitationAppUIState(),
) {
    Box(
        modifier = with(Modifier.background(MaterialTheme.colorScheme.background)) {
        fillMaxSize()
    }) {
        Scaffold(
            contentColor = Color.Transparent,
            containerColor = Color.Transparent,
/*            topBar = {
                CitationTopBar(
                    currentDestination = appUIState.currentDestination,
                    onBack = { appUIState.navController.popBackStack() }
                )
            },
            bottomBar = {
                if (appUIState.shouldShowBottomBar) {
                    CitationBottomBar(
                        destinations = appUIState.topLevelDestinations,
                        currentDestination = appUIState.currentDestination,
                        onNavigateToDestination = appUIState.navController::navigateToTopLevelDestination
                    )
                }
            },*/
        ) { paddingValues ->
//            Image(
//                painter = painterResource(id = R.drawable.ic_pattern_randomized),
//                contentDescription = null,
//                modifier = Modifier.fillMaxSize(),
//                contentScale = ContentScale.Crop
//            )
            GlobalToast()
            Box(modifier = Modifier.padding(paddingValues)) {
                NavigationHost(
                    navController = appUIState.navController
                )
            }
        }
    }
}

/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitationTopBar(
    currentDestination: NavDestination?,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentRoute = Route.getRoute(currentDestination?.route)

    currentRoute?.let { route ->
        if (route.showTopBar) {
            val parentRouteName: Int? = (route as? Route.NestedLevelRoute)?.parent?.displayName
            Box(
                modifier = modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_liquid_cheese),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )
                TopAppBar(
                    title = {
                        route.displayName?.let {
                            TextH3Bold(
                                textId = it,
                                color = white,
                                textAlign = TextAlign.Start
                            )
                        }
                    },
                    navigationIcon = {
                        if (parentRouteName != null) {
                            IconButton(
                                onClick = onBack,
                                modifier = Modifier.padding(horizontal = padding6)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                    contentDescription = null,
                                    tint = white,
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(primary.copy(alpha = 0.6f)),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = white
                    )
                )
            }
        }
    }
}


@Composable
fun CitationBottomBar(
    destinations: List<Route.TopLevelRoute>,
    currentDestination: NavDestination?,
    onNavigateToDestination: (Route.TopLevelRoute) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentRoute = Route.getRoute(currentDestination?.route)
    currentRoute?.let { route ->
        if (route.showBottomBar) {
            NavigationBar(containerColor = white) {
                destinations.forEach { destination ->
                    val interactionSource = remember { MutableInteractionSource() }
                    val pressed by interactionSource.collectIsPressedAsState()
                    val selected =
                        currentDestination.isTopLevelDestinationInHierarchy(destination)

                    val selectedContentColor = if (pressed) black else primary
                    val unselectedContentColor = if (pressed) black else black

                    CompositionLocalProvider {
                        NavigationBarItem(
                            selected = selected,
                            onClick = { onNavigateToDestination(destination) },
                            icon = {
                                Icon(
                                    imageVector = destination.iconId,
                                    // painter = painterResource(id = destination.iconId),
                                    contentDescription = null,
                                    modifier = modifier.size(iconMediumSize)
                                )
                            },
                            label = {
                                TextBottomBar(
                                    textId = destination.titleTextId,
                                    color = Color.Unspecified
                                )
                            },
                            colors = NavigationBarItemDefaults.colors().copy(
                                selectedIconColor = selectedContentColor,
                                selectedTextColor = selectedContentColor,
                                selectedIndicatorColor = Color.Transparent,
                                unselectedIconColor = unselectedContentColor,
                                unselectedTextColor = unselectedContentColor,
                                disabledIconColor = white.copy(alpha = 0.3f),
                                disabledTextColor = white.copy(alpha = 0.3f),
                            ),
                            interactionSource = interactionSource,
                            modifier = modifier.background(
                                if (pressed) primary else Color.Transparent
                            )
                        )
                    }
                }
            }
        }
    }
}*/

@Composable
fun rememberCitationAppUIState(
    navController: NavHostController = rememberNavController(),
): CitationAppUIState {
    return remember(navController) {
        CitationAppUIState(navController)
    }
}

@Stable
class CitationAppUIState(
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination
    val currentRoute: String?
        @Composable get() = currentDestination?.route
}
