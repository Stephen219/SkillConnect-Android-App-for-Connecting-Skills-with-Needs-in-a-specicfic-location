
package com.example.mob_dev_portfolio.accountActivity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.mob_dev_portfolio.R
import com.example.mob_dev_portfolio.databinding.ActivityAppSettingsBinding

/**
 * Activity to display the app settings.
 * The user can change the theme of the app acces their account details. and log out  and Faqs
 * The theme is stored in shared preferences.
 */

class appSettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAppSettingsBinding
    private lateinit var themeSwitch: Switch
    private lateinit var sharedPref: SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPref = getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
        val nightMode = sharedPref.getBoolean("night_mode", false)

        AppCompatDelegate.setDefaultNightMode(
            if (nightMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
        super.onCreate(savedInstanceState)
        binding = ActivityAppSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        themeSwitch = binding.themeSwitch
        themeSwitch.isChecked = nightMode
        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPref.edit().putBoolean("night_mode", true).apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPref.edit().putBoolean("night_mode", false).apply()
            }
            recreate()
        }
    }
}
