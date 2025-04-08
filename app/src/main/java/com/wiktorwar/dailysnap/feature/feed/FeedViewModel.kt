package com.wiktorwar.dailysnap.feature.feed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.wiktorwar.dailysnap.data.concurrency.DispatcherProvider
import com.wiktorwar.dailysnap.feature.base.BaseViewModel
import com.wiktorwar.dailysnap.feature.feed.FeedIntent.LoadPosts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import app.cash.molecule.RecompositionMode
import com.wiktorwar.dailysnap.data.usecase.GetPostsUseCase

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getFriendPostsUseCase: GetPostsUseCase,
    recompositionMode: RecompositionMode,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel<FeedIntent, FeedViewState>(recompositionMode, dispatcherProvider) {

    @Composable
    override fun states(intents: Flow<FeedIntent>): FeedViewState {
        var viewState by remember {
            mutableStateOf(
                FeedViewState(
                    posts = emptyList()
                )
            )
        }

        LaunchedEffect(Unit) {
            intents.collect { intent ->
                when (intent) {
                    LoadPosts -> {
                        viewModelScope.launch {
                            getFriendPostsUseCase(Unit).collect { posts ->
                                viewState = viewState.copy(posts = posts)
                            }
                        }
                    }
                }
            }
        }

        // Initial auto-trigger
        LaunchedEffect(Unit) {
            handle(LoadPosts)
        }

        return viewState
    }
}