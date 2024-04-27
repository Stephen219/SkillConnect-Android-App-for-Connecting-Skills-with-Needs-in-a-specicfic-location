package com.example.mob_dev_portfolio.accountActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mob_dev_portfolio.R
import com.example.mob_dev_portfolio.databinding.ActivityAppSettingsBinding

class appSettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAppSettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_settings)
        binding = ActivityAppSettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}