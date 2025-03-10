package com.example.citationeapp.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.example.citationeapp.R
import com.example.citationeapp.ui.CitationAppUIState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.citationeapp.ui.screens.home.Home
import com.example.citationeapp.ui.screens.profile.Profile
import com.example.citationeapp.ui.screens.profile.Settings
import com.example.citationeapp.designsystem.DesignSystem
import com.example.citationeapp.ui.screens.play.Answer
import com.example.citationeapp.viewmodel.CitationViewModel
import com.example.citationeapp.ui.screens.play.Play
import com.example.citationeapp.ui.screens.play.Question
import com.example.citationeapp.viewmodel.VersionViewModel


//region Liste des routes
sealed class Route(
    val name : String,
    val showTopBar : Boolean,
    val showBottomBar: Boolean,
    // Nom de la route à afficher dans la barre de navigation.
    @StringRes val displayName: Int? = null
) {

    // Routes utilisées dans la barre de navigation
    sealed class TopLevelRoute(
        name: String,
        showTopBar: Boolean,
        showBottomBar: Boolean,
        displayName: Int,
        @DrawableRes val iconId: Int,
        @StringRes val titleTextId: Int
    ) : Route(name, showTopBar, showBottomBar, displayName) {
        data object Home : TopLevelRoute(
            name = "home",
            showTopBar = true,
            showBottomBar = true,
            iconId = R.drawable.ic_home,
            titleTextId = R.string.home_title,
            displayName = R.string.home_title,
        )

        data object Play : TopLevelRoute(
            name = "play",
            showTopBar = true,
            showBottomBar = true,
            iconId = R.drawable.ic_play,
            titleTextId = R.string.play_title,
            displayName = R.string.play_title,
        )

        data object Profile : TopLevelRoute(
            name = "profile",
            showTopBar = true,
            showBottomBar = true,
            iconId = R.drawable.ic_profile,
            titleTextId = R.string.profile_title,
            displayName = R.string.profile_title,
        )

        companion object {
            val entries: List<TopLevelRoute> = listOf(Home, Play, Profile)
        }
    }

    sealed class NestedLevelRoute(
        name: String,
        showTopBar : Boolean,
        showBottomBar: Boolean,
        displayName: Int? = null,
        val parent: TopLevelRoute
    ) : Route(name, showTopBar, showBottomBar, displayName) {
        data object Question : NestedLevelRoute(
            name = "question",
            showTopBar = false,
            showBottomBar = true,
            parent = TopLevelRoute.Play,
            displayName = R.string.play_title,
        )

        data object Answer : NestedLevelRoute(
            name = "answer",
            showTopBar = false,
            showBottomBar = true,
            parent = TopLevelRoute.Play,
            displayName = R.string.play_title,
        )

        // Profil
        data object Settings : NestedLevelRoute(
            name = "settings",
            showTopBar = true,
            showBottomBar = true,
            parent = TopLevelRoute.Profile,
            displayName = R.string.settings_title,
        )

        data object DesignSystem : NestedLevelRoute(
            name = "designsystem",
            showTopBar = true,
            showBottomBar = false,
            parent = TopLevelRoute.Profile,
            displayName = R.string.design_system_title,
        )
    }

    companion object {
        fun getRoute(route: String?): Route? {
            return when (route) {

                TopLevelRoute.Home.name -> TopLevelRoute.Home
                TopLevelRoute.Play.name -> TopLevelRoute.Play
                TopLevelRoute.Profile.name -> TopLevelRoute.Profile

                NestedLevelRoute.Question.name -> NestedLevelRoute.Question
                NestedLevelRoute.Answer.name -> NestedLevelRoute.Answer

                NestedLevelRoute.Settings.name -> NestedLevelRoute.Settings
                NestedLevelRoute.DesignSystem.name -> NestedLevelRoute.DesignSystem

                else -> null
            }
        }
    }
}
//endregion

