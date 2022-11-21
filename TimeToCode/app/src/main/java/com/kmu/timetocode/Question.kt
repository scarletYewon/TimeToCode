package com.kmu.timetocode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment

class Question : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val question = inflater.inflate(R.layout.fragment_question, container, false) as ViewGroup
        val backButton = question.findViewById<ImageButton>(R.id.backCertification)
        backButton.setOnClickListener { (activity as NavActivity?)!!.replaceFragment(Support.newInstance()) }
        return question
    }

    companion object {
        fun newInstance(): Question {
            return Question()
        }
    }
}