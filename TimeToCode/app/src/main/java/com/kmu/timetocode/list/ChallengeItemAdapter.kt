package com.kmu.timetocode.list

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kmu.timetocode.databinding.ChallengeNewItemBinding
import com.kmu.timetocode.detail.ChallengeDetail

class ChallengeItemAdapter(val context: Context, val challengeItemArray: ArrayList<ChallengeItemModel>): RecyclerView.Adapter<ChallengeItemAdapter.ItemViewHolder>()  {

    class ItemViewHolder(val binding: ChallengeNewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setList(item: ChallengeItemModel){
            binding.itemImg.setImageURI(item.image.toUri())
            binding.textChallengeNameInNew.text = item.title
            binding.firstTag.text = "#"+item.tag
            val tag2 = item.tag2
            val whoMade = item.whoMade

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ChallengeNewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }
    override fun getItemCount(): Int {
        return challengeItemArray.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

//        holder.setList(challengeItemArray.get(position))

        val currentItem = challengeItemArray[position]
        // 사진 적용
        Glide.with(context)
            .load(currentItem.image)
            .into(holder.binding.itemImg)
        Log.d("FirebaseImageItem", currentItem.image.toString())
        holder.binding.textChallengeNameInNew.text = currentItem.title
        holder.binding.firstTag.text = "#"+currentItem.tag


        val tag2 = currentItem.tag2
        val whoMade = currentItem.whoMade

        holder.binding.root.setOnClickListener {
            val clickTitleInNew: String = holder.binding.textChallengeNameInNew.text.toString().trim()
            val clickTag1InList:String = holder.binding.firstTag.text.toString().trim()

            val clickChallengeInNew: String = clickTitleInNew+"%25"+clickTag1InList+"%25"+tag2
            val intent = Intent(holder.binding.root?.context, ChallengeDetail::class.java)
            intent.putExtra("clickChallengeInList","${clickChallengeInNew} ")
            intent.putExtra("whoMade","${whoMade}")
            Log.i("adapter","여기는 dapter: ${holder.binding.root.id}")
            ContextCompat.startActivity(holder.binding.root.context, intent,null)
        }
    }

}