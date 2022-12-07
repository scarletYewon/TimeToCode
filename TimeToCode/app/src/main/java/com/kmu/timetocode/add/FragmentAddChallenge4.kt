package com.kmu.timetocode.add

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.storage.FirebaseStorage
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.kmu.timetocode.NavActivity
import com.kmu.timetocode.R
import com.kmu.timetocode.UploadImgDialog
import com.kmu.timetocode.databinding.FragmentAddChallenge4Binding
import com.kmu.timetocode.login.UserProfile
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class FragmentAddChallenge4 : Fragment() {

    private val model: AddChallengeViewModel by activityViewModels()
    var queue: RequestQueue? = null

    private var _binding: FragmentAddChallenge4Binding? = null
    private val binding get() = _binding!!

    var image : String = "none"
    private var howFlag = false
    private var selectImgFlag = false
    private var imgFromCam : Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddChallenge4Binding.inflate(inflater, container, false)

        binding.editChallengeHow.addTextChangedListener(howListener)

        binding.btnSelectHowImg.setOnClickListener {
            if (selectImgFlag){
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("사진첨부를 정말로 취소하시겠습니까?")
                    .setPositiveButton("예",
                        DialogInterface.OnClickListener { dialog, id ->
                            binding.howImgView.setImageResource(R.drawable.gray_img)
                            binding.howImgView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                            binding.textBtnSelectHowImg.text = "사진 첨부하기"
                            selectImgFlag = false
                            image="none"
                            Toast.makeText(activity,"사진첨부를 취소했습니다.",Toast.LENGTH_SHORT).show()
                        })
                    .setNegativeButton("아니요",DialogInterface.OnClickListener{dialog, id->})
                // 다이얼로그를 띄워주기
                builder.show()
            }else{
                requestPermission()
                checkSelect()
            }
        }

    // TODO: 챌린지 생성 후 어디로 가야하는지 결정, 완료 버튼 클릭 시 기입 정보 보내기

        binding.btnAddFinish.setOnClickListener{
            if (imgFromCam){
                savePhoto(image.toUri())
            }
            complete()
        }
        return binding.root
    }

    private fun checkSelect() {
        if (image != "none"){
            binding.textBtnSelectHowImg.text ="사진 첨부 취소하기"
            selectImgFlag = true
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddFinish.isEnabled = false

        requireActivity().supportFragmentManager.setFragmentResultListener("takeRequestKey",this) { requestKey, bundle ->
            bundle.getString("takeBundleKey")?.let {
//                val imageBytes = Base64.getDecoder().decode(it)
//                image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
//                binding.howImgView.setImageBitmap(image)
                binding.howImgView.scaleType = ImageView.ScaleType.CENTER_CROP
                image = it
                var upLoadImg = it.toUri()
                binding.howImgView.setImageURI(upLoadImg)
                imgFromCam = true
                checkSelect()
                savePhoto(image.toUri())
                Log.i("take","takeImg를 통해 fragment")
            }
        }

        requireActivity().supportFragmentManager.setFragmentResultListener("pickRequestKey",this) { requestKey, bundle ->
            bundle.getString("pickBundleKey")?.let {
                var pickImage = Uri.parse(it)
                image = it
                binding.howImgView.setImageURI(pickImage)
                binding.howImgView.scaleType = ImageView.ScaleType.CENTER_CROP
                checkSelect()
                imgFromCam= false
                Log.i("take","pickImg를 통해 fragment")
            }
        }
    }

    private val howListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            howFlag = binding.editChallengeHow.text.isNotEmpty()
            flagCheck()
        }
    }

    // 입력 조건에 따른 다음버튼 활성화 여부 확인
    private fun flagCheck() {
        binding.btnAddFinish.isEnabled = howFlag
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

    private fun savePhoto(uri: Uri) {
        //사진 폴더에 저장하기 위한 경로 선언
        val decode = ImageDecoder.createSource(requireContext().contentResolver,
            uri)
        val bitmap = ImageDecoder.decodeBitmap(decode)
        val folderPath = Environment.getExternalStorageDirectory().absolutePath + "/Pictures/"
        val timestamp : String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val fileName = "${timestamp}.jpeg"
        val folder = File(folderPath)
        if(!folder.isDirectory){//해당 경로에 폴더가 존재하지
            folder.mkdir() // make directory의 줄임말로 해당경로에 폴더 자동으로
        }
        //실제적인 저장 처리
        val out = FileOutputStream(folderPath + fileName)
        Log.i("사진 저장","사진저장: ${out}")
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        Log.i("사진 저장","사진저장b: ${bitmap}")
        out.close()

        Toast.makeText(activity,"사진이 앨범에 저장되었습니다.",Toast.LENGTH_SHORT).show()
    }

    private fun complete() {

        val myId = UserProfile.getId()
        val url = "https://android-pkfbl.run.goorm.io/challenge/add"

        lateinit var chName : String
        lateinit var chIntroduce : String

        lateinit var chImg : String

        var chFreq = 1
        var chCount  = 1
        var chStart = 0
        var chEnd = 2359
        var chInterval = "0"
        var chPeriod = 7

        val chHow = binding.editChallengeHow.text.toString().trim()
        val chHowImg = image

        model.nameData.observe(viewLifecycleOwner, Observer{
            chName = it
        })
        Toast.makeText(activity,"${chName}",Toast.LENGTH_SHORT).show()

        model.introduceData.observe(viewLifecycleOwner, Observer{
            chIntroduce = it
        })

        model.chImgData.observe(viewLifecycleOwner, Observer{
            chImg = it
        })

        model.freqData.observe(viewLifecycleOwner, Observer{
            chFreq = it
        })

        model.countData.observe(viewLifecycleOwner, Observer{
            chCount = it
        })
        model.intervalData.observe(viewLifecycleOwner, Observer{
            chInterval = it
        })
        model.startTimeData.observe(viewLifecycleOwner, Observer{
            chStart = it
        })
        model.endTimeData.observe(viewLifecycleOwner, Observer{
            chEnd = it
        })
        model.chPeriodData.observe(viewLifecycleOwner, Observer{
            chPeriod = it
        })

        Log.i("data","name: ${chName}")
        Log.i("data","made: ${myId}")
        Log.i("data","introduce: ${chIntroduce}")
        Log.i("data","img: ${chImg}")
        Log.i("data","freq: ${chFreq}")
        Log.i("data","start: ${chStart}")
        Log.i("data","count: ${chCount}")
        Log.i("data","end: ${chEnd}")
        Log.i("data","interval: ${chInterval}")
        Log.i("data","period: ${chPeriod}")
        Log.i("data","how: ${chHow}")
        Log.i("data","howImg: ${chHowImg}")

        //파이어베이스에 사진 올리기
        if(chImg != "basic1" && chImg != "basic2"){
            AddImg.AddUri(chImg.toUri(), chName)
        }
        AddImg.AddUri(chImg.toUri(), chName)
        if (image != "none"){
            AddImg.AddUri(chHowImg.toUri(),chName+"HOW")
        }

        val sr: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response: String? ->
                // 회원가입 응답 확인하기
                Toast.makeText(activity, "챌린지 생성완료", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error: VolleyError? ->

                Toast.makeText(
                    activity,
                    "인터넷 연결을 확인해주세요.",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            @Throws(Error::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["idChallenge"] = "0"
                params["nameChallenge"] = chName
                params["intruduce"] = chIntroduce
                params["imageLink"] = chImg

                chImg.toUri()
                params["frequency"] = chFreq.toString()
                params["count"] = chCount.toString()
                params["possibleStartTime"] = chStart.toString()
                params["possibleEndTime"] = chEnd.toString()
                params["countInterval"] = chInterval
                params["endDate"] = chPeriod.toString()
                params["madeIdUser"] = myId.toString()
                params["challengePostCount"] = "0"
                params["countUser"] ="0"
                params["certificationWay"] = chHow
                params["certificationWayImageLink"] = chHowImg
                Log.i("params",params.toString())

                return params
            }
        }
        sr.setShouldCache(false)
        queue = Volley.newRequestQueue(requireContext())
        queue!!.add(sr)

        val intent = Intent(context, NavActivity::class.java)
        startActivity(intent)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}