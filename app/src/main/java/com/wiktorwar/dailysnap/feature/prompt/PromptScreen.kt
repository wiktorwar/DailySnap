package com.wiktorwar.dailysnap.feature.prompt

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun PromptScreen(
    state: PromptViewState,
    onIntent: (PromptIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val animatedPrompt = animateTypedText(state.userPrompt.orEmpty())

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = animatedPrompt,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!state.remainingTime.isNullOrBlank()) {
                Text(
                    text = "⏱ Remaining time: ${state.remainingTime}",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }

            Button(
                onClick = { onIntent(PromptIntent.TakePicture) },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "Capture moment",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun animateTypedText(fullText: String, speedMs: Long = 30L): String {
    var visibleChars by remember { mutableStateOf(0) }

    LaunchedEffect(fullText) {
        visibleChars = 0
        fullText.indices.forEach { i ->
            delay(speedMs)
            visibleChars = i + 1
        }
    }

    return fullText.take(visibleChars)
}

@Preview(showBackground = true)
@Composable
fun PromptScreenPreview() {
    val previewState = PromptViewState(
        userPrompt = "What made you smile today?",
        remainingTime = "01:30"
    )

    PromptScreen(
        state = previewState,
        onIntent = {}
    )
}