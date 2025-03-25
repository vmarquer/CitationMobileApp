package com.example.citationeapp.navigation

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocalPlay
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navOptions
import com.example.citationeapp.R
import com.example.citationeapp.designsystem.DesignSystem
import com.example.citationeapp.ui.CitationAppUIState
import com.example.citationeapp.ui.screens.home.Home
import com.example.citationeapp.ui.screens.play.Play
import com.example.citationeapp.ui.screens.portal.ForgottenPassword
import com.example.citationeapp.ui.screens.portal.Login
import com.example.citationeapp.ui.screens.portal.Portal
import com.example.citationeapp.ui.screens.portal.Register
import com.example.citationeapp.ui.screens.portal.Validation
import com.example.citationeapp.ui.screens.profile.ModifyPassword
import com.example.citationeapp.ui.screens.profile.Profile
import com.example.citationeapp.ui.screens.profile.Settings
import com.example.citationeapp.viewmodel.VersionViewModel


//region Liste des routes
sealed class Route(
    val name : String,
    val showTopBar : Boolean,
    val showBottomBar: Boolean,
    // Nom de la route à afficher dans la barre de navigation.
    @StringRes val displayName: Int? = null
) {

    data object Portal : Route(
        name = "portal",
        showTopBar = false,
        showBottomBar = false,
    )

    data object Login : Route(
        name = "login",
        showTopBar = false,
        showBottomBar = false,
    )

    data object ForgottenPassword : Route(
        name = "forgotten-password",
        showTopBar = false,
        showBottomBar = false,
    )

    data object Register : Route(
        name = "register",
        showTopBar = false,
        showBottomBar = false,
    )

    data object Validation : Route(
        name = "validation",
        showTopBar = false,
        showBottomBar = false,
    )

    // Routes utilisées dans la barre de navigation
    sealed class TopLevelRoute(
        name: String,
        showTopBar: Boolean,
        showBottomBar: Boolean,
        displayName: Int,
        val iconId: ImageVector,
        @StringRes val titleTextId: Int
    ) : Route(name, showTopBar, showBottomBar, displayName) {
        data object Home : TopLevelRoute(
            name = "home",
            showTopBar = true,
            showBottomBar = true,
            iconId = Icons.Rounded.Home,
            titleTextId = R.string.home_title,
            displayName = R.string.home_title,
        )

        data object Play : TopLevelRoute(
            name = "play",
            showTopBar = true,
            showBottomBar = true,
            iconId = Icons.Rounded.LocalPlay,
            titleTextId = R.string.play_title,
            displayName = R.string.play_title,
        )

        data object Settings : TopLevelRoute(
            name = "settings",
            showTopBar = true,
            showBottomBar = true,
            iconId = Icons.Rounded.Settings,
            titleTextId = R.string.settings_title,
            displayName = R.string.settings_title,
        )

        companion object {
            val entries: List<TopLevelRoute> = listOf(Home, Play, Settings)
        }
    }

    sealed class NestedLevelRoute(
        name: String,
        showTopBar : Boolean,
        showBottomBar: Boolean,
        displayName: Int? = null,
        val parent: TopLevelRoute
    ) : Route(name, showTopBar, showBottomBar, displayName) {

        // Settings
        data object Profile : NestedLevelRoute(
            name = "profile",
            showTopBar = true,
            showBottomBar = true,
            parent = TopLevelRoute.Settings,
            displayName = R.string.settings_profile_title,
        )

        data object DesignSystem : NestedLevelRoute(
            name = "designsystem",
            showTopBar = true,
            showBottomBar = false,
            parent = TopLevelRoute.Settings,
            displayName = R.string.settings_design_system_title,
        )

        data object ModifyPassword : NestedLevelRoute(
            name = "modifypassword",
            showTopBar = true,
            showBottomBar = false,
            parent = TopLevelRoute.Settings,
            displayName = R.string.settings_modify_password_title,
        )
    }

    companion object {
        fun getRoute(route: String?): Route? {
            return when (route) {

                Portal.name -> Portal
                Login.name -> Login
                ForgottenPassword.name -> ForgottenPassword
                Register.name -> Register
                Validation.name -> Validation

                TopLevelRoute.Home.name -> TopLevelRoute.Home
                TopLevelRoute.Play.name -> TopLevelRoute.Play
                TopLevelRoute.Settings.name -> TopLevelRoute.Settings

                NestedLevelRoute.Profile.name -> NestedLevelRoute.Profile
                NestedLevelRoute.DesignSystem.name -> NestedLevelRoute.DesignSystem
                NestedLevelRoute.ModifyPassword.name -> NestedLevelRoute.ModifyPassword

                else -> null
            }
        }
    }
}
//endregion

