package com.kmu.timetocode

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import com.kmu.timetocode.databinding.FragmentAddChallenge2Binding
import com.kmu.timetocode.databinding.TakeImgDialogLayoutBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class UploadImgDialog(val title: String) : DialogFragment() {

    private var _binding: TakeImgDialogLayoutBinding? = null

    private val binding get() = _binding!!
    private lateinit var resultLauncher : ActivityResultLauncher<Intent>

    val REQUEST_IMAGE_CAPTURE = 1  //카메라 사진 촬영 요청 코드 *임의로 값 입력
    lateinit var currentPhotoPath : String //문자열 형태의 사진 경로값 (초기값을 null로 시작하고 싶을 때 - lateinti var)
    val REQUEST_IMAGE_PICK = 10

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = TakeImgDialogLayoutBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.uploadImgTitle.text = title

        binding.selectAlbum.setOnClickListener{
            pickImg()
            dismiss()
        }

        binding.selectCam.setOnClickListener{
            takeImg()
            dismiss()
        }

        binding.selectCancel.setOnClickListener{
            dismiss()
        }

        return binding.root
    }

    private fun takeImg() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }

    }

    private fun createImageFile(): File? {
        val timestamp : String  = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date()) // 이미지 파일 이름
        val storeageDir : File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)  // 스트리지 디렉토리 경로

        return File.createTempFile("JPEG_${timestamp}_", ".jpg", storeageDir)
            .apply { currentPhotoPath = absolutePath}
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        val resolver = requireActivity().contentResolver
//
//        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            val imageBitmap = data?.extras?.get("data") as Bitmap
//            imageView.setImageBitmap(imageBitmap)
//        }else if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
//            val currentImageUrl : Uri? = data?.data
//            try{
//                val bitmap = MediaStore.Images.Media.getBitmap(resolver, currentImageUrl)
//                imageView.setImageBitmap(bitmap)
//            }catch (e: Exception){
//                e.printStackTrace()
//            }
//        }
    }


    private fun pickImg() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }






        override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}