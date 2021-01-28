package com.fiyepr.banluck.screens.matchoutcome

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.fiyepr.banluck.database.MatchDatabaseDao

class MatchOutcomeViewModel(
	val database: MatchDatabaseDao,
	application: Application
) : AndroidViewModel(application) {

}