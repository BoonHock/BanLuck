package com.fiyepr.banluck

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.fiyepr.banluck.databinding.ActivityMainBinding
import com.google.android.gms.ads.*

class MainActivity : AppCompatActivity() {
	private lateinit var drawerLayout: DrawerLayout
	private lateinit var appBarConfiguration: AppBarConfiguration

	companion object {
		// minus x seconds so that first interstitial appear earlier
		var adTimer: Long = System.currentTimeMillis() - 40000
		lateinit var mAdView: AdView
		lateinit var mInterstitialAd: InterstitialAd

		fun displayInterstitialAd() {
			// show interstitial ads after at least 90s from previous one to prevent spam
			if (System.currentTimeMillis() - adTimer > 90000 && mInterstitialAd.isLoaded) {
				mInterstitialAd.show()
				adTimer = System.currentTimeMillis()
			}
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val binding =
			DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

		drawerLayout = binding.drawerLayout

		val navController = this.findNavController(R.id.nav_host_fragment)
		NavigationUI.setupActionBarWithNavController(
			this, navController, drawerLayout
		)
		appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

		navController.addOnDestinationChangedListener { nc: NavController,
														nd: NavDestination,
														_: Bundle? ->
			if (nd.id == nc.graph.startDestination) {
				drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
			} else {
				drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
			}
		}
		NavigationUI.setupWithNavController(binding.navView, navController)

		MobileAds.initialize(this) {}

		mAdView = binding.adView
		val adRequest = AdRequest.Builder().build()
		mAdView.loadAd(adRequest)

		mInterstitialAd = InterstitialAd(this)
		mInterstitialAd.adUnitId = getString(R.string.adUnitId_Interstitial)
		mInterstitialAd.loadAd(AdRequest.Builder().build())
		mInterstitialAd.adListener = object : AdListener() {
			override fun onAdClosed() {
				mInterstitialAd.loadAd(AdRequest.Builder().build())
			}
		}
	}

	override fun onSupportNavigateUp(): Boolean {
		val navController = this.findNavController(R.id.nav_host_fragment)
		return NavigationUI.navigateUp(navController, appBarConfiguration)
	}
}