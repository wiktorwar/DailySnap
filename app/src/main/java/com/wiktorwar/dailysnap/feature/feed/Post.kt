package com.wiktorwar.dailysnap.feature.feed

import android.net.Uri

data class Post(
    val username: String,
    val mainPhoto: Uri,
    val profileImage: Uri,
    val postedAt: String
)

