package com.kmu.timetocode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myChallenge = findViewById<View>(R.id.myChallenge) as Button
        myChallenge.setOnClickListener {
            Toast.makeText(applicationContext, "내 챌린지 인증", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, MychallengeActivity::class.java)
            startActivity(intent)
        }
    }
}