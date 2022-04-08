package fr.outadoc.semantique.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.outadoc.semantique.api.model.DayStats

@Composable
fun StatsHeader(modifier: Modifier = Modifier, dayStats: DayStats) {
    Column(modifier = modifier) {
        Text(
            "Jour n°%d".format(dayStats.dayNumber),
            style = MaterialTheme.typography.h4
        )

        Text(
            "%,d personnes ont trouvé le mot du jour.".format(dayStats.solverCount),
            style = MaterialTheme.typography.subtitle1
        )
    }
}
