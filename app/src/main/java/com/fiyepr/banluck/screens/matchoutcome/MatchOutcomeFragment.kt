package com.fiyepr.banluck.screens.matchoutcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fiyepr.banluck.R
import com.fiyepr.banluck.database.MatchDatabase
import com.fiyepr.banluck.databinding.FragmentMatchOutcomeBinding

class MatchOutcomeFragment : Fragment() {
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val binding: FragmentMatchOutcomeBinding = DataBindingUtil.inflate(
			inflater,
			R.layout.fragment_match_outcome,
			container,
			false
		)

		val application = requireNotNull(this.activity).application

		val dataSource = MatchDatabase.getInstance(application).matchDatabaseDao

		val viewModelFactory = MatchOutcomeViewModelFactory(dataSource, application)

		val matchOutcomeViewModel = ViewModelProvider(this, viewModelFactory)
			.get(MatchOutcomeViewModel::class.java)

		binding.matchOutcomeViewModel = matchOutcomeViewModel

		binding.lifecycleOwner = this

		// Inflate the layout for this fragment
		return binding.root
	}
}