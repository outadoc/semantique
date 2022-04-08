import fr.outadoc.common.App
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import fr.outadoc.cemantix.CemantixServer
import fr.outadoc.common.MainComponent
import fr.outadoc.semantique.api.SemanticApi
import fr.outadoc.semantique.api.cemantix.CemantixSemanticApi
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.compose.withDI
import org.kodein.di.instance

@OptIn(DelicateCoroutinesApi::class)
fun main() = application {
    val di = DI {
        bindSingleton {
            HttpClient(CIO) {
                install(JsonFeature)
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.HEADERS
                }
            }
        }

        bindSingleton<SemanticApi> {
            CemantixSemanticApi(
                CemantixServer(instance())
            )
        }

        bindSingleton { MainComponent(GlobalScope, instance()) }
    }

    Window(
        title = "Sémantique",
        state = WindowState(
            size = DpSize(
                width = 400.dp,
                height = 750.dp
            )
        ),
        onCloseRequest = ::exitApplication
    ) {
        MaterialTheme {
            withDI(di) {
                App()
            }
        }
    }
}