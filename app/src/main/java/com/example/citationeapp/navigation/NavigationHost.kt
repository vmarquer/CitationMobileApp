package com.example.citationeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.citationeapp.designsystem.DesignSystem
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

@Composable
fun NavigationHost(
    navController: NavHostController,
    startDestination: String = Route.Portal.name
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Route.Portal.name) {
            Portal(
                goLogin = { navController.navigate(Route.Login.name) },
                goRegister = { navController.navigate(Route.Register.name) },
                goHome = { navController.navigate(Route.Home.name) }
            )
        }

        composable(Route.Login.name) {
            Login(
                goRegister = { navController.navigate(Route.Register.name) },
                goHome = { navController.navigate(Route.Home.name) },
                goForgottenPassword = { navController.navigate(Route.ForgottenPassword.name) }
            )
        }

        composable(Route.ForgottenPassword.name) {
            ForgottenPassword(
                goLogin = { navController.navigate(Route.Login.name) }
            )
        }

        composable(Route.Register.name) {
            Register(
                goValidation = { navController.navigate(Route.Validation.name) },
                goLogin = { navController.navigate(Route.Login.name) }
            )
        }

        composable(Route.Validation.name) {
            Validation(
                goLogin = { navController.navigate(Route.Login.name) }
            )
        }

        composable(Route.Home.name) {
            Home(
                goSettings = { navController.navigate(Route.Settings.name) },
                launchGame = { navController.navigate(Route.Play.name) }
            )
    }

        composable(route = Route.Play.name) {
            Play(
                goHome = { navController.navigate(Route.Home.name) },
                onForceLogin = { navController.navigate(Route.Portal.name) }
            )
        }

        composable(route = Route.Settings.name) {
            Settings(
                showProfile = { navController.navigate(Route.Profile.name) },
                showDesignSystem = { navController.navigate(Route.DesignSystem.name) }
            )
        }

        composable(route = Route.Profile.name) {
            Profile(
                goPortal = { navController.navigate(Route.Portal.name) },
                goModifyPassword = { navController.navigate(Route.ModifyPassword.name) },
            )
        }

        composable(Route.ModifyPassword.name) {
            ModifyPassword(
                goProfile = { navController.popBackStack(Route.Profile.name, inclusive = false) },
                onForceLogin = { navController.navigate(Route.Portal.name) }
            )
        }

        composable(Route.DesignSystem.name) {
            DesignSystem()
        }
    }
}
