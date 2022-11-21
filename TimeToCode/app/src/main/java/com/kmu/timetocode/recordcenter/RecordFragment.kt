package com.kmu.timetocode.recordcenter

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.kmu.timetocode.MainFragment
import com.kmu.timetocode.NavActivity
import com.kmu.timetocode.R
import com.kmu.timetocode.certicenter.CertificationFragment

class RecordFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        val rootView = inflater.inflate(R.layout.fragment_record, container, false)
        val gridView = view?.findViewById<GridView>(R.id.recordList)

        val backRecord = rootView?.findViewById<ImageButton>(R.id.backRecord)
        backRecord?.setOnClickListener { (activity as NavActivity?)!!. replaceFragment(CertificationFragment()) }

        var recordList = ArrayList<Record>() // 기록 전체 목록을 담고 있는 리스트

        var adapter = GridViewAdapter(requireContext(), recordList)

        adapter!!.addItem(Record(1, "제목", R.drawable.ttcwhite))
        adapter!!.addItem(Record(2, "제목", R.drawable.ttcwhite))
        adapter!!.addItem(Record(3, "제목", R.drawable.ttcwhite))
        adapter!!.addItem(Record(4, "제목", R.drawable.ttcwhite))

        gridView?.setAdapter(adapter)
        rootView?.findViewById<GridView>(R.id.recordList)?.adapter = adapter
        return rootView
    }

    /* 그리드뷰 어댑터 */
    inner class GridViewAdapter(val context: Context, val list: ArrayList<Record>) : BaseAdapter() {
        override fun getCount(): Int { return list.size }

        fun addItem(item: Record) { list.add(item) }
        override fun getItem(position: Int): Any { return list[position] }
        override fun getItemId(position: Int): Long { return position.toLong() }

        override fun getView(position: Int, convertview: View?, parent: ViewGroup?): View {
            val view: View
            val holder: ViewHolder

            if (convertview == null) {
                view = LayoutInflater.from(parent?.context).inflate(R.layout.gridview_record, parent, false)
                holder = ViewHolder()

                holder.rc_number = view.findViewById(R.id.recordNumber)
                holder.rc_title = view.findViewById(R.id.recordTitle)
                holder.rc_image = view.findViewById(R.id.recordImage)
                holder.back_record = view.findViewById(R.id.backRecord)

                holder.rc_number?.text = list[position].num.toString()
                holder.rc_title?.text = list[position].title
                list[position].resId?.let {holder.rc_image?.setImageResource(it)}

                holder.back_record?.setOnClickListener { (activity as NavActivity?)!!.replaceFragment(MainFragment()) }
                view.tag = holder
            } else {
                holder = convertview.tag as ViewHolder
                view = convertview
            }

            val recordItem = list[position]
            val resourceId = context.resources.getIdentifier(recordItem.resId.toString(), "drawable", context.packageName)
            holder.rc_image?.setImageResource(resourceId)

            return view //뷰 객체 반환
        }
        private inner class ViewHolder {
            var rc_number: TextView? = null
            var rc_title: TextView? = null
            var rc_image: ImageButton? = null
            var back_record: Button? = null
        }
    }
}