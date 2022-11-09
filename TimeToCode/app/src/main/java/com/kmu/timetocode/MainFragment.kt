package com.kmu.timetocode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        val rootView = inflater.inflate(R.layout.fragment_main, container, false)

        val userName = rootView?.findViewById<TextView>(R.id.userName)
        val userLevel = rootView?.findViewById<TextView>(R.id.userLevel)
        val myChallenge = rootView?.findViewById<TextView>(R.id.myChallenge)
        val calendarView = rootView?.findViewById<TextView>(R.id.calenderView)

        return rootView
    }
}