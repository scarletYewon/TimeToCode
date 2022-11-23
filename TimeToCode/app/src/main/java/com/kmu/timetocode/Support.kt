package com.kmu.timetocode

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment

class Support : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val support = inflater.inflate(R.layout.fragment_support, container, false) as ViewGroup
        val backButton = support.findViewById<ImageButton>(R.id.backCertification)
        val questionButton = support.findViewById<ImageButton>(R.id.questionButton)
        val inquiryButton = support.findViewById<ImageButton>(R.id.inquiryButton)
        backButton.setOnClickListener { (activity as NavActivity?)!!.replaceFragment(MainFragment()) }
        questionButton.setOnClickListener { (activity as NavActivity?)!!.replaceFragment(Question.newInstance()) }
        inquiryButton.setOnClickListener { (activity as NavActivity?)!!.replaceFragment(Inquiry.newInstance()) }
        return support
    }

    companion object {
        fun newInstance(): Support {
            return Support()
        }
    }
}