package com.example.mob_dev_portfolio

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

/**
 * The App class that initializes global settings when the application starts.
 * ideally, when the user changes the theme in the settings, the theme should be applied to the entire app
 * without this the the theme will only be applied in the appSettingsActitbty and hence i intoroduced this class
 * This class sets the default night mode based on the user's stored preference.
 */

class App : Application() {
    /**
     * Called when the application is created. Initializes the default theme
     * mode based on the user's stored preference for night mode.
     */

    override fun onCreate() {
        super.onCreate()
        val sharedPref = getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
        val nightMode = sharedPref.getBoolean("night_mode", false)

        AppCompatDelegate.setDefaultNightMode(
            if (nightMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )


    }
}
