package com.kmu.timetocode.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.kmu.timetocode.R
import com.kmu.timetocode.UploadImgDialog
import com.kmu.timetocode.databinding.FragmentAddChallenge2Binding


class FragmentAddChallenge2 : Fragment() {

    private var _binding: FragmentAddChallenge2Binding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddChallenge2Binding.inflate(inflater, container, false)

        binding.btnSelectBG.setOnClickListener{
            requestPermission()
        }

        binding.btnGoAdd3.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentAddChallenge2_to_fragmentAddChallenge3)
        }

        return binding.root
    }

    private fun requestPermission() {
        TedPermission.create()
            .setPermissionListener(object : PermissionListener {

                //권한이 허용됐을 때
                override fun onPermissionGranted() {
                    UploadImgDialog("이미지 가져오기").show(parentFragmentManager,"takeImgDialog")

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