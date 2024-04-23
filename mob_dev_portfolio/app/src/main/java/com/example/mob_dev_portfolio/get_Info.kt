
package com.example.mob_dev_portfolio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import java.util.UUID

class get_Info : AppCompatActivity() {
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_info)

        saveButton = findViewById(R.id.button_save)

        saveButton.setOnClickListener {
            val editName = findViewById<EditText>(R.id.edit_name)
            val editWeb = findViewById<EditText>(R.id.edit_website)
            val editPhone = findViewById<EditText>(R.id.edit_phone)
            val editEmail = findViewById<EditText>(R.id.edit_email)
            val editSkill = findViewById<EditText>(R.id.edit_skills)
            val editLocation = findViewById<EditText>(R.id.edit_location)

            // Validation
            if (editName.text.isEmpty() || editWeb.text.isEmpty() || editPhone.text.isEmpty() ||
                editEmail.text.isEmpty() || editSkill.text.isEmpty() || editLocation.text.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val name = editName.text.toString()
            val web = editWeb.text.toString()
            val phone = editPhone.text.toString()
            val email = editEmail.text.toString()
            val skill = editSkill.text.toString()
            val location = editLocation.text.toString()
            val database = FirebaseDatabase.getInstance()
            val usersRef = database.getReference("users")
            val userId = usersRef.push().key


            fun getCurrentFirebaseUser(): FirebaseUser? {
                val auth = FirebaseAuth.getInstance()
                return auth.currentUser
            }
            fun getCurrentUserId(): String? {
                val currentUser = getCurrentFirebaseUser()
                return currentUser?.uid
            }


            userId?.let {
                val userMap = hashMapOf(
                    "id" to getCurrentUserId(),
                    "name" to name,
                    "website" to web,
                    "phone" to phone,
                    "email" to email,
                    "skill" to skill,
                    "location" to location
                )
                usersRef.child(it).setValue(userMap)
                Intent(this@get_Info, MainActivity::class.java).also { intent ->
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}
