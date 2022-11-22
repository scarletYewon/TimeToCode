package com.kmu.timetocode.add

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.kmu.timetocode.UploadImgDialog
import com.kmu.timetocode.databinding.FragmentAddChallenge3Binding
import com.kmu.timetocode.databinding.FragmentAddChallenge4Binding
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class FragmentAddChallenge4 : Fragment() {

    private var _binding: FragmentAddChallenge4Binding? = null

    private val binding get() = _binding!!

    lateinit var image : Bitmap

    private var imgFromCam : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddChallenge4Binding.inflate(inflater, container, false)

        binding.btnSelectExplainImg.setOnClickListener {
            requestPermission()
        }

    // TODO: 챌린지 생성 후 어디로 가야하는지 결정, 완료 버튼 클릭 시 기입 정보 보내기, 촬영해서 가져온 이미지 갤러리에 저장

        binding.btnAddFinish.setOnClickListener{
            if (imgFromCam){
                savePhoto(image)
            }

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().supportFragmentManager.setFragmentResultListener("takeRequestKey",this) { requestKey, bundle ->
            bundle.getString("takeBundleKey")?.let {
                val imageBytes = Base64.getDecoder().decode(it)
                image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                binding.explainImgView.setImageBitmap(image)
                imgFromCam = true
                Log.i("take","takeImg를 통해 fragment")
            }
        }

        requireActivity().supportFragmentManager.setFragmentResultListener("pickRequestKey",this) { requestKey, bundle ->
            bundle.getString("pickBundleKey")?.let {
                var pickImage = Uri.parse(it)
                binding.explainImgView.setImageURI(pickImage)
                imgFromCam= false
                Log.i("take","pickImg를 통해 fragment")
            }
        }
    }

    private fun requestPermission() {
        TedPermission.create()
            .setPermissionListener(object : PermissionListener {

                //권한이 허용됐을 때
                override fun onPermissionGranted() {
                    UploadImgDialog("인증방법 설명 이미지 가져오기",2).show(parentFragmentManager,"takeImgDialog")
                }
                //권한이 거부됐을 때
                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(activity, "카메라 기능 실행", Toast.LENGTH_SHORT).show()
                }
            })
            .setDeniedMessage("카메라 권한을 허용해주세요.")// 권한이 없을 때 띄워주는 Dialog Message
            .setPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.CAMERA)// 얻으려는 권한
            .check()
    }

    private fun savePhoto(bitmap: Bitmap) {
        //사진 폴더에 저장하기 위한 경로 선언
        val folderPath = Environment.getExternalStorageDirectory().absolutePath + "/Pictures/"
        val timestamp : String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val fileName = "${timestamp}.jpeg"
        val folder = File(folderPath)
        if(!folder.isDirectory){//해당 경로에 폴더가 존재하지
            folder.mkdir() // make directory의 줄임말로 해당경로에 폴더 자동으로
        }
        //실제적인 저장 처리
        val out = FileOutputStream(folderPath + fileName)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        Toast.makeText(activity,"사진이 앨범에 저장되었습니다.",Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}