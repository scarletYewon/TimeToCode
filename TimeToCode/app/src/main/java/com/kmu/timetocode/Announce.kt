package com.kmu.timetocode

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment

class Announce : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val announce = inflater.inflate(R.layout.fragment_announce, container, false) as ViewGroup
        val backButton = announce.findViewById<ImageButton>(R.id.backCertification)
        val AnnouncePageButton = announce.findViewById<ImageButton>(R.id.announcePageButton)
        backButton.setOnClickListener { (activity as NavActivity?)!!.replaceFragment(MainFragment.newInstance()) }
        AnnouncePageButton.setOnClickListener {
            (activity as NavActivity?)!!.replaceFragment(
                AnnouncePage.newInstance()
            )
        }
        return announce
    }

    companion object {
        @JvmStatic
        fun newInstance(): Announce {
            return Announce()
        }
    }
}