package com.fiyepr.banluck

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.fiyepr.banluck.database.MatchDatabase
import com.fiyepr.banluck.database.MatchDatabaseDao
import com.fiyepr.banluck.database.MatchOutcome
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MatchDatabaseTest {
	private lateinit var matchDao: MatchDatabaseDao
	private lateinit var db: MatchDatabase

	@Before
	fun createDb() {
		val context = InstrumentationRegistry.getInstrumentation().targetContext
		// Using an in-memory database because the information stored here disappears when the
		// process is killed.
		db = Room.inMemoryDatabaseBuilder(context, MatchDatabase::class.java)
			// Allowing main thread queries, just for testing.
			.allowMainThreadQueries()
			.build()
		matchDao = db.matchDatabaseDao
	}

	@After
	@Throws(IOException::class)
	fun closeDb() {
		db.close()
	}

	@Test
	@Throws(Exception::class)
	suspend fun insertAndGetNight() {
		val match = MatchOutcome()
		matchDao.insert(match)
		val tonight = matchDao.getThisGame()
		assertEquals(tonight?.winCount, -1)
	}
}