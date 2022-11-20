package com.kmu.timetocode

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.coroutines.NonDisposableHandle.parent

class CertificationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        val rootView = inflater.inflate(R.layout.fragment_certification, container, false)
        val listView = view?.findViewById<ListView>(R.id.list)

        val backCertification = rootView?.findViewById<ImageButton>(R.id.backCertification)
        backCertification?.setOnClickListener { (activity as NavActivity?)!!.replaceFragment(MainFragment()) }

        var challengeList = ArrayList<Challenge>() // 챌린지 전체 목록을 담고 있는 리스트

        var adapter = ListViewAdapter(requireContext(), challengeList)
        // challenge 추가하는 부분
        adapter!!.addItem(Challenge(1, "Github Commit", "설명1", "만든이", R.drawable.ttcwhite))
        adapter!!.addItem(Challenge(2, "BOJ algorithm", "설명2", "만든사람", R.drawable.ttcwhite))

        listView?.setAdapter(adapter)
        rootView?.findViewById<ListView>(R.id.list)?.adapter = adapter
        return rootView
    }

    inner class ListViewAdapter(val context: Context, val list: ArrayList<Challenge>): BaseAdapter() {
        override fun getCount(): Int { return list.size }

        fun addItem(item: Challenge) {
            list.add(item)
            val holder = ViewHolder()
            holder.ch_number?.text = item.num.toString()
            holder.ch_title?.text = item.title
            holder.ch_maker?.text = item.maker
            holder.ch_explain?.text = item.explain
            item.resId?.let { holder.ch_image?.setImageResource(it) }
        }
        override fun getItem(position: Int): Any { return list[position] }
        override fun getItemId(position: Int): Long { return position.toLong() }

        override fun getView(position: Int, convertview: View?, parent: ViewGroup?): View {
            val view: View
            val holder: ViewHolder

            if (convertview == null) {
                view = LayoutInflater.from(parent?.context).inflate(R.layout.listview_challenge, parent, false)
                holder = ViewHolder()

                holder.ch_number = view.findViewById(R.id.challengeNum)
                holder.ch_title = view.findViewById(R.id.challengeTitle)
                holder.ch_explain = view.findViewById(R.id.challengeExplain)
                holder.ch_maker = view.findViewById(R.id.challengeMaker)
                holder.ch_image = view.findViewById(R.id.challengeImage)

                view.tag = holder
            } else {
                holder = convertview.tag as ViewHolder
                view = convertview
            }

            val challengeItem = list[position] // 챌린지 list 안의 챌린지 한개
            val resourceId = context.resources.getIdentifier(challengeItem.resId.toString(), "drawable", context.packageName)
            holder.ch_image?.setImageResource(resourceId)
            challengeItem.resId?.let { holder.ch_image?.setImageResource(it) };

            return view //뷰 객체 반환
        }
        private inner class ViewHolder {
            var ch_number: TextView? = null
            var ch_title: TextView? = null
            var ch_explain: TextView? = null
            var ch_maker: TextView? = null
            var ch_image: ImageButton? = null
        }
    }

}