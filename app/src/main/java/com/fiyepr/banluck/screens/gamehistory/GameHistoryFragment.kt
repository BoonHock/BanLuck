package com.fiyepr.banluck.screens.gamehistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fiyepr.banluck.R
import com.fiyepr.banluck.database.GameDatabase
import com.fiyepr.banluck.databinding.FragmentGameHistoryBinding
import com.google.android.material.snackbar.Snackbar

class GameHistoryFragment : Fragment() {
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		// Inflate the layout for this fragment
		val binding: FragmentGameHistoryBinding = DataBindingUtil.inflate(
			inflater, R.layout.fragment_game_history, container, false
		)

		val application = requireNotNull(this.activity).application

		val dataSource = GameDatabase.getInstance(application).gameDatabaseDao

		val viewModelFactory = GameHistoryViewModelFactory(dataSource, application)

		val gameHistoryViewModel =
			ViewModelProvider(this, viewModelFactory)
				.get(GameHistoryViewModel::class.java)

		binding.gameHistoryViewModel = gameHistoryViewModel



		binding.lifecycleOwner = this

		gameHistoryViewModel.showSnackbar.observe(viewLifecycleOwner, {
			if (it) {
				Snackbar.make(
					binding.gameHistoryFragmentContent,
					"Cleared history.",
					Snackbar.LENGTH_SHORT
				).show()
				gameHistoryViewModel.doneShowingSnackbar()
			}
		})

		val adapter = GameHistoryAdapter()
		binding.gameList.adapter = adapter

		gameHistoryViewModel.histories.observe(viewLifecycleOwner, Observer {
			it?.let {
				adapter.submitList(it)
			}
		})

		return binding.root
	}
}