package com.fiyepr.banluck.screens.gamehistory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.fiyepr.banluck.convertLongToDateString
import com.fiyepr.banluck.database.GameDatabaseDao
import com.fiyepr.banluck.database.GameHistory
import kotlinx.coroutines.*

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

	val histories = database.getAllHistories()

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

}