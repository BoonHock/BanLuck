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
		holder.bind(item)
	}

	class ViewHolder private constructor(val binding: ListItemGameHistoryBinding) :
		RecyclerView.ViewHolder(binding.root) {

		fun bind(item: GameHistory) {
			binding.game = item
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