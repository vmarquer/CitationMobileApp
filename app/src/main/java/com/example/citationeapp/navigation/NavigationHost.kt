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
import com.example.citationeapp.ui.screens.settings.ModifyPassword
import com.example.citationeapp.ui.screens.settings.Profile

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
                goHome = { navController.navigate(Route.TopLevelRoute.Home.name) }
            )
        }

        composable(Route.Login.name) {
            Login(
                goRegister = { navController.navigate(Route.Register.name) },
                goHome = { navController.navigate(Route.TopLevelRoute.Home.name) },
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

        composable(Route.TopLevelRoute.Home.name) {
            Home(
                goProfile = { navController.navigate(Route.NestedLevelRoute.Profile.name) },
                goDesignSystem = { navController.navigate(Route.NestedLevelRoute.DesignSystem.name) },
                launchGame = { navController.navigate(Route.TopLevelRoute.Play.name) }
            )
    }

        composable(route = Route.TopLevelRoute.Play.name) {
            Play(
                goHome = { navController.navigate(Route.TopLevelRoute.Home.name) },
                onForceLogin = { navController.navigate(Route.Portal.name) }
            )
        }

        composable(route = Route.NestedLevelRoute.Profile.name) {
            Profile(
                goPortal = { navController.navigate(Route.Portal.name) },
                goModifyPassword = { navController.navigate(Route.NestedLevelRoute.ModifyPassword.name) },
            )
        }

        composable(Route.NestedLevelRoute.ModifyPassword.name) {
            ModifyPassword(
                goProfile = { navController.popBackStack(Route.NestedLevelRoute.Profile.name, inclusive = false) },
                onForceLogin = { navController.navigate(Route.Portal.name) }
            )
        }

        composable(Route.NestedLevelRoute.DesignSystem.name) {
            DesignSystem()
        }
    }
}
