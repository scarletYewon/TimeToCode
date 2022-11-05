package com.kmu.timetocode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val TAG_CERTIFICATION = "certification_fragment"
private const val TAG_SEARCH = "search_fragment"
private const val TAG_CHALLENGE = "challenge_fragment"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val userName = findViewById<TextView>(R.id.userName)
        val userLevel = findViewById<TextView>(R.id.userLevel)
        val myChallenge = findViewById<Button>(R.id.myChallenge)
        val calendarView = findViewById<CalendarView>(R.id.calenderView)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.navigationView)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 왼쪽 버튼 사용 설정
        supportActionBar!!.setHomeAsUpIndicator(com.kmu.timetocode.R.drawable.ic_baseline_favorite_24)

        myChallenge.setOnClickListener {
            val intent = Intent(this, CertificationActivity::class.java)
            startActivity(intent)
        }


        bottomNavigation!!.setOnItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.certificationFragment -> {
                    Toast.makeText(applicationContext, "인증센터", Toast.LENGTH_SHORT).show()
                    setFragment(TAG_CERTIFICATION, CertificationFragment())
                }
                R.id.searchFragment -> {
                    Toast.makeText(applicationContext, "검색", Toast.LENGTH_SHORT).show()
                    setFragment(TAG_SEARCH, SearchFragment())
                }
                R.id.challengeFragment -> {
                    Toast.makeText(applicationContext, "챌린지", Toast.LENGTH_SHORT).show()
                    setFragment(TAG_CHALLENGE, ChallengeFragment())
                }
            }
            true
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
    // 툴바의 버튼 눌렀을 때 화면전환
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                Toast.makeText(applicationContext, "찜한 챌린지 보기", Toast.LENGTH_SHORT).show()
//                val intent = Intent(applicationContext, ) // 내가 찜한 챌린지로 이동
                return true
            }
//            R.id.favorite -> {
//                Toast.makeText(applicationContext, "찜한 챌린지 보기", Toast.LENGTH_SHORT).show()
//                val intent = Intent(applicationContext, ) // 내가 찜한 챌린지로 이동
//                return true
//            }
            R.id.notification -> {
                Toast.makeText(applicationContext, "알림센터로 이동", Toast.LENGTH_SHORT).show()
//                val intent = Intent(applicationContext, ) // 알림센터로 이동
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        if(manager.findFragmentByTag(tag) == null) fragTransaction.add(R.id.mainLayout, fragment, tag)
        val certification = manager.findFragmentByTag(TAG_CERTIFICATION)
        val search = manager.findFragmentByTag(TAG_SEARCH)
        val challenge = manager.findFragmentByTag(TAG_CHALLENGE)

        if(certification != null) fragTransaction.hide(certification)
        if(search != null) fragTransaction.hide(search)
        if(challenge != null) fragTransaction.hide(challenge)

        if(tag == TAG_CERTIFICATION && certification != null) {
            fragTransaction.show(certification)
            val intent = Intent(this, CertificationActivity::class.java)
            startActivity(intent)
        }
        else if(tag == TAG_SEARCH && search != null) fragTransaction.show(search)
        else if(tag == TAG_CHALLENGE && challenge != null) fragTransaction.show(challenge)

        fragTransaction.commitAllowingStateLoss()

    }
}