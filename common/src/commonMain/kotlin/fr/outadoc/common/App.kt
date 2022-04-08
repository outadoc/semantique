package fr.outadoc.common

import androidx.compose.runtime.Composable
import org.kodein.di.compose.instance

@Composable
fun App() {
    val mainComponent: MainComponent by instance()
    MainScreen(mainComponent = mainComponent)
}
