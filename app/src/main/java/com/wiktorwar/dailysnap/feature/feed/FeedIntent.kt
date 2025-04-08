package com.wiktorwar.dailysnap.feature.feed

sealed interface FeedIntent {
    object LoadPosts : FeedIntent
}