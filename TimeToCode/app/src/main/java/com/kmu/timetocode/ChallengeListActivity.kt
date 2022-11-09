package com.kmu.timetocode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.chip.Chip
import com.kmu.timetocode.databinding.ActivityAddChallengeBinding
import com.kmu.timetocode.databinding.ActivityChallengeListBinding
import com.kmu.timetocode.databinding.ChallengeListItemBinding
import com.kmu.timetocode.databinding.ChoiceTagBinding

class ChallengeListActivity : AppCompatActivity() {

    lateinit var binding : ActivityChallengeListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChallengeListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


    }



}