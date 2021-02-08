package com.fiyepr.banluck.screens.gamehistory

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.fiyepr.banluck.R
import com.fiyepr.banluck.convertLongToDateString
import com.fiyepr.banluck.database.GameHistory

@BindingAdapter("GameDateString")
fun TextView.setGameDateString(item: GameHistory?) {
	item?.let {
		text = if (it.hasEnded) {
			convertLongToDateString(item.matchTimeMilli)
		} else {
			context.getString(R.string.ongoing)
		}
	}
}

@BindingAdapter("gameWonString")
fun TextView.setGameWonString(item: GameHistory?) {
	item?.let {
		text = context.getString(R.string.won_summary, item.winCount)
	}
}

@BindingAdapter("gameLostString")
fun TextView.setGameLostString(item: GameHistory?) {
	item?.let {
		text = context.getString(R.string.lost_summary, item.loseCount)
	}
}

@BindingAdapter("gameTieString")
fun TextView.setGameTieString(item: GameHistory?) {
	item?.let {
		text = context.getString(R.string.tie_summary, item.tieCount)
	}
}

@BindingAdapter("gameRunString")
fun TextView.setGameRunString(item: GameHistory?) {
	item?.let {
		text = context.getString(R.string.run_summary, item.runCount)
	}
}

@BindingAdapter("gameImage")
fun ImageView.setGameImage(item: GameHistory?) {
	item?.let {
		Glide.with(context).load(
			if (!item.hasEnded) {
				R.drawable.history_ongoing
			} else if (item.winCount > item.loseCount) {
				R.drawable.history_happy
			} else if (item.loseCount > item.winCount) {
				R.drawable.history_sad
			} else if (item.runCount > item.tieCount) {
				R.drawable.history_run
			} else {
				R.drawable.history_tie
			}
		).into(this)
	}
}

@BindingAdapter("GameString")
fun TextView.setGameString(item: GameHistory?) {
	item?.let {
		text = context.getString(
			R.string.game_summary,
			item.winCount,
			item.loseCount,
			item.tieCount,
			item.runCount
		)
	}
}