package com.fiyepr.banluck.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface GameDatabaseDao {
	@Insert
	suspend fun insert(outcome: GameHistory)

	@Update
	suspend fun update(outcome: GameHistory)

	@Query("SELECT * FROM game_history_table WHERE matchId = :key")
	suspend fun get(key: Long): GameHistory?

	@Query("DELETE FROM game_history_table")
	suspend fun clear()

	@Query("SELECT * FROM game_history_table ORDER BY matchId DESC")
	fun getAllHistories(): LiveData<List<GameHistory>>

	@Query("SELECT * FROM game_history_table ORDER BY matchId DESC LIMIT 1")
	suspend fun getCurrentGame(): GameHistory?
}