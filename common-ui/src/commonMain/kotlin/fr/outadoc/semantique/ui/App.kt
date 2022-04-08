package fr.outadoc.semantique.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.outadoc.semantique.viewmodels.MainViewModel
import org.kodein.di.compose.instance

@Composable
fun App() {
    val mainViewModel: MainViewModel by instance()
    MaterialTheme {
        Scaffold(topBar = { TopAppBar { Text("Sémantique") } }) { padding ->
            MainScreen(
                modifier = Modifier.padding(padding),
                mainViewModel = mainViewModel
            )
        }
    }
}
