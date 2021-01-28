package com.fiyepr.banluck.screens.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.fiyepr.banluck.R
import com.fiyepr.banluck.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		val binding: FragmentTitleBinding = DataBindingUtil.inflate(
			inflater, R.layout.fragment_title, container, false
		)
		binding.buttonStart.setOnClickListener(
			Navigation.createNavigateOnClickListener(R.id.action_titleFragment_to_gameFragment)
		)
		Glide.with(this).load(R.drawable.pips).into(binding.imageView)
		// Inflate the layout for this fragment
		return binding.root
	}
}