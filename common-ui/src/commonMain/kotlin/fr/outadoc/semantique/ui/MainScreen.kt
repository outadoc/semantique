package fr.outadoc.semantique.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import fr.outadoc.semantique.viewmodels.MainViewModel

@Composable
fun MainScreen(modifier: Modifier = Modifier, mainViewModel: MainViewModel) {
    val state by mainViewModel.state.collectAsState()

    LaunchedEffect(mainViewModel) {
        mainViewModel.onStart()
    }

    MainList(
        modifier = modifier,
        state = state,
        onInputChanged = mainViewModel::onInputChanged,
        onGuessWordClicked = mainViewModel::onGuessWordClicked
    )
}
