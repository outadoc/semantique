package fr.outadoc.semantique.ui

import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.outadoc.semantique.api.model.DayStats
import fr.outadoc.semantique.ui.util.AnimatedNullability

@Composable
fun Header(
    modifier: Modifier = Modifier,
    dayStats: DayStats?,
    isLoading: Boolean,
    currentInputWord: String,
    errorMessage: String?,
    onInputChanged: (String) -> Unit,
    onGuessWordClicked: () -> Unit
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            AnimatedNullability(
                dayStats,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) { dayStats ->
                StatsHeader(
                    modifier = Modifier.padding(bottom = 16.dp),
                    dayStats = dayStats
                )
            }

            WordInput(
                currentInputWord = currentInputWord,
                isLoading = isLoading,
                errorMessage = errorMessage,
                onInputChanged = onInputChanged,
                onGuessWordClicked = onGuessWordClicked,
            )
        }
    }
}
