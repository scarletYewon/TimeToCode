package com.kmu.timetocode.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kmu.timetocode.databinding.ChallengeListItemBinding

class ChallengeListAdapter(val context: Context, val challengeListArray: ArrayList<ChallengeListModel>): RecyclerView.Adapter<ChallengeListAdapter.ListViewHolder>() {

    class ListViewHolder(val binding: ChallengeListItemBinding) : RecyclerView.ViewHolder(binding.root) {
//        TODO: img 추가
        fun setList(item: ChallengeListModel){
            binding.textChallegeNameInList.text = item.title
            binding.textMadeUserInList.text = item.owner
            binding.challengePrtcp.text = "+"+item.prtcp+"명"
            binding.challengeTag1.text = item.tag1
            binding.challengeTag2.text = item.tag2

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

}