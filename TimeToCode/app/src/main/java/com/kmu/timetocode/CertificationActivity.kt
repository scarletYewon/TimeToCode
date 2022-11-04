package com.kmu.timetocode

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class CertificationActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certification)

        val challengeImage = findViewById<ImageButton>(R.id.challengeImage)
        val challengeTitle = findViewById<Button>(R.id.challengeTitle)
        val btnCertification = findViewById<Button>(R.id.btnCertification)
        val btnGallery = findViewById<Button>(R.id.btnGallery)
    }
}