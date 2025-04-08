package com.wiktorwar.dailysnap.feature.feed

data class FeedViewState(
    val posts: List<Post> = emptyList()
)