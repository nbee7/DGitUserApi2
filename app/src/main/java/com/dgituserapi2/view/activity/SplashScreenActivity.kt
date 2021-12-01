package com.dgituserapi2.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.dgituserapi2.databinding.ActivitySplashScreenBinding
import com.dgituserapi2.theme.ThemePreference
import com.dgituserapi2.theme.ThemeViewModel
import com.dgituserapi2.utility.BundleKeys.ALPHA
import com.dgituserapi2.utility.BundleKeys.DURATION_SPLASH
import com.dgituserapi2.utility.ViewModelFactoryTheme

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = binding.root

        supportActionBar?.hide()

        val pref = ThemePreference.getInstance(dataStore)
        val themeViewModel = ViewModelProvider(this, ViewModelFactoryTheme(pref))[ThemeViewModel::class.java]

        themeViewModel.getThemeSetting().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                setContentView(view)
            })
        binding.icGithub.animate().setDuration(DURATION_SPLASH.toLong()).alpha(ALPHA).withEndAction {
            toMainActivity()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }

    private fun toMainActivity() {
        MainActivity.start(this)
        finish()
    }
}