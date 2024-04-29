package com.example.mob_dev_portfolio.accountActivity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.mob_dev_portfolio.Login
import com.example.mob_dev_portfolio.databinding.ActivityAppSettingsBinding
import com.google.firebase.auth.FirebaseAuth

/**
 * Activity to display the app settings.
 * The user can change the theme of the app acces their account details. and log out  and Faqs
 * The theme is stored in shared preferences.
 */

class appSettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAppSettingsBinding
    private lateinit var themeSwitch: Switch
    private lateinit var sharedPref: SharedPreferences
    private lateinit var logout: Button
    private lateinit var faqs: LinearLayout
    private lateinit var accountDetails: LinearLayout
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPref = getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
        val nightMode = sharedPref.getBoolean("night_mode", false)

        AppCompatDelegate.setDefaultNightMode(
            if (nightMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
        super.onCreate(savedInstanceState)
        binding = ActivityAppSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        themeSwitch = binding.themeSwitch
        logout = binding.logoutButton
        faqs = binding.faqsOption
        accountDetails = binding.accountOption

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
        /**
         * logout button
         */

        logout.setOnClickListener {
            auth.signOut()
            Intent(this@appSettingsActivity, Login::class.java).also {
                startActivity(it)
            }
            Toast.makeText(this, "You have been Logged out successifully", Toast.LENGTH_SHORT)
                .show()
            finish()
        }

        /**
         * faqs button
         */
        faqs.setOnClickListener {
            Toast.makeText(this, "FAQs", Toast.LENGTH_SHORT).show()
        }

        /**
         * account details button
         */
        accountDetails.setOnClickListener {
            Intent(this@appSettingsActivity, myAccountDetailsActitvity::class.java).also {
                startActivity(it)
            }
        }
    }
}
