package fr.outadoc.semantique.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.outadoc.semantique.ui.strings.stringResource
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
                            contentDescription = stringResource(MR.strings.help_cd)
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
                title = {
                    Text(text = stringResource(MR.strings.app_title))
                }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainList(
                modifier = modifier
                    .widthIn(max = 600.dp)
                    .padding(padding),
                state = state,
                onInputChanged = onInputChanged,
                onGuessWordClicked = onGuessWordClicked,
                onDisplayNeighborsChanged = onDisplayNeighborsToggled
            )
        }
    }
}
