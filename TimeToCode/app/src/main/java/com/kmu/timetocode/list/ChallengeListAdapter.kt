package com.kmu.timetocode.list

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kmu.timetocode.databinding.ChallengeListItemBinding
import com.kmu.timetocode.detail.ChallengeDetail

class ChallengeListAdapter(val context: Context, private val challengeListArray: ArrayList<ChallengeListModel>): RecyclerView.Adapter<ChallengeListAdapter.ListViewHolder>() {

    interface OnListItemLongSelectedInterface {
        fun onItemLongSelected(v: View?, position: Int)
    }

    interface OnListItemSelectedInterface {
        fun onItemSelected(v: View?, position: Int)
    }

    private val mListener: OnListItemSelectedInterface? = null
    private val mLongListener: OnListItemLongSelectedInterface? = null


    class ListViewHolder(val binding: ChallengeListItemBinding) : RecyclerView.ViewHolder(binding.root) {
//        TODO: img 추가
        fun setList(item: ChallengeListModel){

            binding.textChallegeNameInList.text = item.title
            binding.textMadeUserInList.text = item.owner
            binding.challengePrtcp.text = "+"+item.prtcp+"명"
            binding.challengeTag1.text = item.tag1
            binding.challengeTag2.text = item.tag2

//    binding.root.setOnClickListener {
//        itemClickedListener(item)
//    }

//        val pos = adapterPosition
//        if(pos!= RecyclerView.NO_POSITION)
//        {
//            itemView.setOnClickListener {
//                listener?.onItemClick(itemView,item,pos)
//            }
//        }
        }


    }

    // Define the listener interface
    interface OnItemClickListener {
        fun onItemClick(itemView: View?, position: Int)
    }

    private var listener : OnItemClickListener? = null
    // Define listener member variable
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(ChallengeListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }
    override fun getItemCount(): Int {
        return challengeListArray.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        holder.setList(challengeListArray.get(position))
        holder.binding.root.setOnClickListener {
            val intent = Intent(holder.binding.root?.context, ChallengeDetail::class.java)
            intent.putExtra("content","원하는 데이터를 보냅니다. ")
            intent.putExtra("no",111)
            Log.i("adapter","여기는 dapter: ${holder.binding.root.id}")
            ContextCompat.startActivity(holder.binding.root.context, intent,null)
        }
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