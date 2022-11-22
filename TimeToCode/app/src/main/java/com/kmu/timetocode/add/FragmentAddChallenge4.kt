package com.kmu.timetocode.add

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
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
import java.util.*


class FragmentAddChallenge4 : Fragment() {

    private var _binding: FragmentAddChallenge4Binding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddChallenge4Binding.inflate(inflater, container, false)

        binding.btnSelectExplainImg.setOnClickListener {
            requestPermission()
        }

    // TODO: 챌린지 생성 후 어디로 가야하는지 결정

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().supportFragmentManager.setFragmentResultListener("takeRequestKey",this) { requestKey, bundle ->
            bundle.getString("takeBundleKey")?.let {
                val imageBytes = Base64.getDecoder().decode(it)
                val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                binding.explainImgView.setImageBitmap(image)
                Log.i("take","takeImg를 통해 fragment")
            }
        }

        requireActivity().supportFragmentManager.setFragmentResultListener("pickRequestKey",this) { requestKey, bundle ->
            bundle.getString("pickBundleKey")?.let {
                val image = Uri.parse(it)
                binding.explainImgView.setImageURI(image)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}