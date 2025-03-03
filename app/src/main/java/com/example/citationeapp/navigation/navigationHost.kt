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


//region Liste des routes
sealed class Route(
    val name: String,
    val showBottomBar: Boolean
) {

    // Routes utilisées dans la barre de navigation
    sealed class TopLevelRoute(
        name: String,
        showBottomBar: Boolean,
        @DrawableRes val iconId: Int,
        @StringRes val titleTextId: Int
    ) : Route(name, showBottomBar) {
        data object Home : TopLevelRoute(
            name = "home",
            showBottomBar = true,
            iconId = R.drawable.ic_home,
            titleTextId = R.string.home_bottom_bar,
        )

        data object Profile : TopLevelRoute(
            name = "profile",
            showBottomBar = true,
            iconId = R.drawable.ic_profile,
            titleTextId = R.string.profile_bottom_bar,
        )

        companion object {
            val entries: List<TopLevelRoute> = listOf(Home, Profile)
        }
    }

    sealed class NestedLevelRoute(
        name: String,
        showBottomBar: Boolean,
        val parent: TopLevelRoute
    ) : Route(name, showBottomBar) {
        data object Question : NestedLevelRoute(
            name = "question",
            showBottomBar = true,
            parent = TopLevelRoute.Home,
        )

        data object Answer : NestedLevelRoute(
            name = "answer",
            showBottomBar = true,
            parent = TopLevelRoute.Home,
        )

        // Profil
        data object Settings : NestedLevelRoute(
            name = "settings",
            showBottomBar = true,
            parent = TopLevelRoute.Profile
        )

        data object DesignSystem : NestedLevelRoute(
            name = "designsystem",
            showBottomBar = false,
            parent = TopLevelRoute.Profile
        )
    }

    companion object {
        fun getRoute(route: String?): Route? {
            return when (route) {

                TopLevelRoute.Home.name -> TopLevelRoute.Home
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

private const val profileRoutePattern = "profile_graph"

@Composable
fun NavigationHost(
    appUIState: CitationAppUIState,
    modifier: Modifier = Modifier,
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
                    onBack = { navController.popBackStack() }
                )
            }

            composable(route = Route.NestedLevelRoute.DesignSystem.name) {
                DesignSystem(
                    onBack = { navController.popBackStack() }
                )
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
        Route.TopLevelRoute.Profile -> this.navigateToProfile(topLevelNavOptions)
    }
}

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(Route.TopLevelRoute.Home.name, navOptions)
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









//fun NavController.navigateToStep(step: Step?) {
//    val navOptions = navOptions {
//        popUpTo(route = Route.TopLevelRoute.Challenges.name)
//    }
//
//    when (step) {
//        is Input -> navigateToInput(navOptions)
//        is InputQuestion -> navigateToInputQuestion(navOptions)
//        is InputProgressive -> navigateToInputProgressive(navOptions)
//        is Quiz -> navigateToQuiz(navOptions)
//        is Ready -> navigateToReady(navOptions)
//        is Tutorial -> navigateToTutorial(navOptions)
//        is Exercise -> navigateToExercise(navOptions)
//        is Score -> navigateToScore(navOptions)
//
//        // si plus d'étape, on affiche l'écran score
//        else -> navigateToScore(navOptions)
//    }
//}

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