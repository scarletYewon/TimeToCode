package com.kmu.timetocode

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.FragmentActivity
import com.kmu.timetocode.favorite.FavoritePage
import com.kmu.timetocode.notice.NoticePage
import com.kmu.timetocode.search.Search

class NavActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)

        val mainFrag = MainFragment()
        val certiFrag = CertificationFragment()
        val favoriteFrag = FavoritePage()
        val searchFrag = Search()
        val noticeFrag = NoticePage()
        val challFrag = FragmentChallengeList()

        val heart = findViewById<ImageView>(R.id.heart)
        val bell = findViewById<ImageView>(R.id.bell)
        val ttc = findViewById<ImageView>(R.id.ttc)
        val btn1 = findViewById<LinearLayout>(R.id.btn1)
        val btn2 = findViewById<LinearLayout>(R.id.btn2)
        val btn3 = findViewById<LinearLayout>(R.id.btn3)

        ttc.setOnClickListener{
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerView, mainFrag)
                .commit()
        }
        btn1.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerView, certiFrag)
                .commit()
        }
        btn2.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerView, searchFrag)
                .commit()
        }
        btn3.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerView, challFrag)
                .commit()
        }
        heart.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerView, favoriteFrag)
                .commit()
        }
        bell.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerView, noticeFrag)
                .commit()
        }
        fun changeFragment(index: Int){
            when(index){
                1 -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView, favoriteFrag)
                        .commit()
                }

                2 -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView, searchFrag)
                        .commit()
                }
                3 -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView, noticeFrag)
                        .commit()
                }
            }
        }
    }

}