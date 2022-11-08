package com.kmu.timetocode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController

class NavActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)

        val favoriteFrag = FavoritePage()
        val searchFrag = SearchPage()
        val noticeFrag = NoticePage()

        val btn1 = findViewById<LinearLayout>(R.id.btn1);
        val btn2 = findViewById<LinearLayout>(R.id.btn2);
        val btn3 = findViewById<LinearLayout>(R.id.btn3);

        btn1.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerView, favoriteFrag)
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