package com.kmu.timetocode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MainFragment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_main)

        val userName = findViewById<TextView>(R.id.userName)
        val userLevel = findViewById<TextView>(R.id.userLevel)
        val myChallenge = findViewById<Button>(R.id.myChallenge)
        val calendarView = findViewById<CalendarView>(R.id.calenderView)
    }
}