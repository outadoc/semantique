package fr.outadoc.semantique.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.outadoc.semantique.api.model.DayStats
import fr.outadoc.semantique.ui.strings.stringResource

@Composable
fun StatsHeader(modifier: Modifier = Modifier, dayStats: DayStats) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            text = stringResource(MR.strings.header_day, dayStats.dayNumber),
            style = MaterialTheme.typography.h5
        )

        Text(
            text = stringResource(MR.strings.header_winners, dayStats.solverCount),
            style = MaterialTheme.typography.subtitle1
        )
    }
}
