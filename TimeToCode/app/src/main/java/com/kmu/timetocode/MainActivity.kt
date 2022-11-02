package com.kmu.timetocode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CalendarView
import com.kmu.timetocode.R
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var toolbar: Toolbar? = null
        var calendarView: CalendarView? = null
        var bottomNavigation: BottomNavigationView? = null

        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        calendarView = findViewById<View>(R.id.calenderView) as CalendarView
        calendarView!!.setOnDateChangeListener { calendarView, i, i1, i2 ->
            Log.i("캘린더", "선택한 날짜는 " + i + "년 " + (i1 + 1) + "월 " + i2 + "일")
        }

        bottomNavigation = findViewById<View>(R.id.bottomNavigation) as BottomNavigationView
        bottomNavigation!!.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.certification -> {
                    Toast.makeText(applicationContext, "인증센터", Toast.LENGTH_SHORT).show()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.search -> {
                    Toast.makeText(applicationContext, "검색", Toast.LENGTH_SHORT).show()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.challenge -> {
                    Toast.makeText(applicationContext, "챌린지", Toast.LENGTH_SHORT).show()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.favorite -> {
                Toast.makeText(applicationContext, "찜한 챌린지 보기", Toast.LENGTH_SHORT).show()
                return super.onOptionsItemSelected(item)
            }
            R.id.notification -> {
                Toast.makeText(applicationContext, "알림센터로 이동", Toast.LENGTH_SHORT).show()
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}