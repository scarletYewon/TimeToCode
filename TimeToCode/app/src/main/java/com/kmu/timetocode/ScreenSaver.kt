package com.kmu.timetocode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class ScreenSaver : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        val rootView : View = inflater.inflate(R.layout.screen_saver, container, false)
        val start = rootView.findViewById<ImageView>(R.id.start)

        start?.setOnClickListener { (activity as NavActivity?)!!.replaceFragment(MainFragment()) }
        return rootView
    }
}