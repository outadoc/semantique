package fr.outadoc.semantique.api.model

data class DayStats(

    /**
     * Day number, i.e. since number of days since the game has been running.
     */
    val dayNumber: Long,

    /**
     * Number of people who solved the word today.
     */
    val solverCount: Long
)
