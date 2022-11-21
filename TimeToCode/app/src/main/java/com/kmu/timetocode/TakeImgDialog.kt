package com.kmu.timetocode

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.kmu.timetocode.databinding.FragmentAddChallenge2Binding
import com.kmu.timetocode.databinding.TakeImgDialogLayoutBinding

class TakeImgDialog(val title: String) : DialogFragment() {

    private var _binding: TakeImgDialogLayoutBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = TakeImgDialogLayoutBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.takeImgTitle.text = title

        binding.selectAlbum.setOnClickListener{
            dismiss()
        }

        binding.selectCam.setOnClickListener{
            dismiss()
        }

        return binding.root
    }

        private fun selectAlbum(){
            var intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.type = "image/*"

        }


        override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}