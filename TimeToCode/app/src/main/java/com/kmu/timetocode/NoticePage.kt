package com.kmu.timetocode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

class NoticePage : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        val rootView : View = inflater.inflate(R.layout.fragment_notice_page, container, false)
//        val mActivity = activity as MainActivity
//        val btn_change = rootView.findViewById(R.id.button_change);
//        btn_change.setOnClickListener{
//            activity.changeFragment(2)
//        }

        val list_array = arrayListOf<NoticeListModel>(
            NoticeListModel("a","챌린지 이름"),
            NoticeListModel("a","챌린지 이름"),
            NoticeListModel("a","챌린지 이름"),
            NoticeListModel("a","챌린지 이름"),
            NoticeListModel("a","챌린지 이름"),

        )
        var Adapter = NoticeListAdapter(requireContext(),list_array)
        rootView?.findViewById<ListView>(R.id.listview_notice_fragment)?.adapter = Adapter

        return rootView
    }
}