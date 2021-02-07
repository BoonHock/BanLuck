package com.fiyepr.banluck.screens.gamehistory

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fiyepr.banluck.R
import com.fiyepr.banluck.convertLongToDateString
import com.fiyepr.banluck.database.GameHistory
import com.fiyepr.banluck.databinding.ListItemGameHistoryBinding

class GameHistoryAdapter :
	ListAdapter<GameHistory, GameHistoryAdapter.ViewHolder>(GameHistoryDiffCallback()) {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder.from(parent)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = getItem(position)
		val resources = holder.itemView.context.resources

		holder.bind(item, resources)
	}

	class ViewHolder private constructor(val binding: ListItemGameHistoryBinding) :
		RecyclerView.ViewHolder(binding.root) {

		fun bind(item: GameHistory, resources: Resources) {
			binding.txtHistoryDate.text =
				if (item.hasEnded) convertLongToDateString(item.matchTimeMilli) else "Ongoing"
			binding.txtHistoryWon.text = resources.getString(R.string.won_summary, item.winCount)
			binding.txtHistoryLost.text = resources.getString(R.string.lost_summary, item.loseCount)
			binding.txtHistoryTie.text = resources.getString(R.string.tie_summary, item.tieCount)
			binding.txtHistoryRun.text = resources.getString(R.string.run_summary, item.runCount)

			Glide.with(itemView).load(
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
			).into(binding.imgHistory)
		}

		companion object {
			fun from(parent: ViewGroup): ViewHolder {
				val layoutInflater = LayoutInflater.from(parent.context)
				val binding = ListItemGameHistoryBinding
					.inflate(layoutInflater, parent, false)
				return ViewHolder(binding)
			}
		}
	}
}

class GameHistoryDiffCallback : DiffUtil.ItemCallback<GameHistory>() {
	override fun areItemsTheSame(oldItem: GameHistory, newItem: GameHistory): Boolean {
		return oldItem.matchId == newItem.matchId
	}

	override fun areContentsTheSame(oldItem: GameHistory, newItem: GameHistory): Boolean {
		return oldItem == newItem
	}

}