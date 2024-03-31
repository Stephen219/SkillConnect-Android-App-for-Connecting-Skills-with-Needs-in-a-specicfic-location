package com.example.mob_dev_portfolio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class get_Info : AppCompatActivity() {

    private lateinit var saveButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_info)

        saveButton = findViewById(R.id.button_save)

        saveButton.setOnClickListener {
            Intent(this@get_Info, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }


    }
}