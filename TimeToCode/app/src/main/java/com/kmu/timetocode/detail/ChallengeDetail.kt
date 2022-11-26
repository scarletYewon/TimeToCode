package com.kmu.timetocode.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kmu.timetocode.R
import com.kmu.timetocode.databinding.ActivityChallengeDetailBinding

class ChallengeDetail : AppCompatActivity() {

    private lateinit var binding : ActivityChallengeDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge_detail)

        var intent = getIntent()
        var text = intent.getStringExtra("content")
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}