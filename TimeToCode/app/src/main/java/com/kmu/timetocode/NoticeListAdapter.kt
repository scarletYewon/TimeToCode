package com.kmu.timetocode

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class NoticeListAdapter(val context: Context, val list: ArrayList<NoticeListModel>): BaseAdapter() {

    override fun getView(position: Int, convertview: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertview == null) {
            view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.notice_list_item, parent, false)
            holder = ViewHolder()

            holder.view_image = view.findViewById(R.id.imageArea)
            holder.view_content = view.findViewById(R.id.content)

            view.tag = holder
        } else {
            holder = convertview.tag as ViewHolder
            view = convertview
        }
        val item = list[position]
        holder.view_content?.text = item.content

        return view

    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any = list.get(position)

    override fun getItemId(position: Int): Long = position.toLong()

    private class ViewHolder {
        var view_image: ImageView? = null
        var view_content: TextView? = null
    }
}
