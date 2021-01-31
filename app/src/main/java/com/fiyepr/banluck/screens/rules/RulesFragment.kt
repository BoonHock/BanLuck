package com.fiyepr.banluck.screens.rules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.fiyepr.banluck.R
import com.fiyepr.banluck.databinding.FragmentRulesBinding

class RulesFragment : Fragment() {
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		val binding: FragmentRulesBinding = DataBindingUtil.inflate(
			inflater, R.layout.fragment_rules, container, false
		)

		Glide.with(this).load(R.drawable.aces).into(binding.imageView3)

		// Inflate the layout for this fragment
		return binding.root
	}
}