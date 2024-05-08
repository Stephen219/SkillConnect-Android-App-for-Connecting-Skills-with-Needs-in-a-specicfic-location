package com.example.mob_dev_portfolio.accountActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mob_dev_portfolio.R
import com.example.mob_dev_portfolio.databinding.ActivityMyAccountDetailsActitvityBinding


class myAccountDetailsActitvity : AppCompatActivity() {
    private lateinit var binding: ActivityMyAccountDetailsActitvityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account_details_actitvity)
        binding = ActivityMyAccountDetailsActitvityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



    }
}