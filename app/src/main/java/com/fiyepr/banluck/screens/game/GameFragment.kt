package com.fiyepr.banluck.screens.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.fiyepr.banluck.MainActivity
import com.fiyepr.banluck.R
import com.fiyepr.banluck.databinding.FragmentGameBinding
import com.google.android.material.snackbar.Snackbar

class GameFragment : Fragment() {
	private lateinit var viewModel: GameViewModel
	private lateinit var binding: FragmentGameBinding

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = DataBindingUtil.inflate(
			inflater, R.layout.fragment_game, container, false
		)

		viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
		binding.gameViewModel = viewModel
		binding.lifecycleOwner = this

		viewModel.playerCard1.observe(viewLifecycleOwner, { card ->
			updateCard(card, binding.playerCard1, true)
		})
		viewModel.playerCard2.observe(viewLifecycleOwner, { card ->
			updateCard(card, binding.playerCard2, true)
		})
		viewModel.playerCard3.observe(viewLifecycleOwner, { card ->
			updateCard(card, binding.playerCard3, true)
		})
		viewModel.playerCard4.observe(viewLifecycleOwner, { card ->
			updateCard(card, binding.playerCard4, true)
		})
		viewModel.playerCard5.observe(viewLifecycleOwner, { card ->
			updateCard(card, binding.playerCard5, true)
		})
		viewModel.comCard1.observe(viewLifecycleOwner, { card ->
			updateCard(card, binding.comCard1, false)
		})
		viewModel.comCard2.observe(viewLifecycleOwner, { card ->
			updateCard(card, binding.comCard2, false)
		})
		viewModel.comCard3.observe(viewLifecycleOwner, { card ->
			updateCard(card, binding.comCard3, false)
		})
		viewModel.comCard4.observe(viewLifecycleOwner, { card ->
			updateCard(card, binding.comCard4, false)
		})
		viewModel.comCard5.observe(viewLifecycleOwner, { card ->
			updateCard(card, binding.comCard5, false)
		})
		viewModel.comText.observe(viewLifecycleOwner, { text ->
			binding.txtComCardVal.text = text
		})
		viewModel.playerCardMax.observe(viewLifecycleOwner, { isMax ->
			binding.buttonDeal.isEnabled = !isMax
		})
		viewModel.gameStatus.observe(viewLifecycleOwner, { gameStatus ->
			if (gameStatus != GameViewModel.GameStatus.ONGOING) {
				// game's over
				// set text in game over text
				binding.txtGameOver.text = when (gameStatus) {
					GameViewModel.GameStatus.WON -> getString(R.string.game_won)
					GameViewModel.GameStatus.LOST -> getString(R.string.game_lost)
					GameViewModel.GameStatus.RUN -> getString(R.string.game_run)
					else -> getString(R.string.game_tie)
				}

				binding.gameOverLayout.visibility = View.VISIBLE // show game over layout
				binding.txtComCardVal.visibility = View.VISIBLE // show com score

				// display com's card
				updateCardPng(viewModel.comCard1.value ?: "", binding.comCard1)
				updateCardPng(viewModel.comCard2.value ?: "", binding.comCard2)
				updateCardPng(viewModel.comCard3.value ?: "", binding.comCard3)
				updateCardPng(viewModel.comCard4.value ?: "", binding.comCard4)
				updateCardPng(viewModel.comCard5.value ?: "", binding.comCard5)
			} else {
				// game is ongoing
				binding.gameOverLayout.visibility = View.GONE // hide game over layout
				binding.txtComCardVal.visibility = View.INVISIBLE // hide com score

				// hide com's card
				binding.comCard1.setImageResource(R.drawable.yellow_back)
				binding.comCard2.setImageResource(R.drawable.yellow_back)
				binding.comCard3.setImageResource(R.drawable.yellow_back)
				binding.comCard4.setImageResource(R.drawable.yellow_back)
				binding.comCard5.setImageResource(R.drawable.yellow_back)
				binding.comCard1.visibility = View.INVISIBLE
				binding.comCard2.visibility = View.INVISIBLE
				binding.comCard3.visibility = View.INVISIBLE
				binding.comCard4.visibility = View.INVISIBLE
				binding.comCard5.visibility = View.INVISIBLE
			}
		})
		viewModel.mustHitCard.observe(viewLifecycleOwner, { mustHit ->
			if (mustHit) {
				Snackbar.make(
					binding.gameFragmentContent,
					"Your card total is lesser than 16. Please deal card instead.",
					Snackbar.LENGTH_SHORT
				).show()
			}
		})

		binding.buttonEndGame.setOnClickListener { view ->
			// proceed to display score
			view.findNavController().navigate(
				GameFragmentDirections
					.actionGameFragmentToScoreFragment(
						viewModel.wonCount,
						viewModel.lostCount,
						viewModel.tieCount,
						viewModel.runCount
					)
			)
		}
		binding.buttonAgain.setOnClickListener {
			MainActivity.displayInterstitialAd()
			viewModel.onAgain()
		}

		Glide.with(this).load(R.drawable.blue_back).into(binding.deck)
		// Inflate the layout for this fragment
		return binding.root
	}

	/*
	 * update card imageView. if @is_player = true, will display card's front
	 * else, display card's back
	 * */
	private fun updateCard(card: String, imgView: ImageView, is_player: Boolean) {
		if (card.isEmpty()) {
			imgView.visibility = View.INVISIBLE
		} else {
			if (is_player) {
				updateCardPng(card, imgView)
			}
			imgView.visibility = View.VISIBLE
		}
	}

	private fun updateCardPng(card: String, imgView: ImageView) {
		if (card.isNotEmpty()) {
			context?.resources?.let {
				Glide.with(this)
					.load(
						it.getIdentifier(
							"drawable/$card",
							null,
							context?.packageName
						)
					)
					.into(imgView)
			}
		}
	}
}