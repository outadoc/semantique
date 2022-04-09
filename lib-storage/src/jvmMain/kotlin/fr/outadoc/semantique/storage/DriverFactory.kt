package fr.outadoc.semantique.storage

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import fr.outadoc.semantique.storage.db.SemantiqueDatabase
import net.harawata.appdirs.AppDirsFactory
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.div

actual class DriverFactory {

    actual fun createDriver(): SqlDriver {
        val appDir: Path =
            AppDirsFactory.getInstance()
                .getUserDataDir("Semantique", null, "outadoc")
                .let { path -> Path(path) }

        appDir.createDirectories()
        val dbPath = appDir / "semantique.db"

        return JdbcSqliteDriver("jdbc:sqlite:$dbPath").also { driver ->
            SemantiqueDatabase.Schema.create(driver)
        }
    }
}
