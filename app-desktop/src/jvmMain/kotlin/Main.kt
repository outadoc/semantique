import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import fr.outadoc.cemantix.CemantixServer
import fr.outadoc.semantique.api.SemanticApi
import fr.outadoc.semantique.api.cemantix.CemantixSemanticApi
import fr.outadoc.semantique.storage.DriverFactory
import fr.outadoc.semantique.storage.Storage
import fr.outadoc.semantique.storage.StorageImpl
import fr.outadoc.semantique.storage.db.SemantiqueDatabase
import fr.outadoc.semantique.ui.App
import fr.outadoc.semantique.viewmodels.MainViewModel
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
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
                CemantixServer(client = instance())
            )
        }

        bindSingleton<Storage> {
            StorageImpl(
                database = SemantiqueDatabase(DriverFactory().createDriver()),
                ioDispatcher = Dispatchers.IO
            )
        }

        bindSingleton {
            MainViewModel(
                scope = GlobalScope,
                api = instance(),
                storage = instance()
            )
        }
    }

    Window(
        title = "Sémantique",
        state = WindowState(
            size = DpSize(
                width = 500.dp,
                height = 850.dp
            )
        ),
        onCloseRequest = ::exitApplication
    ) {
        withDI(di) {
            App()
        }
    }
}
