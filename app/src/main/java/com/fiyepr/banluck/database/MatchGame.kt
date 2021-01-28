package com.fiyepr.banluck.database

import androidx.room.*


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