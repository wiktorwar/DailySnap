package com.wiktorwar.dailysnap.feature.camera

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import app.cash.molecule.RecompositionMode
import com.wiktorwar.dailysnap.data.concurrency.DispatcherProvider
import com.wiktorwar.dailysnap.feature.base.BaseViewModel
import com.wiktorwar.dailysnap.navigation.Destination
import com.wiktorwar.dailysnap.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val navigator: Navigator,
    recompositionMode: RecompositionMode,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel<CameraIntent, CameraViewState>(recompositionMode, dispatcherProvider) {

    @Composable
    override fun states(intents: Flow<CameraIntent>): CameraViewState {
        var photoUri by remember { mutableStateOf<Uri?>(null) }
        var comment by remember { mutableStateOf("") }

        LaunchedEffect(Unit) {
            intents.collect { intent ->
                when (intent) {
                    is CameraIntent.PictureTaken -> {
                        photoUri = intent.uri
                    }

                    is CameraIntent.Post -> {
                        // TODO: Update once backend is done
                        // Success:
                        navigator.navigate(Destination.Feed)
                    }

                    is CameraIntent.Retake -> {
                        photoUri = null
                        comment = ""
                    }
                }
            }
        }

        return CameraViewState(imageUri = photoUri, comment = comment)
    }
}