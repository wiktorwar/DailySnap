package com.wiktorwar.dailysnap

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wiktorwar.dailysnap.feature.camera.CameraScreen
import com.wiktorwar.dailysnap.feature.camera.CameraViewModel
import com.wiktorwar.dailysnap.feature.feed.FeedScreen
import com.wiktorwar.dailysnap.feature.feed.FeedViewModel
import com.wiktorwar.dailysnap.feature.feed.Post
import com.wiktorwar.dailysnap.feature.prompt.PromptScreen
import com.wiktorwar.dailysnap.feature.prompt.PromptViewModel
import com.wiktorwar.dailysnap.navigation.Destination
import com.wiktorwar.dailysnap.navigation.Navigator
import com.wiktorwar.dailysnap.ui.theme.DailySnapTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val promptViewModel: PromptViewModel by viewModels()

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            DailySnapTheme {
                val navController = rememberNavController()

                LaunchedEffect(Unit) {
                    navigator.destinations.collect { destination ->
                        when (destination) {
                            is Destination.Back -> navController.popBackStack()
                            else -> navController.navigate(destination.route) {
                                destination.popUpTo?.let { pop ->
                                    popUpTo(pop.route) {
                                        inclusive = destination.inclusive
                                    }
                                }
                            }
                        }
                    }
                }
                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

                Scaffold(
                    modifier = Modifier
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                        .background(Color.Black),
                    topBar = {
                        val currentBackStack by navController.currentBackStackEntryAsState()
                        val currentRoute = currentBackStack?.destination?.route

                        if (currentRoute == Destination.Feed.route) {
                            val promptState by promptViewModel.viewStates.collectAsState()
                            TopAppBar(
                                title = {
                                    Text(
                                        promptState.userPrompt.orEmpty(),
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.SemiBold
                                        ),
                                        maxLines = 2
                                    )
                                },
                                navigationIcon = {
                                    IconButton(onClick = { navigator.back() }) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Close",
                                            tint = Color.White
                                        )
                                    }
                                },
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = Color.Black,
                                    titleContentColor = Color.White
                                ),
                                scrollBehavior = scrollBehavior
                            )
                        } else if (currentRoute != Destination.Prompt.route) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp, start = 8.dp),
                                contentAlignment = Alignment.TopStart
                            ) {
                                IconButton(
                                    onClick = { navigator.back() },
                                    modifier = Modifier
                                        .size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Close",
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Destination.Prompt.route
                    ) {
                        val modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                        composable(Destination.Prompt.route) {
                            val state by promptViewModel.viewStates.collectAsState()

                            PromptScreen(
                                state = state,
                                onIntent = promptViewModel::handle,
                                modifier = modifier
                            )
                        }

                        composable(Destination.Camera.route) {
                            val viewModel: CameraViewModel by viewModels()

                            CameraScreen(
                                viewModel,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        composable(Destination.Feed.route) {
                            val viewModel: FeedViewModel by viewModels()

                            FeedScreen(viewModel, modifier = Modifier.padding(innerPadding))
                        }
                    }
                }
            }
        }
    }
}