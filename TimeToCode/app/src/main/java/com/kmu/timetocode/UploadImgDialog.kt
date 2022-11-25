package com.kmu.timetocode

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.kmu.timetocode.databinding.TakeImgDialogLayoutBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class UploadImgDialog(val title: String, val dialogType: Int) : DialogFragment() {

    private var _binding: TakeImgDialogLayoutBinding? = null

    private val binding get() = _binding!!

    val REQUEST_IMAGE_CAPTURE = 1  //카메라 사진 촬영 요청 코드
    lateinit var currentPhotoPath : String //문자열 형태의 사진 경로값
    val REQUEST_IMAGE_PICK = 10

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = TakeImgDialogLayoutBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.uploadImgTitle.text = title

        when(dialogType) {
            1-> binding.selectCam.visibility = View.GONE
        }


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.selectAlbum.setOnClickListener{
            pickImg()
        }

        binding.selectCam.setOnClickListener{
            takeImg()

        }

        binding.selectCancel.setOnClickListener{
            dismiss()
        }
    }

    private fun takeImg() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            Log.i("test","실행1")

            if (takePictureIntent.resolveActivity(requireContext().packageManager) != null) {
                // 촬영한 사진 파일로 만들기
                val photoFile: File? =
                    try {
                        createImageFile()
                    } catch (ex: IOException) {
                        Log.d("TAG", "사진파일 생성 중 에러 발생")
                        null
                    }

                // 그림파일을 성공적으로 만들었다면 onActivityForResult로 보내기
                photoFile?.also {
                    Log.i("also","여기는 also")
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(), "com.kmu.timetocode.fileprovider", it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    Log.i("also","여기는 also2")
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    Log.i("also","여기는 also3")

                }
            }
        }
        }


    private fun createImageFile(): File? {
        val timestamp : String  = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date()) // 이미지 파일 이름
        val storeageDir : File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)  // 스트리지 디렉토리 경로
        Log.i("test","여기는 create")

        return File.createTempFile("JPEG_${timestamp}_", ".jpg", storeageDir)
            .apply { currentPhotoPath = absolutePath}

    }

    private fun pickImg() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val resolver = requireActivity().contentResolver
//        Log.i("test","실행??")
//
//
//        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            val imageString = data?.extras?.get("data") as String
//            val bundle = bundleOf("bundleKey" to imageString)
//            Log.v("test","실행2")
//
//            setFragmentResult("requestKey", bundle)
//
//        }else if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
//            val currentImageUrl : Uri? = data?.data
//            try{
//                val bitmap = MediaStore.Images.Media.getBitmap(resolver, currentImageUrl)
////                imageView.setImageBitmap(bitmap)
//            }catch (e: Exception){
//                e.printStackTrace()
//            }
//        }
        when (requestCode){
            1 -> {
                if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){

                    // 카메라로부터 받은 데이터가 있을경우에만
                    val file = File(currentPhotoPath)
                    if (Build.VERSION.SDK_INT < 28) {
                        val bitmap = MediaStore.Images.Media
                            .getBitmap(resolver, Uri.fromFile(file))  //Deprecated
                        Log.i("file","여기는 getbitmap")
                        Toast.makeText(activity, "여기는 getbitmap", Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                    else{
                        val test = Uri.fromFile(file)
                        val decode = ImageDecoder.createSource(resolver,
                            Uri.fromFile(file))
                        val bitmap = ImageDecoder.decodeBitmap(decode)
                        Log.i("file","여기는 getbitmap2")
//                        val sendData = bitmapToString(bitmap)
                        val sendData = test.toString()
                        val bundle = bundleOf("takeBundleKey" to sendData)
                        requireActivity().supportFragmentManager.setFragmentResult("takeRequestKey", bundle)
                        dismiss()
                    }
                }
            }
            10->{
                if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_PICK){
                    val currentImg : Uri? = data?.data
                    val imgData = currentImg.toString()
                    Log.i("a","이것은 선택한 이미지: ${imgData}")
                    val bundle = bundleOf("pickBundleKey" to imgData)
                    requireActivity().supportFragmentManager.setFragmentResult("pickRequestKey", bundle)
                    dismiss()

                }
            }
        }
    }


    fun bitmapToString(bitmap: Bitmap): String {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

        val bytes = stream.toByteArray()

        return Base64.getEncoder().encodeToString(bytes)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}