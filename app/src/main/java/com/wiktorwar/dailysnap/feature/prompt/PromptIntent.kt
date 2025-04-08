package com.wiktorwar.dailysnap.feature.prompt

sealed interface PromptIntent {
    data object LoadPrompt : PromptIntent
    data object TakePicture : PromptIntent
}