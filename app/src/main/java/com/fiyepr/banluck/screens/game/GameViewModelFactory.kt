package com.fiyepr.banluck.screens.game

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fiyepr.banluck.database.GameDatabaseDao

class GameViewModelFactory(
	private val dataSource: GameDatabaseDao,
	private val application: Application
) : ViewModelProvider.Factory {
	override fun <T : ViewModel?> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
			return GameViewModel(dataSource, application) as T
		}
		throw IllegalArgumentException("Unknown ViewModel class")
	}

}