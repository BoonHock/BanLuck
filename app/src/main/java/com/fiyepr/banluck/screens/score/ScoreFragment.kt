package com.fiyepr.banluck.screens.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.fiyepr.banluck.R
import com.fiyepr.banluck.databinding.FragmentScoreBinding

/**
 * A simple [Fragment] subclass.
 * Use the [ScoreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScoreFragment : Fragment() {
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		val binding: FragmentScoreBinding = DataBindingUtil.inflate(
			inflater, R.layout.fragment_score, container, false
		)
		binding.buttonNextMatch.setOnClickListener(
			Navigation.createNavigateOnClickListener(R.id.action_scoreFragment_to_gameFragment)
		)
		binding.buttonEnd.setOnClickListener(
			Navigation.createNavigateOnClickListener(R.id.action_scoreFragment_to_titleFragment)
		)

		val args = ScoreFragmentArgs.fromBundle(requireArguments())

		binding.txtWon.text = getString(R.string.won_summary, args.wonCount)
		binding.txtLost.text = getString(R.string.lost_summary, args.lostCount)
		binding.txtRun.text = getString(R.string.run_summary, args.runCount)
		binding.txtTie.text = getString(R.string.tie_summary, args.tieCount)

		// Inflate the layout for this fragment
		return binding.root
	}
}