package fr.outadoc.semantique.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import fr.outadoc.semantique.viewmodels.MainViewModel
import org.kodein.di.compose.instance
import java.util.*

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val mainViewModel: MainViewModel by instance()
    val state by mainViewModel.state.collectAsState()

    LaunchedEffect(mainViewModel) {
        mainViewModel.onStart()
    }

    val uriHandler = LocalUriHandler.current
    Scaffold(
        topBar = {
            TopAppBar(
                actions = {
                    IconButton(
                        onClick = { uriHandler.openUri("https://cemantix.herokuapp.com/") }
                    ) {
                        Icon(
                            Icons.Default.Help,
                            contentDescription = "Informations sur le jeu"
                        )
                    }

                    LanguagePicker(
                        currentLanguageCode = state.languageCode,
                        onLanguageCodeSelected = mainViewModel::switchLanguage,
                        availableLanguageCodes = listOf(
                            Locale.FRENCH.language,
                            Locale.ENGLISH.language
                        )
                    )
                },
                title = { Text("Sémantique") }
            )
        }
    ) { padding ->
        MainList(
            modifier = modifier.padding(padding),
            state = state,
            onInputChanged = mainViewModel::onInputChanged,
            onGuessWordClicked = mainViewModel::onGuessWordClicked,
            onDisplayNeighborsChanged = mainViewModel::onDisplayNeighborsToggled
        )
    }
}
