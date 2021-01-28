package com.fiyepr.banluck.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MatchDatabaseDao {
	@Insert
	suspend fun insert(outcome: MatchOutcome)

	@Update
	suspend fun update(outcome: MatchOutcome)

	@Query("SELECT * FROM match_outcome_table WHERE matchId = :key")
	suspend fun get(key: Long): MatchOutcome?

	@Query("DELETE FROM match_outcome_table")
	suspend fun clear()

	@Query("SELECT * FROM match_outcome_table ORDER BY matchId DESC")
	fun getAllMatches(): LiveData<List<MatchOutcome>>

	@Query("SELECT * FROM match_outcome_table ORDER BY matchId DESC LIMIT 1")
	suspend fun getThisGame():MatchOutcome?
}