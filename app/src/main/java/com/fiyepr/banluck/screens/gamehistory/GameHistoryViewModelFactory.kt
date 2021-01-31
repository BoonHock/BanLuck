package com.fiyepr.banluck.screens.gamehistory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fiyepr.banluck.database.GameDatabaseDao
import com.fiyepr.banluck.screens.game.GameViewModel

class GameHistoryViewModelFactory(
	private val dataSource: GameDatabaseDao,
	private val application: Application
) : ViewModelProvider.Factory {
	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel?> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(GameHistoryViewModel::class.java)) {
			return GameHistoryViewModel(dataSource, application) as T
		}
		throw IllegalArgumentException("Unknown ViewModel class")
	}
}