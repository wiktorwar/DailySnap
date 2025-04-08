package com.wiktorwar.dailysnap.data.usecase

import android.net.Uri
import com.wiktorwar.dailysnap.data.FlowUseCase
import com.wiktorwar.dailysnap.data.concurrency.DispatcherProvider
import com.wiktorwar.dailysnap.feature.feed.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Unit, List<Post>>(dispatcherProvider) {

    override fun execute(params: Unit?): Flow<List<Post>> = flow {
        emit(
            listOf(
                Post(
                    username = "lurymj",
                    mainPhoto = Uri.parse("https://images.unsplash.com/photo-1518779578993-ec3579fee39f"),
                    profileImage = Uri.parse("https://randomuser.me/api/portraits/men/1.jpg"),
                    postedAt = "22h Late"
                ),
                Post(
                    username = "victoriawithrow",
                    mainPhoto = Uri.parse("https://images.unsplash.com/photo-1535930749574-1399327ce78f"),
                    profileImage = Uri.parse("https://randomuser.me/api/portraits/women/2.jpg"),
                    postedAt = "2h ago"
                )
            )
        )
    }
}