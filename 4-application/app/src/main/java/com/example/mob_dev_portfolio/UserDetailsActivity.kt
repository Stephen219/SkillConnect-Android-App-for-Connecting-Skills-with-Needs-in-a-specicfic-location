package com.example.mob_dev_portfolio

import User
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.mob_dev_portfolio.databinding.ActivityUserDetailsBinding

class UserDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailsBinding
    // the 2 buttons
    private lateinit var callButton:Button
    private lateinit var emailButton:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val nameTextView: TextView = findViewById(R.id.name_text)
        val emailTextView: TextView = findViewById(R.id.email_text)
        val phoneTextView: TextView = findViewById(R.id.phone_text)
        val websiteTextView: TextView = findViewById(R.id.website_text)
        val skillTextView: TextView = findViewById(R.id.skill_text)
        val locationTextView: TextView = findViewById(R.id.location_text)
//        val aboutTextView: TextView = findViewById(R.id.about_text)
        // TODO: add the about text view

        val user2 = intent.extras?.get("user") as? User
        if (user2 != null) {
            nameTextView.text = user2.name
            emailTextView.text = user2.email
            phoneTextView.text = user2.phone
            websiteTextView.text = user2.website
            skillTextView.text = user2.skill
            locationTextView.text = user2.location
        }
        else {
            Intent(this@UserDetailsActivity, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
            Toast.makeText(this, "User is not found",
                Toast.LENGTH_SHORT).show()
            Log.e("UserDetailsActivity", "User is null")
        }

        callButton = findViewById(R.id.call_button)
        emailButton = findViewById(R.id.email_button)

        callButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${phoneTextView.text}")
            startActivity(intent)
        }

        emailButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:${emailTextView.text}")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Request for more information")
            intent.putExtra(Intent.EXTRA_TEXT, "Hello ${nameTextView.text},")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailTextView.text.toString()))
            startActivity(intent)
        }

    }
}