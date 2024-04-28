package com.example.mob_dev_portfolio

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class Login : AppCompatActivity() {

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
            Intent(this@Login, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextTextEmail = findViewById(R.id.email)
        editTextTextPassword = findViewById(R.id.password)
        button = findViewById(R.id.loginButton)
        texttoLogin = findViewById(R.id.Register_now)
        auth = FirebaseAuth.getInstance()
        progessBar = findViewById(R.id.progressBar)
        texttoLogin.setOnClickListener {
            var intent = Intent(this@Login, Register::class.java)
            startActivity(intent)
            finish()
        }
        button.setOnClickListener {
            progessBar.visibility = ProgressBar.VISIBLE
            var email: String
            var password: String
            email = editTextTextEmail.text.toString()
            password = editTextTextPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@Login, "Please enter email and password", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            fun updateUI(user: FirebaseUser?) {
                if (user != null) {
                    Toast.makeText(this@Login, "Login Successful!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@Login, "Login Failed!", Toast.LENGTH_SHORT).show()
                }
            }
            // from https://firebase.google.com/docs/auth/android/password-auth#kotlin+ktx_5
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)

                        Intent(this@Login, MainActivity::class.java).also {
                            startActivity(it)
                            finish()
                        }


                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        updateUI(null)
                    }

                }


        }



    }
}