private const val playRoutePattern = "play_graph"
private const val profileRoutePattern = "profile_graph"

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    appUIState: CitationAppUIState,
    citationViewModel: CitationViewModel = hiltViewModel(),
    versionViewModel: VersionViewModel  = hiltViewModel(),
    startDestination: String = Route.TopLevelRoute.Home.name
) {
    val navController = appUIState.navController

    NavHost(
        navController = navController, startDestination = startDestination, modifier = modifier
    ) {
        composable(route = Route.TopLevelRoute.Home.name) {
            Home()
        }
        navigation(
            route = playRoutePattern,
            startDestination = Route.TopLevelRoute.Play.name
        ) {
            composable(route = Route.TopLevelRoute.Play.name) {
                Play (
                    citationViewModel = citationViewModel,
                    versionViewModel = versionViewModel,
                    launchGame = { navController.navigate(Route.NestedLevelRoute.Question.name) }
                )
            }

            composable(route = Route.NestedLevelRoute.Question.name) {
                Question(
                    citationViewModel = citationViewModel,
                    versionViewModel = versionViewModel,
                    goToAnswer = { navController.navigate(Route.NestedLevelRoute.Answer.name) },
                    goHome = { navController.navigate(Route.TopLevelRoute.Home.name) }
                )
            }

            composable(route = Route.NestedLevelRoute.Answer.name) {
                Answer(
                    citationViewModel = citationViewModel,
                    versionViewModel = versionViewModel,
                    goToNewCitation = { navController.navigate(Route.NestedLevelRoute.Question.name) },
                    goHome = { navController.navigate(Route.TopLevelRoute.Home.name) }
                )
            }
        }

        navigation(
            route = profileRoutePattern,
            startDestination = Route.TopLevelRoute.Profile.name
        ) {
            composable(route = Route.TopLevelRoute.Profile.name) {
                Profile(
                    showProfileSettings = { navController.navigate(Route.NestedLevelRoute.Settings.name) },
                    showDesignSystem = { navController.navigate(Route.NestedLevelRoute.DesignSystem.name) }
                )
            }

            composable(route = Route.NestedLevelRoute.Settings.name) {
                Settings(
                    versionViewModel = versionViewModel,
                )
            }

            composable(route = Route.NestedLevelRoute.DesignSystem.name) {
                DesignSystem()
            }
        }
    }
}
//endregion

//region Extensions
fun NavController.navigateToTopLevelDestination(topLevelDestination: Route.TopLevelRoute) {
    val topLevelNavOptions = navOptions {
        popUpTo(Route.TopLevelRoute.Home.name) {
            saveState = false
        }
        launchSingleTop = true
        restoreState = false
    }

    when (topLevelDestination) {
        Route.TopLevelRoute.Home -> this.navigateToHome(topLevelNavOptions)
        Route.TopLevelRoute.Play -> this.navigateToPlay(topLevelNavOptions)
        Route.TopLevelRoute.Profile -> this.navigateToProfile(topLevelNavOptions)
    }
}

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(Route.TopLevelRoute.Home.name, navOptions)
}

fun NavController.navigateToPlay(navOptions: NavOptions? = null) {
    this.navigate(Route.TopLevelRoute.Play.name, navOptions)
}

fun NavController.navigateToProfile(navOptions: NavOptions? = null) {
    this.navigate(Route.TopLevelRoute.Profile.name, navOptions)
}

fun NavController.navigateToQuestion(navOptions: NavOptions? = null) {
    this.navigate(Route.NestedLevelRoute.Question.name, navOptions)
}

fun NavController.navigateToAnswer(navOptions: NavOptions? = null) {
    this.navigate(Route.NestedLevelRoute.Answer.name, navOptions)
}

fun NavController.navigateToSettings(navOptions: NavOptions? = null) {
    this.navigate(Route.NestedLevelRoute.Settings.name, navOptions)
}

fun NavController.navigateToDesignSystem(navOptions: NavOptions? = null) {
    this.navigate(Route.NestedLevelRoute.DesignSystem.name, navOptions)
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    // le propriétaire de shared view model est l'accueil comme c'est l'écran principal
    val navGraphRoute = Route.TopLevelRoute.Home.name
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}
//endregion
//endregion