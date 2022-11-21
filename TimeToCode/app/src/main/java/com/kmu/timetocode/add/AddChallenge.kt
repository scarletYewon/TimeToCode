package com.kmu.timetocode.add

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kmu.timetocode.databinding.ActivityAddChallengeBinding


class AddChallenge : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager

    private lateinit var binding : ActivityAddChallengeBinding

    private var fragmentAddCh1: FragmentAddChallenge1? = null
    private var fragmentAddCh2: FragmentAddChallenge2? = null
    private var fragmentAddCh3: FragmentAddChallenge3? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddChallengeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val fragmentAddCh1 = FragmentAddChallenge1()
        val fragmentAddCh2 = FragmentAddChallenge2()
        val fragmentAddCh3 = FragmentAddChallenge3()
        val fragmentAddCh4 = FragmentAddChallenge4()


//        binding.btnNextAC.setOnClickListener {
//            setFragment()
//        }

//        binding.btnNextAC.setOnClickListener{
//            val bundle = Bundle()
//            fragmentAddCh2.arguments = bundle
//            val transaction = supportFragmentManager.beginTransaction()
//            transaction.replace(R.id.frameLayout, fragmentAddCh2)
//            transaction.commit()
//        }

    }
//    private fun setFragment() {
//        val fragment: FragmentAddChallenge1 =
//            supportFragmentManager.findFragmentByTag("add1") as FragmentAddChallenge1
//    }

}