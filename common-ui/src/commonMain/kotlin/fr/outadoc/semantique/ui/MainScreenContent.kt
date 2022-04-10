package fr.outadoc.semantique.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.outadoc.semantique.viewmodels.MainViewModel
import java.util.*

@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    state: MainViewModel.State,
    onHelpButtonClicked: () -> Unit,
    onLanguageCodeSelected: (String) -> Unit,
    onInputChanged: (String) -> Unit,
    onGuessWordClicked: () -> Unit,
    onDisplayNeighborsToggled: (Boolean) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                actions = {
                    IconButton(onClick = onHelpButtonClicked) {
                        Icon(
                            Icons.Default.Help,
                            contentDescription = "Informations sur le jeu"
                        )
                    }

                    LanguagePicker(
                        currentLanguageCode = state.languageCode,
                        onLanguageCodeSelected = onLanguageCodeSelected,
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
            onInputChanged = onInputChanged,
            onGuessWordClicked = onGuessWordClicked,
            onDisplayNeighborsChanged = onDisplayNeighborsToggled
        )
    }
}
