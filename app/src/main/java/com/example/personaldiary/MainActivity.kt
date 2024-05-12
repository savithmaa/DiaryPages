package com.example.personaldiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var logo: ImageView
    private lateinit var text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)

        logo = findViewById(R.id.logo)

        logo.alpha = 0f
        logo.animate().setDuration(6000).alpha(1f).withEndAction {
            val i = Intent(this, DiaryPages::class.java) // Replace NextActivity with the actual name of your next activity
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }

        text = findViewById(R.id.textView)

        text.alpha = 0f
        text.animate().setDuration(6000).alpha(1f).withEndAction {
            val i = Intent(this, DiaryPages::class.java) // Replace NextActivity with the actual name of your next activity
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}