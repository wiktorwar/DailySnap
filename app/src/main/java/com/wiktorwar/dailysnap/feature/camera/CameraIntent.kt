package com.wiktorwar.dailysnap.feature.camera

import android.net.Uri

sealed interface CameraIntent {
    data class PictureTaken(val uri: Uri) : CameraIntent
    data class Post(val comment: String) : CameraIntent
    data object Retake : CameraIntent
}