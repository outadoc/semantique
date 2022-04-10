package fr.outadoc.semantique.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import fr.outadoc.semantique.viewmodels.MainViewModel
import kotlinx.coroutines.flow.collect
import org.kodein.di.compose.instance

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val viewModel: MainViewModel by instance()
    val state by viewModel.state.collectAsState()

    val uriHandler = LocalUriHandler.current

    LaunchedEffect(viewModel) {
        viewModel.onStart()
    }

    LaunchedEffect(viewModel.events) {
        viewModel.events.collect { event ->
            when (event) {
                is MainViewModel.Event.OpenUri -> {
                    uriHandler.openUri(event.uri)
                }
            }
        }
    }

    MainScreenContent(
        modifier = modifier,
        state = state,
        onHelpButtonClicked = viewModel::onHelpButtonClicked,
        switchLanguage = viewModel::switchLanguage,
        onInputChanged = viewModel::onInputChanged,
        onGuessWordClicked = viewModel::onGuessWordClicked,
        onDisplayNeighborsToggled = viewModel::onDisplayNeighborsToggled
    )
}
