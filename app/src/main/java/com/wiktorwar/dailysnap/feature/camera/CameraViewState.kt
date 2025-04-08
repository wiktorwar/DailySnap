package com.wiktorwar.dailysnap.feature.camera

import android.net.Uri

data class CameraViewState(
    val imageUri: Uri? = null,
    val comment: String = ""
)