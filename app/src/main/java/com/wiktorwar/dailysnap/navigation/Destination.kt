package com.wiktorwar.dailysnap.navigation

sealed class Destination(val route: String) {
    data object Prompt : Destination("prompt")
    data object Camera : Destination("camera")
    data object Feed : Destination("feed")
    data object Back : Destination("back")
}