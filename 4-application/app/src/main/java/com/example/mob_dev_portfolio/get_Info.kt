
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

/**
 * This class is responsible for getting user information and saving it to the Firebase database.
 * It is called when the user clicks the "Save" button on the "Get Info" screen.
 * The user information includes name, website, phone number, email, skills, and location.
 * The information is saved to the Firebase database under the "users" node.
 * The user is then redirected to the main activity screen.
 * If any of the fields are empty, a toast message is displayed asking the user to fill all fields.
 * The user's ID is also saved to the database.
 */

class get_Info : AppCompatActivity() {
    private lateinit var saveButton: Button

    /**
     * this is the activity that comes after registering the user
     * This function is called when the activity is created.
     * It sets up the layout and functionality of the "Get Info" screen.
     * It checks if the user is connected to the internet before saving the user information to the database.
     * If the user is not connected to the internet, a toast message is displayed.
     * If the user is connected to the internet, the user information is saved to the database.
     * The user is then redirected to the main activity screen.
     */

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
            // Check if all fields are filled

            val fields = arrayOf(editName, editWeb, editPhone, editEmail, editSkill, editLocation)
            for (field in fields) {
                if (field.text.isEmpty()) {
                    field.error = "This field is required"
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            if (!checkNetwork.isNetworkAvailable(this@get_Info)) {
                Toast.makeText(this@get_Info, "No internet Connection", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validation
//            if (editName.text.isEmpty() || editWeb.text.isEmpty() || editPhone.text.isEmpty() ||
//                editEmail.text.isEmpty() || editSkill.text.isEmpty() || editLocation.text.isEmpty()) {
//                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }

            val name = editName.text.toString()
            val web = editWeb.text.toString()
            val phone = editPhone.text.toString()
            val email = editEmail.text.toString()
            val skill = editSkill.text.toString()
            val location = editLocation.text.toString()
            val database = FirebaseDatabase.getInstance()
            val usersRef = database.getReference("users")
            val userId = usersRef.push().key

            /**
             * This function gets the current Firebase user.
             * It returns the current user if they are logged in, otherwise it returns null.
             */
            fun getCurrentFirebaseUser(): FirebaseUser? {
                val auth = FirebaseAuth.getInstance()
                return auth.currentUser
            }

            /**
             * This function gets the current user ID.
             * It returns the user ID if the user is logged in, otherwise it returns null.
             */
            fun getCurrentUserId(): String? {
                val currentUser = getCurrentFirebaseUser()
                return currentUser?.uid
            }
            // here we are saving the user information to the database
            // we are saving the user information under the "users" node of the firebase database

            userId?.let {
                val userMap = hashMapOf(
                    "id" to getCurrentUserId(), // get the current user id save it as the id
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
