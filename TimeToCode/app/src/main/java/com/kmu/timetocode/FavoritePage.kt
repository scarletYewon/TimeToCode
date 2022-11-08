package com.kmu.timetocode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController

class FavoritePage : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        val rootView = inflater.inflate(R.layout.fragment_favorite_page, container, false)
//        val mActivity = activity as MainActivity
//        val btn_change = rootView.findViewById(R.id.button_change);
//        btn_change.setOnClickListener{
//            activity.changeFragment(2)
//        }
        return rootView
    }
}