package com.kmu.timetocode.certicenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kmu.timetocode.R

class Certifbox : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        val rootView : View = inflater.inflate(R.layout.fragment_certifbox, container, false)
        return rootView
    }
}