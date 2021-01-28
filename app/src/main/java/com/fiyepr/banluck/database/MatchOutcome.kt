package com.fiyepr.banluck.database

import androidx.room.*

@Entity(tableName = "match_outcome_table")
data class MatchOutcome(
	@PrimaryKey(autoGenerate = true)
	val matchId: Long = 0L,

	@ColumnInfo(name = "win_count")
	val winCount: Int = 0,

	@ColumnInfo(name = "lose_count")
	val loseCount: Int = 0,

	@ColumnInfo(name = "tie_count")
	val tieCount: Int = 0,

	@ColumnInfo(name = "run_count")
	val runCount: Int = 0,

	@ColumnInfo(name = "match_time_milli")
	val matchTimeMilli: Long = System.currentTimeMillis()
)