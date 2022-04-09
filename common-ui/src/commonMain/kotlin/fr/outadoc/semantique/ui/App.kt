package fr.outadoc.semantique.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.outadoc.semantique.viewmodels.MainViewModel
import org.kodein.di.compose.instance

@Composable
fun App() {
    val mainViewModel: MainViewModel by instance()
    MaterialTheme(
        colors = if (isSystemInDarkTheme()) darkColors() else lightColors()
    ) {
        Scaffold(
            topBar = { TopAppBar { Text("Sémantique") } }
        ) { padding ->
            MainScreen(
                modifier = Modifier.padding(padding),
                mainViewModel = mainViewModel
            )
        }
    }
}
