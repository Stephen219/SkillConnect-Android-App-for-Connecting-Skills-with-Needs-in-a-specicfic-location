package com.example.mob_dev_portfolio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser




class MainActivity : AppCompatActivity() {

    private lateinit var useemail:TextView
    private lateinit var button: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        useemail = findViewById(R.id.name)
        button = findViewById(R.id.logoutButton)
        bottomNavView = findViewById(R.id.bottom_navigation)
        user = auth.currentUser!!
        // this is a non-nullable type, so we can use !! to force unwrap it

        if (user != null) {
            useemail.text = user.email
        }
        if (user == null) {
            Intent(this@MainActivity, Login::class.java).also {
                startActivity(it)
                finish()
            }
        }

        button.setOnClickListener {
            auth.signOut()
            Intent(this@MainActivity, Login::class.java).also {
                startActivity(it)}

            finish()
        }
        bottomNavView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    ReplaceFragment(Home())
                    true
                }
                R.id.search -> {
                    ReplaceFragment(Search())
                     true
                }
                R.id.favourite -> {
                    ReplaceFragment(Favourites())
                     true
                }
                R.id.account -> {
                    ReplaceFragment(Account())
                    true
                }
                else -> false
            }
        }
        ReplaceFragment(Home())
    }

    private fun ReplaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}