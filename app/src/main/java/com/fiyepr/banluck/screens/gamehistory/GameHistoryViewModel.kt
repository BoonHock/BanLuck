package com.fiyepr.banluck.screens.gamehistory

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.fiyepr.banluck.database.GameDatabaseDao
import com.fiyepr.banluck.database.GameHistory
import kotlinx.coroutines.*
import java.text.SimpleDateFormat

class GameHistoryViewModel(
	val database: GameDatabaseDao,
	application: Application
) : AndroidViewModel(application) {
	private val _showSnackbarEvent = MutableLiveData<Boolean>()
	val showSnackbar: LiveData<Boolean>
		get() = _showSnackbarEvent

	private var viewModelJob = Job()

	override fun onCleared() {
		super.onCleared()
		viewModelJob.cancel()
	}

	private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

	private val histories = database.getAllHistories()

	val historiesString = Transformations.map(histories) { histories ->
		formatHistories(histories)
	}

	private fun formatHistories(histories: List<GameHistory>): String {
		var str = ""

		histories.forEach {
			str += "\n"
			str += (if (it.hasEnded) convertLongToDateString(it.matchTimeMilli) else "Ongoing") + "\n"
			str += "Win : " + it.winCount + "\n"
			str += "Lost: " + it.loseCount + "\n"
			str += "Run : " + it.runCount + "\n"
			str += "Tie : " + it.tieCount + "\n"
		}

		return str
	}

	init {
	}

	fun onClear() {
		uiScope.launch {
			clear()
			_showSnackbarEvent.value = true
		}
	}

	private suspend fun clear() {
		withContext(Dispatchers.IO) {
			database.clear()
		}
	}

	fun doneShowingSnackbar() {
		_showSnackbarEvent.value = false
	}

	/**
	 * Take the Long milliseconds returned by the system and stored in Room,
	 * and convert it to a nicely formatted string for display.
	 *
	 * EEEE - Display the long letter version of the weekday
	 * MMM - Display the letter abbreviation of the nmotny
	 * dd-yyyy - day in month and full year numerically
	 * HH:mm - Hours and minutes in 24hr format
	 */
	@SuppressLint("SimpleDateFormat")
	fun convertLongToDateString(systemTime: Long): String {
		return SimpleDateFormat("EEEE MMM-dd-yyyy' Time: 'HH:mm")
			.format(systemTime).toString()
	}
}