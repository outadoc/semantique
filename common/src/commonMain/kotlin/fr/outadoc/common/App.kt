package fr.outadoc.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.kodein.di.compose.instance

@Composable
fun App() {
    val mainComponent: MainComponent by instance()

    Scaffold(
        topBar = { TopAppBar { Text("Sémantique") } }
    ) { padding ->
        MainScreen(
            modifier = Modifier.padding(padding),
            mainComponent = mainComponent
        )
    }
}
