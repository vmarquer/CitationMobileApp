package com.example.citationeapp.navigation

import androidx.annotation.StringRes
import com.example.citationeapp.R

sealed class Route(
    val name : String,
    val showTopBar : Boolean,
    @StringRes val displayName: Int? = null
) {
    data object Portal: Route(
        name = "portal",
        showTopBar = false
    )
    data object Login: Route(
        name = "login",
        showTopBar = false
    )
    data object ForgottenPassword: Route(
        name = "forgotten-password",
        showTopBar = false
    )
    data object Register: Route(
        name = "register",
        showTopBar = false
    )
    data object Validation: Route(
        name = "validation",
        showTopBar = false
    )
    sealed class TopLevelRoute(
        name: String,
        showTopBar: Boolean,
        displayName: Int,
        @StringRes val titleTextId: Int
    ) : Route(name, showTopBar, displayName) {
        data object Home : TopLevelRoute(
            name = "home",
            showTopBar = false,
            titleTextId = R.string.home_title,
            displayName = R.string.home_title,
        )
        data object Play : TopLevelRoute(
            name = "play",
            showTopBar = false,
            titleTextId = R.string.play_title,
            displayName = R.string.play_title,
        )
    }

    sealed class NestedLevelRoute(
        name: String,
        showTopBar : Boolean,
        displayName: Int? = null,
        val parent: TopLevelRoute
    ) : Route(name, showTopBar, displayName) {
        data object Profile : NestedLevelRoute(
            name = "profile",
            showTopBar = true,
            parent = TopLevelRoute.Home,
            displayName = R.string.profile_title,
        )
        data object ModifyPassword : NestedLevelRoute(
            name = "modify-password",
            showTopBar = true,
            parent = TopLevelRoute.Home,
            displayName = R.string.modify_password_title,
        )
        data object DesignSystem : NestedLevelRoute(
            name = "design-system",
            showTopBar = true,
            parent = TopLevelRoute.Home,
            displayName = R.string.design_system_title,
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
                NestedLevelRoute.Profile.name -> NestedLevelRoute.Profile
                NestedLevelRoute.DesignSystem.name -> NestedLevelRoute.DesignSystem
                else -> null
            }
        }
    }
}