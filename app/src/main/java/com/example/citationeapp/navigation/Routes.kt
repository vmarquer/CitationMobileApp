package com.example.citationeapp.navigation

sealed class Route(val name: String) {
    object Portal : Route("portal")
    object Login : Route("login")
    object ForgottenPassword : Route("forgotten-password")
    object Register : Route("register")
    object Validation : Route("validation")
    object Home : Route("home")
    object Play : Route("play")
    object Settings : Route("settings")
    object Profile : Route("profile")
    object ModifyPassword : Route("modify-password")

    //Design system
    object DesignSystem : Route("design-system")
}