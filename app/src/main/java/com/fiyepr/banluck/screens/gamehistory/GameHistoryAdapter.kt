package com.fiyepr.banluck.screens.gamehistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fiyepr.banluck.database.GameHistory
import com.fiyepr.banluck.databinding.ListItemGameHistoryBinding

class GameHistoryAdapter(val clickListener: GameHistoryListener) :
	ListAdapter<GameHistory, GameHistoryAdapter.ViewHolder>(GameHistoryDiffCallback()) {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder.from(parent)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.bind(getItem(position)!!, clickListener)
	}

	class ViewHolder private constructor(val binding: ListItemGameHistoryBinding) :
		RecyclerView.ViewHolder(binding.root) {

		fun bind(item: GameHistory, clickListener: GameHistoryListener) {
			binding.game = item
			binding.clickListener = clickListener
			binding.executePendingBindings()
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

class GameHistoryListener(val clickListener: (matchId: Long) -> Unit) {
	fun onClick(game: GameHistory) = clickListener(game.matchId)
}