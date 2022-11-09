package com.kmu.timetocode

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class CertificationFragment : Fragment() {
    var challengeList = ArrayList<Challenge>() // 챌린지 전체 목록을 담고 있는 리스트
    var adapter: ListViewAdapter? = null

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        val rootView = inflater.inflate(R.layout.fragment_certification, container, false)

        val listView = view?.findViewById<ListView>(R.id.list) // 레이아웃 내에 챌린지 목록을 담고 있는 리스트뷰

        adapter = ListViewAdapter(requireContext(), listView)

        // challenge 추가하는 부분
        adapter!!.addItem(Challenge(1, "Github Commit", "설명1", "만든이", R.drawable.ic_launcher_background))
        adapter!!.addItem(Challenge(2, "BOJ algorithm", "설명2", "만든사람", R.drawable.ic_launcher_background))

        listView?.setAdapter(adapter);

        return rootView
    }

    inner class ListViewAdapter(val context: Context, listview: ListView?): BaseAdapter() {
        override fun getCount(): Int { return challengeList.size }

        fun addItem(item: Challenge) { challengeList.add(item) }
        override fun getItem(position: Int): Any { return challengeList[position] }
        override fun getItemId(position: Int): Long { return position.toLong() }

        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val view: View = LayoutInflater.from(context).inflate(R.layout.listview_challenge, null)

            val ch_number = view.findViewById<TextView>(R.id.challengeNum)
            val ch_title = view.findViewById<TextView>(R.id.challengeTitle)
            val ch_explain = view.findViewById<TextView>(R.id.challengeExplain)
            val ch_maker = view.findViewById<TextView>(R.id.challengeMaker)
            val ch_image = view.findViewById<ImageButton>(R.id.challengeImage)

            val challengeItem = challengeList[position] // 챌린지 list 안의 챌린지 한개
            val resourceId = context.resources.getIdentifier(challengeItem.resId.toString(), "drawable", context.packageName)
            ch_image.setImageResource(resourceId)

            ch_number.text = challengeItem.num.toString()
            ch_title.text = challengeItem.title
            ch_explain.text = challengeItem.explain
            ch_maker.text = challengeItem.maker
            challengeItem.resId?.let { ch_image.setImageResource(it) };

            return view //뷰 객체 반환
        }
    }

}