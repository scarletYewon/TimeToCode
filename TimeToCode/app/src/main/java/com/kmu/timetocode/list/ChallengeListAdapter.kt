package com.kmu.timetocode.list

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.kmu.timetocode.databinding.ChallengeListItemBinding

class ChallengeListAdapter(val context: Context, private val challengeListArray: ArrayList<ChallengeListModel>): RecyclerView.Adapter<ChallengeListAdapter.ListViewHolder>() {

    class ListViewHolder(val binding: ChallengeListItemBinding) : RecyclerView.ViewHolder(binding.root) {
//        TODO: img 추가
        fun setList(item: ChallengeListModel){

            binding.textChallegeNameInList.text = item.title
            binding.textMadeUserInList.text = item.owner
            binding.challengePrtcp.text = "+"+item.prtcp+"명"
            binding.challengeTag1.text = item.tag1
            binding.challengeTag2.text = item.tag2

        val pos = adapterPosition
        if(pos!= RecyclerView.NO_POSITION)
        {
            itemView.setOnClickListener {
                listener?.onItemClick(itemView,item,pos)
            }
        }
        }

        interface OnItemClickListener{
            fun onItemClick(v: View, data: ChallengeListModel, pos : Int)
        }
        private var listener : OnItemClickListener? = null
        fun setOnItemClickListener(listener : OnItemClickListener) {
            this.listener = listener
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(ChallengeListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }
    override fun getItemCount(): Int {
        return challengeListArray.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        holder.setList(challengeListArray.get(position))
    }

    override fun onViewDetachedFromWindow(holder: ListViewHolder) {
        super.onViewDetachedFromWindow(holder)
        Log.e("onViewDetachedFromWindow","${holder.adapterPosition}")
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        Log.e("onAttachedToRecyclerView","onAttachedToRecyclerView")

    }

}