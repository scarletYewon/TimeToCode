package com.kmu.timetocode.add

import android.app.AlertDialog
import android.content.DialogInterface
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
    private var basicImgFlag = false

    lateinit var selectImg : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddChallenge2Binding.inflate(inflater, container, false)

        binding.selectImgGroup.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                binding.selectImgOption1.id->{
                    basicImgFlag = false
                    flagCheck()
                }
                binding.selectImgOption2.id->{
                    if(imgFlag){
                        //앨범에서 가져온 이미지 업로드 취소 여부 확인
                        selectImg="basic1"
                        selectImgDialog()
                    }else{
                        basicImgFlag = true
                        flagCheck()
                    }
                }
                binding.selectImgOption3.id -> {
                    if(imgFlag){
                        //앨범에서 가져온 이미지 업로드 취소 여부 확인
                        selectImg="basic2"
                        selectImgDialog()
                    }else{
                        basicImgFlag = true
                        flagCheck()
                    }
                }
            }
        }

        binding.btnUploadBackGround.setOnClickListener{
            binding.uploadBackGroundImgView.setImageResource(R.drawable.gray_img)
            binding.uploadBackGroundImgView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            imgFlag = false
            flagCheck()
            requestPermission()
        }
        binding.btnUploadCancel.setOnClickListener{
            binding.uploadBackGroundImgView.setImageResource(R.drawable.gray_img)
            binding.uploadBackGroundImgView.scaleType = ImageView.ScaleType.CENTER_INSIDE

            imgFlag = false
            flagCheck()
        }

        binding.btnGoAdd3.setOnClickListener{
            model.addData2(selectImg)
            findNavController().navigate(R.id.action_fragmentAddChallenge2_to_fragmentAddChallenge3)
        }

        return binding.root
    }

    private fun selectImgDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("기본이미지를 선택하셨네요!")
            .setMessage("갤러리에서 가져온 이미지 업로드를 취소할까요?")
            .setPositiveButton("예",
                DialogInterface.OnClickListener { dialog, id ->
                    basicImgFlag = true
                    imgFlag = false
                    binding.uploadBackGroundImgView.setImageResource(R.drawable.gray_img)
                    binding.uploadBackGroundImgView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                    flagCheck()
                })
            .setNegativeButton("아니요",DialogInterface.OnClickListener { dialogInterface, i ->
                basicImgFlag = false
                imgFlag = true
                binding.selectImgOption1.isChecked=true
                flagCheck()
            })
        // 다이얼로그를 띄워주기
        builder.show()
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
                basicImgFlag = false
                binding.selectImgOption1.isChecked = true
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
        binding.btnGoAdd3.isEnabled = (imgFlag == !basicImgFlag)
        Toast.makeText(activity,"basic: ${basicImgFlag}, img: ${imgFlag}", Toast.LENGTH_SHORT).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}