package com.kmu.timetocode.cdp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.kmu.timetocode.R

class CdpListAdapter(val context: Context, val list: ArrayList<CdpListModel>): BaseAdapter() {

    override fun getView(position: Int, convertview: View?, parent: ViewGroup?): View {
        val view: View
        val holder : ViewHolder

        if (convertview == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.cdp_list_item,parent,false)
            holder = ViewHolder()

            holder.view_image = view.findViewById(R.id.imageArea)
            holder.view_title = view.findViewById(R.id.title)
            holder.view_owner = view.findViewById(R.id.owner)
            holder.view_count = view.findViewById(R.id.countUser)
            holder.view_tag1 = view.findViewById(R.id.tag1)
            holder.view_tag2 = view.findViewById(R.id.tag2)

            view.tag = holder
            val challengeItem = list[position] // 챌린지 list 안의 챌린지 한개

            val fullName = challengeItem.title
            holder.view_title?.text = fullName?.split("%")?.get(0)
            holder.view_owner?.text = challengeItem.owner
            holder.view_count?.text = "+" + challengeItem.countUser.toString() + "명"
            holder.view_tag1?.text = "#" + challengeItem.tag1
            holder.view_tag2?.text = "#" + challengeItem.tag2
            Log.d("test", "UserImages_" + fullName)

            val storage: FirebaseStorage = FirebaseStorage.getInstance("gs://timetocode-13747.appspot.com/")
            val fileExt = arrayOf(".jpeg", ".jpg", "")
            for(i in fileExt)
                storage.getReference().child("UserImages_" + fullName + i).downloadUrl
                    .addOnSuccessListener { uri ->
                        Glide.with(context).load(uri.toString().toUri()).into(holder.view_image!!)
                    }
        } else {
            holder = convertview.tag as ViewHolder
            view = convertview
        }

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
        var view_count: TextView? = null
        var view_tag1: TextView? = null
        var view_tag2: TextView? = null
    }
}