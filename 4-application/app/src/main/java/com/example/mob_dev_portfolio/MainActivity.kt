package com.example.mob_dev_portfolio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mob_dev_portfolio.Data.UserViewModel
import com.example.mob_dev_portfolio.accountActivity.appSettingsActivity
import com.example.mob_dev_portfolio.accountActivity.myAccountDetailsActitvity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.example.mob_dev_portfolio.checkNetwork
import com.example.mob_dev_portfolio.checkNetwork.recheckNetworkAfterDelay


class MainActivity : AppCompatActivity() {
    // Todo : code documentation for all the classes

    private lateinit var useemail: TextView
    private lateinit var button: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var mUserViewModel: UserViewModel
    private val counterToRecheckNetwork = 5


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_file, menu) // Inflate the menu
        return true
    }

    // Handle menu item selections
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.account -> {
                Intent(this@MainActivity, myAccountDetailsActitvity::class.java).also {
                    startActivity(it)
                }
                Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show()
                return true
            }

            R.id.setting_real -> {

                Intent(this@MainActivity, appSettingsActivity::class.java).also {
                    startActivity(it)
                }


                return true
            }

            R.id.logout -> {
                auth.signOut()
                Intent(this@MainActivity, Login::class.java).also {
                    startActivity(it)
                }
                return true
            }

            else -> return super.onOptionsItemSelected(item) // Default handling
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.top_action_bar)
        setSupportActionBar(toolbar)
        auth = FirebaseAuth.getInstance()
        useemail = findViewById(R.id.name)
//        button = findViewById(R.id.logoutButton)
        bottomNavView = findViewById(R.id.bottom_navigation)
        user = auth.currentUser!!
        // this is a non-nullable type, so we can use !! to force unwrap it

        if (user != null) {
            Log.d("MainActivity", "User is not null and is ${user.email}")
        }
        if (user == null) {
            Intent(this@MainActivity, Login::class.java).also {
                startActivity(it)
                finish()
            }
        }
        Log.d("the network status", checkNetwork.isNetworkAvailable(this).toString())
        Log.d("the network status", checkNetwork.isNetworkAvailable(this).toString())
        Log.d("the network status", checkNetwork.isNetworkAvailable(this).toString())

        if (!checkNetwork.isNetworkAvailable(this)) {
            Toast.makeText(
                this,
                "No internet connection. Please check your network.",
                Toast.LENGTH_SHORT
            ).show()
            recheckNetworkAfterDelay(this, 6000)

        }


//        button.setOnClickListener {
//            auth.signOut()
//            Intent(this@MainActivity, Login::class.java).also {
//                startActivity(it)}
//
//            finish()
//        }
        bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    ReplaceFragment(Home())
                    if (!checkNetwork.isNetworkAvailable(this)) {
                        Toast.makeText(
                            this,
                            "No internet connection. Please check your network.",
                            Toast.LENGTH_LONG
                        ).show()


                    }
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

    private fun ReplaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}