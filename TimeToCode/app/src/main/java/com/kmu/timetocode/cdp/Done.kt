package com.kmu.timetocode.cdp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.kmu.timetocode.R
import com.kmu.timetocode.favorite.FavoriteListAdapter
import com.kmu.timetocode.favorite.FavoriteListModel

class Done : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        val rootView : View = inflater.inflate(R.layout.fragment_done, container, false)
//        val mActivity = activity as MainActivity
//        val btn_change = rootView.findViewById(R.id.button_change);
//        btn_change.setOnClickListener{
//            activity.changeFragment(2)
//        }

        val list_array = arrayListOf<FavoriteListModel>(
            FavoriteListModel("a","완료된","생성자","+60명","github"),
            FavoriteListModel("a","챌린지 이름","생성자","+60명","github"),
            FavoriteListModel("a","챌린지 이름","생성자","+60명","github"),
            FavoriteListModel("a","챌린지 이름","생성자","+60명","github"),
            FavoriteListModel("a","챌린지 이름","생성자","+60명","github"),
            FavoriteListModel("a","챌린지 이름","생성자","+60명","github")

        )
        var Adapter = FavoriteListAdapter(requireContext(),list_array)
        rootView?.findViewById<ListView>(R.id.listview_done_fragment)?.adapter = Adapter

        return rootView
    }
}