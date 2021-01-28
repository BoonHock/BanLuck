package com.fiyepr.banluck.screens.matchoutcome

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fiyepr.banluck.database.MatchDatabaseDao

class MatchOutcomeViewModelFactory(
	private val dataSource: MatchDatabaseDao,
	private val application: Application
) : ViewModelProvider.Factory {
	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel?> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(MatchOutcomeViewModel::class.java)) {
			return MatchOutcomeViewModel(dataSource, application) as T
		}
		throw IllegalArgumentException("Unknown ViewModel class")
	}

}