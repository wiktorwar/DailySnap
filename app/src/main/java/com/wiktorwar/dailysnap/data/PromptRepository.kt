package com.wiktorwar.dailysnap.data

import javax.inject.Inject

class PromptRepository @Inject constructor() {

    private val mockPrompts = listOf(
        "What made you smile today?",
        "What did you avoid today?",
        "What was your favorite detail from today’s routine?",
        "What drained your energy today?",
        "What moment would you like to relive from today?",
        "What did you eat today?",
        "What frustrated you today?",
        "What felt effortless today?",
        "Who did you connect with today?",
        "What did you spend most of your time on today?",
        "What would you do differently if you could redo today?",
        "What did you learn today (if anything)?",
        "What did your surroundings look like at 3 PM?",
        "What surprised you in a good way today?",
        "What felt heavy today?",
        "What did you do for yourself today?",
        "What conversation left you thinking?",
        "What was something you noticed today that others might have missed?"
    )

    fun getRandomPrompt() = Prompt(mockPrompts.random())
}