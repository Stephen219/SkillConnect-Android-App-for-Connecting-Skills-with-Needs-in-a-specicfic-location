package com.example.mob_dev_portfolio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {
    private lateinit var editTextTextEmail: TextInputEditText
    private lateinit var editTextTextPassword: TextInputEditText
    private lateinit var button: MaterialButton
    private lateinit var auth: FirebaseAuth
    private lateinit var progessBar: ProgressBar
    private lateinit var texttoLogin: TextView

    public override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            Intent(this@Register, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        editTextTextEmail = findViewById(R.id.email)
        editTextTextPassword = findViewById(R.id.password)
        editTextTextPassword.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        button = findViewById(R.id.RegisterButton)
        progessBar = findViewById(R.id.progressBar)
        texttoLogin = findViewById(R.id.click_to_Login)
        auth = FirebaseAuth.getInstance()
        texttoLogin.setOnClickListener {
            var intent = Intent(this@Register, Login::class.java)
            startActivity(intent)
            finish()
        }

        button.setOnClickListener {

            if (!checkNetwork.isNetworkAvailable(this@Register)) {
                Toast.makeText(this@Register, "No internet Connection", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
                progessBar.visibility = ProgressBar.INVISIBLE
            }
            progessBar.visibility = ProgressBar.VISIBLE
            var email: String
            var password: String
            email = editTextTextEmail.text.toString()
            password = editTextTextPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                editTextTextPassword.error = "Please enter password"
                editTextTextEmail.error = "Please enter email"
                Toast.makeText(this@Register, "Please enter email and password", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }


            // from https://firebase.google.com/docs/auth/android/password-auth#kotlin+ktx_5
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        //Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(
                            this@Register,
                            "Authentication successful.",
                            Toast.LENGTH_SHORT
                        ).show()
                        // open login activity
                        Intent(this@Register, get_Info::class.java).also {
                            startActivity(it)
                            finish()
                        }

                        progessBar.visibility = ProgressBar.GONE
//                        updateUI(user)
                    } else {
                        Toast.makeText(
                            this@Register,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()

                        progessBar.visibility = ProgressBar.GONE
//                        updateUI(null)
                    }
                }

        }
    }
}



