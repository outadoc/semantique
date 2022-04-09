package fr.outadoc.semantique.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.Dispatchers
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.compose.withDI
import org.kodein.di.instance

class MainActivity : AppCompatActivity() {

    private val di = DI {
        bindSingleton {
            HttpClient(CIO) {
                install(JsonFeature)
            }
        }

        bindSingleton<SemanticApi> {
            CemantixSemanticApi(
                CemantixServer(client = instance())
            )
        }

        bindSingleton<Storage> {
            StorageImpl(
                database = SemantiqueDatabase(
                    DriverFactory(applicationContext).createDriver()
                ),
                ioDispatcher = Dispatchers.IO
            )
        }

        bindSingleton {
            MainViewModel(
                scope = lifecycleScope,
                api = instance(),
                storage = instance()
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            withDI(di) {
                App()
            }
        }
    }
}
