package fr.outadoc.semantique.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import fr.outadoc.semantique.viewmodels.MainViewModel
import org.kodein.di.compose.instance

@Composable
fun App() {
    val mainViewModel: MainViewModel by instance()
    MaterialTheme(
        colors = if (isSystemInDarkTheme()) darkColors() else lightColors()
    ) {
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
                    },
                    title = { Text("Sémantique") }
                )
            }
        ) { padding ->
            MainScreen(
                modifier = Modifier.padding(padding),
                mainViewModel = mainViewModel
            )
        }
    }
}
