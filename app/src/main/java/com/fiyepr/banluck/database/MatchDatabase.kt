package com.fiyepr.banluck.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// https://classroom.udacity.com/courses/ud9012/lessons/fcd3f9aa-3632-4713-a299-ea39939d6fd7/concepts/5b079cb7-685d-48b5-bb59-b1f72d2ad5d0
@Database(entities = [MatchOutcome::class], version = 1, exportSchema = false)
abstract class MatchDatabase : RoomDatabase() {
	abstract val matchDatabaseDao: MatchDatabaseDao

	companion object {
		@Volatile
		private var INSTANCE: MatchDatabase? = null

		fun getInstance(context: Context): MatchDatabase {
			synchronized(this) {
				var instance = INSTANCE

				if (instance == null) {
					instance = Room.databaseBuilder(
						context.applicationContext,
						MatchDatabase::class.java,
						"match_database" // database's name
					)
						.fallbackToDestructiveMigration()
						.build()
					INSTANCE = instance
				}

				return instance
			}
		}
	}
}