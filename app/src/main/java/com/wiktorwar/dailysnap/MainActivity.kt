package com.wiktorwar.dailysnap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wiktorwar.dailysnap.feature.prompt.PromptScreen
import com.wiktorwar.dailysnap.feature.prompt.PromptViewModel
import com.wiktorwar.dailysnap.navigation.Destination
import com.wiktorwar.dailysnap.navigation.Navigator
import com.wiktorwar.dailysnap.ui.theme.DailySnapTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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
                            else -> navController.navigate(destination.route)
                        }
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Destination.Prompt.route
                    ) {
                        composable(Destination.Prompt.route) {
                            val state by promptViewModel.viewStates.collectAsState()

                            PromptScreen(
                                state = state,
                                onIntent = promptViewModel::handle,
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .fillMaxSize()
                            )
                        }

                        composable(Destination.Camera.route) {
                            TODO()
                        }
                    }
                }
            }
        }
    }
}