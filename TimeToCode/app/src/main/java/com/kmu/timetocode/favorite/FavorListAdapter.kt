package com.kmu.timetocode.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.kmu.timetocode.R
import com.kmu.timetocode.cdp.CdpListModel

class FavorListAdapter(val context: Context, val list: ArrayList<FavorListModel>): BaseAdapter() {
    override fun getView(position: Int, convertview: View?, parent: ViewGroup?): View {
        val view: View
        val holder : ViewHolder

        if (convertview == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.favor_list_item,parent,false)
            holder = ViewHolder()

            holder.view_image = view.findViewById(R.id.imageArea)
            holder.view_title = view.findViewById(R.id.title)
            holder.view_owner = view.findViewById(R.id.owner)

            view.tag = holder
        } else {
            holder = convertview.tag as ViewHolder
            view = convertview
        }
        val item = list[position]
        holder.view_title?.text = item.title
        holder.view_owner?.text = item.owner

        return view

    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any = list.get(position)

    override fun getItemId(position: Int): Long = position.toLong()

    private class ViewHolder {
        var view_image : ImageView? = null
        var view_title: TextView? = null
        var view_owner: TextView? = null
    }
}