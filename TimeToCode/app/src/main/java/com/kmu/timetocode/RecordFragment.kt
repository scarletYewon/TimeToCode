package com.kmu.timetocode

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment

class RecordFragment : Fragment() {
    var recordList = ArrayList<Record>() // 기록 전체 목록을 담고 있는 리스트
    var adapter: GridViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        val rootView = inflater.inflate(R.layout.fragment_record, container, false)

        val gridView = view?.findViewById<GridView>(R.id.recordList)

        adapter = GridViewAdapter(this, gridView)
        adapter!!.addItem(Record(1, "제목", R.drawable.ic_launcher_background))
        adapter!!.addItem(Record(2, "제목", R.drawable.ic_launcher_background))
        adapter!!.addItem(Record(3, "제목", R.drawable.ic_launcher_background))
        adapter!!.addItem(Record(4, "제목", R.drawable.ic_launcher_background))

        gridView?.setAdapter(adapter)

        return rootView
    }

    /* 그리드뷰 어댑터 */
    inner class GridViewAdapter(val context: Context, gridView: GridView?) : BaseAdapter() {
        override fun getCount(): Int { return recordList.size }

        fun addItem(item: Record) { recordList.add(item) }
        override fun getItem(position: Int): Any { return recordList[position] }
        override fun getItemId(position: Int): Long { return position.toLong() }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View = LayoutInflater.from(context).inflate(R.layout.gridview_record, null)

            val rc_num = view.findViewById<TextView>(R.id.recordNumber)
            val rc_title = view.findViewById<TextView>(R.id.recordTitle)
            val rc_image = view.findViewById<ImageButton>(R.id.recordImage)

            val recordItem = recordList[position]
            val resourceId = context.resources.getIdentifier(recordItem.resId.toString(), "drawable", context.packageName)
            rc_image.setImageResource(resourceId)

            rc_num.text = recordItem.num.toString()
            rc_title.text = recordItem.title
            recordItem.resId?.let { rc_image.setImageResource(it) };

            return view //뷰 객체 반환
        }
    }
}