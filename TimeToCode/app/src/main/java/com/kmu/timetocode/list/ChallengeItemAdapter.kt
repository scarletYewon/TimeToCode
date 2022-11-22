package com.kmu.timetocode.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kmu.timetocode.databinding.ChallengeNewItemBinding

class ChallengeItemAdapter(val context: Context, val challengeItemArray: ArrayList<ChallengeItemModel>): RecyclerView.Adapter<ChallengeItemAdapter.ItemViewHolder>()  {

    class ItemViewHolder(val binding: ChallengeNewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setList(item: ChallengeItemModel){
            binding.textChallengeNameInNew.text = item.title
            binding.firstTag.text = item.tag

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ChallengeNewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }
    override fun getItemCount(): Int {
        return challengeItemArray.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.setList(challengeItemArray.get(position))
    }

}