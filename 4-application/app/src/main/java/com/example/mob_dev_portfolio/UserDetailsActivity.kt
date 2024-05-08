package com.example.mob_dev_portfolio


import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.FrameLayout
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.example.mob_dev_portfolio.Data.User
import com.example.mob_dev_portfolio.databinding.ActivityUserDetailsBinding

/**
 * This activity displays the details of a user
 * The user can call the user, email the user, block the user, and report the user  // report and block not done as per say
 * The user can also see the rating of the user though hardcoded
 */


class UserDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailsBinding

    // the 2 buttons
    private lateinit var callButton: Button
    private lateinit var emailButton: Button


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.userdetailstools, menu)
        return true
    }

    /**
     * This function is called when the user clicks on the menu item
     * shows a flash after the actioon
     */

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_block -> {
                showFlashMessage("The user is now blocked and reported.")

                Toast.makeText(this, "you blocked the user ", Toast.LENGTH_SHORT).show()
                return true
            }

            R.id.menu_report -> {
                showFlashMessage("The user is blocked and reported.")
                return true
            }

            else -> {
                Toast.makeText(this, "you clicked on something else", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

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
        val ratingBar = findViewById<RatingBar>(R.id.rbStarReview)

        // toolbar setup

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)

        // toolbar back button
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        // rating bar setup and listener to prompt the user to review the user   now here is where i will open the review activity

        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            showReviewDialog(rating)
        }


//        val aboutTextView: TextView = findViewById(R.id.about_text)
        // TODO: add the about text view

        val user2 = intent.extras?.get("user") as? User
        if (user2 != null) {
            toolbar.title = user2.name
            nameTextView.text = user2.name
            emailTextView.text = user2.email
            phoneTextView.text = user2.phone
            websiteTextView.text = user2.website
            skillTextView.text = user2.skill
            locationTextView.text = user2.location
        } else {
            Intent(this@UserDetailsActivity, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
            Toast.makeText(
                this, "User is not found",
                Toast.LENGTH_SHORT
            ).show()
            Log.e("UserDetailsActivity", "User is null")
        }

        callButton = findViewById(R.id.call_button)
        emailButton = findViewById(R.id.email_button)

        callButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${phoneTextView.text}")
            startActivity(intent)
        }
        // email button setup and listener to send an email to the user

        emailButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:${emailTextView.text}")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Request for more information")
            intent.putExtra(Intent.EXTRA_TEXT, "Hello ${nameTextView.text},")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailTextView.text.toString()))
            startActivity(intent)
        }

    }


    /**
     * This function shows a dialog to the user to review the user
     * it shows the rating given by the user
     * it shows a message to the user to review the user
     *
     */

    private fun showReviewDialog(rating: Float) {
        val message =
            "You have given the user a $rating star rating. Would you like to review them?"
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Review this user")
            .setMessage(message)
            .setPositiveButton("Yes") { dialog, _ ->
                Toast.makeText(this, "Redirecting to review page", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                //TODO: redirect to the review page
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        alertDialog.show()
    }


    /**
     * in android , a flash is a good way to show a message to the user, an alternative to a toast
     * this function shows a flash message to the user
     * it creates a flash message with the given message
     * it shows the flash message for 2.5 seconds
     */

    private fun showFlashMessage(message: String) {
        val rootLayout =
            findViewById<FrameLayout>(R.id.user_details_layout) // Ensure it's a FrameLayout
        val flashLayout = LayoutInflater.from(this).inflate(R.layout.flashmessage, null)
        val textView = flashLayout.findViewById<TextView>(R.id.flash_message_text)
        textView.text = message
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.gravity = Gravity.TOP
        rootLayout.addView(flashLayout, layoutParams)
        val flash =
            ObjectAnimator.ofArgb(flashLayout, "backgroundColor", Color.DKGRAY, Color.TRANSPARENT)
        flash.duration = 500
        flash.repeatCount = 1
        flash.repeatMode = ObjectAnimator.REVERSE
        flash.start()
        flashLayout.postDelayed({
            rootLayout.removeView(flashLayout)
        }, 2500)
    }

}

