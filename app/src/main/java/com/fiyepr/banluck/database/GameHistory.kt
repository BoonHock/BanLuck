package com.fiyepr.banluck.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_history_table")
data class GameHistory(
	@PrimaryKey(autoGenerate = true)
	val matchId: Long = 0L,

	@ColumnInfo(name = "win_count")
	var winCount: Int = 0,

	@ColumnInfo(name = "lose_count")
	var loseCount: Int = 0,

	@ColumnInfo(name = "tie_count")
	var tieCount: Int = 0,

	@ColumnInfo(name = "run_count")
	var runCount: Int = 0,

	@ColumnInfo(name = "match_time_milli")
	var matchTimeMilli: Long = System.currentTimeMillis(),

	@ColumnInfo(name = "has_ended")
	var hasEnded : Boolean = false
)



/*
*
*

@Entity(
	tableName = "match_game_table",
	foreignKeys = [
		ForeignKey(
			entity = MatchOutcome::class,
			parentColumns = ["match_id"],
			childColumns = ["game_match_id"]
		)
	]
)
data class MatchGame(
	@PrimaryKey(autoGenerate = true)
	val gameId: Long = 0L,

	@ColumnInfo(name = "game_match_id")
	val gameMatchId: Long = 0L
)

data class MatchWithGame(
	@Embedded
	val match: MatchOutcome,

	@Relation(
		parentColumn = "match_id",
		entity = MatchOutcome::class,
		entityColumn = "game_match_id"
	)
	val games: List<MatchGame>
)

* *
*
*
*
*
* */