private const val profileRoutePattern = "profile_graph"

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    appUIState: CitationAppUIState,
    versionViewModel: VersionViewModel = hiltViewModel(),
    initialRoute: String = Route.Portal.name
) {
    val navController = appUIState.navController

    NavHost(
        navController = navController, startDestination = initialRoute, modifier = modifier
    ) {

        composable(route = Route.Portal.name) {
            Portal(
                goLogin = { navController.navigate(Route.Login.name) },
                goRegister = { navController.navigate(Route.Register.name) },
                goHome = { navController.navigate(Route.TopLevelRoute.Home.name) }
            )
        }

        composable(route = Route.Login.name) {
            Login(
                goRegister = { navController.navigate(Route.Register.name) },
                goHome = { navController.navigate(Route.TopLevelRoute.Home.name) },
                goForgottenPassword = { navController.navigate(Route.ForgottenPassword.name) }
            )
        }

        composable(route = Route.ForgottenPassword.name) {
            ForgottenPassword(
                goLogin = { navController.navigate(Route.Login.name) }
            )
        }

        composable(route = Route.Register.name) {
            Register(
                goValidation = { navController.navigate(Route.Validation.name) },
                goLogin = { navController.navigate(Route.Login.name) }
            )
        }

        composable(route = Route.Validation.name) {
            Validation(
                goLogin = { navController.navigate(Route.Login.name) }
            )
        }

        composable(route = Route.TopLevelRoute.Home.name) {
            Home()
        }

        composable(route = Route.TopLevelRoute.Play.name) {
            Play()
        }

        navigation(
            route = profileRoutePattern,
            startDestination = Route.TopLevelRoute.Settings.name
        ) {
            composable(route = Route.TopLevelRoute.Settings.name) {
                Settings(
                    versionViewModel = versionViewModel,
                    showProfile = { navController.navigate(Route.NestedLevelRoute.Profile.name) },
                    showDesignSystem = { navController.navigate(Route.NestedLevelRoute.DesignSystem.name) }
                )
            }

            composable(route = Route.NestedLevelRoute.Profile.name) {
                Profile(
                    goPortal = { navController.navigate(Route.Portal.name) },
                    goModifyPassword = { navController.navigate(Route.NestedLevelRoute.ModifyPassword.name) }
                )
            }

            composable(route = Route.NestedLevelRoute.DesignSystem.name) {
                DesignSystem()
            }

            composable(route = Route.NestedLevelRoute.ModifyPassword.name) {
                ModifyPassword(
                    goProfile = { navController.navigate(Route.NestedLevelRoute.Profile.name) }
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
        Route.TopLevelRoute.Play -> this.navigateToPlay(topLevelNavOptions)
        Route.TopLevelRoute.Settings -> this.navigateToSettings(topLevelNavOptions)
    }
}

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(Route.TopLevelRoute.Home.name, navOptions)
}

fun NavController.navigateToPlay(navOptions: NavOptions? = null) {
    this.navigate(Route.TopLevelRoute.Play.name, navOptions)
}

fun NavController.navigateToSettings(navOptions: NavOptions? = null) {
    this.navigate(Route.TopLevelRoute.Settings.name, navOptions)
}

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    // le propriétaire de shared view model est l'accueil comme c'est l'écran principal
    val navGraphRoute = Route.Portal.name
    val parentEntry = remember(navController, navGraphRoute) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}
//endregion
//endregion