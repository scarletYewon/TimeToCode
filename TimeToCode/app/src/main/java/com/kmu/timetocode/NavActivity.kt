package com.kmu.timetocode

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.kmu.timetocode.favorite.FavoritePage
import com.kmu.timetocode.search.Search
import com.kmu.timetocode.certicenter.CertificationFragment
import com.kmu.timetocode.list.FragmentChallengeList

class NavActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)

        val mainFrag = MainFragment()
        val certiFrag = CertificationFragment()
        val favoriteFrag = FavoritePage()
        val searchFrag = Search()
        val challFrag = FragmentChallengeList()

        val heart = findViewById<ImageView>(R.id.heart)
        val search = findViewById<ImageView>(R.id.Searchbtn)
        val ttc = findViewById<ImageView>(R.id.ttc)
        val btn1 = findViewById<LinearLayout>(R.id.btn1)
        val btn2 = findViewById<LinearLayout>(R.id.btn2)
        val btn3 = findViewById<LinearLayout>(R.id.btn3)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerView, mainFrag)
            .commit()

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
                .replace(R.id.fragmentContainerView, mainFrag)
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
        search.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerView, searchFrag)
                .commit()
        }
    }

    fun replaceFragment(fragment : Fragment){
        var fragmentManager = supportFragmentManager
        var fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment).commit()
    }
}