package com.kmu.timetocode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

class SearchResult : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        val rootView : View = inflater.inflate(R.layout.fragment_search_result, container, false)

        val list_array = arrayListOf<FavoriteListModel>(
            FavoriteListModel("a","검색결과","생성자","+60명","github"),
            FavoriteListModel("a","챌린지 이름","생성자","+60명","github"),
            FavoriteListModel("a","챌린지 이름","생성자","+60명","github"),
            FavoriteListModel("a","챌린지 이름","생성자","+60명","github"),
            FavoriteListModel("a","챌린지 이름","생성자","+60명","github"),
            FavoriteListModel("a","챌린지 이름","생성자","+60명","github")

        )
        var Adapter = FavoriteListAdapter(requireContext(),list_array)
        rootView?.findViewById<ListView>(R.id.listview_searchresult_fragment)?.adapter = Adapter

        return rootView
    }
}