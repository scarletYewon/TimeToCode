package com.kmu.timetocode.add

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.kmu.timetocode.R
import com.kmu.timetocode.UploadImgDialog
import com.kmu.timetocode.databinding.FragmentAddChallenge2Binding


class FragmentAddChallenge2 : Fragment() {

    private val model: AddChallengeViewModel by activityViewModels()

    private var _binding: FragmentAddChallenge2Binding? = null
    private val binding get() = _binding!!

    private var imgFlag = false

    lateinit var selectImg : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddChallenge2Binding.inflate(inflater, container, false)

        binding.btnUploadBackGround.setOnClickListener{
            binding.uploadBackGroundImgView.setImageResource(R.drawable.gray_img)
            binding.uploadBackGroundImgView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            imgFlag = false
            requestPermission()
        }
        binding.btnUploadCancel.setOnClickListener{
            binding.uploadBackGroundImgView.setImageResource(R.drawable.gray_img)
            binding.uploadBackGroundImgView.scaleType = ImageView.ScaleType.CENTER_INSIDE

            imgFlag = false
        }

        binding.btnGoAdd3.setOnClickListener{
            model.addData2(selectImg)
            findNavController().navigate(R.id.action_fragmentAddChallenge2_to_fragmentAddChallenge3)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 다음버튼 비활성화
        binding.btnGoAdd3.isEnabled = false

        requireActivity().supportFragmentManager.setFragmentResultListener("pickRequestKey",this) { requestKey, bundle ->
            bundle.getString("pickBundleKey")?.let {
                selectImg = it
                val image = Uri.parse(it)
                binding.uploadBackGroundImgView.setImageURI(image)
                binding.uploadBackGroundImgView.scaleType = ImageView.ScaleType.CENTER_CROP
                imgFlag = true
                flagCheck()

                Log.i("take","pickImg를 통해 fragment")
            }
        }
    }

    private fun requestPermission() {
        TedPermission.create()
            .setPermissionListener(object : PermissionListener {

                //권한이 허용됐을 때
                override fun onPermissionGranted() {
                    UploadImgDialog("이미지 가져오기",1).show(parentFragmentManager,"takeImgDialog")
                }

                //권한이 거부됐을 때
                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(activity, "카메라 기능 실행", Toast.LENGTH_SHORT).show()
                }
            })
            .setDeniedMessage("카메라 권한을 허용해주세요.")// 권한이 없을 때
            .setPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)// 얻으려는 권한
            .check()
    }

    private fun flagCheck() {
        binding.btnGoAdd3.isEnabled = imgFlag
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}