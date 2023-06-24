package com.example.projet_mobile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Start the Navigation activity
        val intent = Intent(this, SliderView::class.java)
        startActivity(intent)

        // Finish the MainActivity (optional)
        finish()
    }
}
