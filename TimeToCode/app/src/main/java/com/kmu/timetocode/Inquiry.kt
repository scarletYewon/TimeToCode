package com.kmu.timetocode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment

class Inquiry : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inquiry = inflater.inflate(R.layout.fragment_inquiry, container, false) as ViewGroup
        val backButton = inquiry.findViewById<ImageButton>(R.id.backCertification)
        backButton.setOnClickListener { (activity as NavActivity?)!!.replaceFragment(Support.newInstance()) }
        return inquiry
    }

    companion object {
        fun newInstance(): Inquiry {
            return Inquiry()
        }
    }
}