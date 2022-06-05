package com.example.pinapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val showStoriesButton = findViewById<Button>(R.id.idBtnStories)
        showStoriesButton.setOnClickListener {
            showStories()
        }
    }

    private fun showStories() {
        val i = Intent(this@MainActivity, StoriesActivity::class.java)
        startActivity(i)
    }
}
