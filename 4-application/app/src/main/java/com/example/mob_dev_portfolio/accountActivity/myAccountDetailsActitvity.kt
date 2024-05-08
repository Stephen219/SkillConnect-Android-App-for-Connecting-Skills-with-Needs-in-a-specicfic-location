package com.example.mob_dev_portfolio.accountActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mob_dev_portfolio.R
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


/**
 this is the clas that enables the user to update their account details
 */

class myAccountDetailsActitvity : AppCompatActivity() {
    private lateinit var nameText: EditText
    private lateinit var skillText: EditText
    private lateinit var phoneText: EditText
    private lateinit var emailText: EditText
    private lateinit var locationText: EditText
    private lateinit var websiteText: EditText
    private lateinit var saveButton: Button
    private lateinit var database: DatabaseReference
    private lateinit var userUid: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account_details_actitvity)
        database = FirebaseDatabase.getInstance().getReference("users")
        userUid = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        nameText = findViewById(R.id.name_text)
        skillText = findViewById(R.id.skill_text)
        phoneText = findViewById(R.id.phone_text)
        emailText = findViewById(R.id.email_text)
        locationText = findViewById(R.id.location_text)
        websiteText = findViewById(R.id.website_text)
        saveButton = findViewById(R.id.saveInfo)

        saveButton.setOnClickListener {
            val updatedName = nameText.text.toString()
            val updatedSkill = skillText.text.toString()
            val updatedPhone = phoneText.text.toString()
            val updatedEmail = emailText.text.toString()
            val updatedLocation = locationText.text.toString()
            val updatedWebsite = websiteText.text.toString()
            updateUserInformation(updatedName, updatedSkill, updatedPhone, updatedEmail, updatedLocation, updatedWebsite)
        }
    }

    private fun updateUserInformation(
        name: String,
        skill: String,
        phone: String,
        email: String,
        location: String,
        website: String
    ) {
        // Construct a map with updated user information
        val userUpdates = mapOf(
            "name" to name,
            "skill" to skill,
            "phone" to phone,
            "email" to email,
            "location" to location,
            "website" to website
        )

        // Update the user information in the Realtime Database
        database.child(userUid).updateChildren(userUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Information updated successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to update information. Please try again.", Toast.LENGTH_SHORT).show()
                }
            }
    }

}
