package com.wiktorwar.dailysnap.navigation

sealed class Destination(
    val route: String,
    val popUpTo: Destination? = null,
    val inclusive: Boolean = false
) {
    data object Prompt : Destination("prompt")
    data object Camera : Destination("camera")
    data object Feed : Destination("feed", popUpTo = Camera, inclusive = true)
    data object Back : Destination("back")
}