package com.kmu.timetocode

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class FavoriteListAdapter(val context: Context, val list: ArrayList<FavoriteListModel>): BaseAdapter() {

    override fun getView(position: Int, convertview: View?, parent: ViewGroup?): View {
        val view: View
        val holder : ViewHolder

        if (convertview == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.favor_list_item,parent,false)
            holder = ViewHolder()

            holder.view_image = view.findViewById(R.id.imageArea)
            holder.view_text1 = view.findViewById(R.id.title)
            holder.view_text2 = view.findViewById(R.id.owner)
            holder.view_text3 = view.findViewById(R.id.tag1)
            holder.view_text4 = view.findViewById(R.id.tag2)

            view.tag = holder
        } else {
            holder = convertview.tag as ViewHolder
            view = convertview
        }
        val item = list[position]
        holder.view_text1?.text = item.title

        return view

    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any = list.get(position)

    override fun getItemId(position: Int): Long = position.toLong()

    private class ViewHolder {
        var view_image : ImageView? = null
        var view_text1: TextView? = null
        var view_text2: TextView? = null
        var view_text3: TextView? = null
        var view_text4: TextView? = null
    }
}