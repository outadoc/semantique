package fr.outadoc.semantique.storage

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import fr.outadoc.semantique.storage.db.SemantiqueDatabase

actual class DriverFactory(private val context: Context) {

    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(SemantiqueDatabase.Schema, context, "semantique.db")
    }
}
