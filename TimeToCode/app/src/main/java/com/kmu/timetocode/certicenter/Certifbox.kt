package com.kmu.timetocode.certicenter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.kmu.timetocode.*
import com.kmu.timetocode.add.AddImg
import com.kmu.timetocode.login.UserProfile
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class Certifbox : Fragment() {

    lateinit var image : Bitmap
    lateinit var certiImage : String

    private var imgFromCam : Boolean = false
    var myId : Int = UserProfile.getId()
    var challengename : String = ""
    var now = LocalDate.now().format(DateTimeFormatter.ofPattern("MMdd"))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        val rootView : View = inflater.inflate(R.layout.fragment_certifbox, container, false)

        val backCertification = rootView?.findViewById<ImageButton>(R.id.backCertification)
        val uploadCertifImg = rootView?.findViewById<LinearLayout>(R.id.uploadCertifImg)
        val challengeTitle = rootView?.findViewById<TextView>(R.id.challengeTitle)
        val submitBtn = rootView?.findViewById<Button>(R.id.submit)

        backCertification?.setOnClickListener { (activity as NavActivity?)!!.replaceFragment(
            CertificationFragment()
        ) }
        submitBtn?.setOnClickListener {
            submit()
            (activity as NavActivity?)!!.replaceFragment(
                MainFragment()
            )

        }

        val model = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)

        val fullName = model.getMessage()
        val title = fullName?.split("%")?.get(0)
        Log.d("test", title.toString())
        challengeTitle?.text = title
        if (title != null) {
            challengename = title
        }

        uploadCertifImg?.setOnClickListener {  requestPermission() }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uploadCertifImgView = view?.findViewById<ImageView>(R.id.uploadCertifImgView)

        requireActivity().supportFragmentManager.setFragmentResultListener("takeRequestKey",this) { requestKey, bundle ->
            bundle.getString("takeBundleKey")?.let {
                val imageBytes = Base64.getDecoder().decode(it)
                image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                uploadCertifImgView?.setImageBitmap(image)
                imgFromCam = true
                Log.i("take","takeImg??? ?????? fragment")
            }
        }

        requireActivity().supportFragmentManager.setFragmentResultListener("pickRequestKey",this) { requestKey, bundle ->
            bundle.getString("pickBundleKey")?.let {
                var pickImage = Uri.parse(it)
                certiImage = it
                uploadCertifImgView?.setImageURI(pickImage)
                imgFromCam= false
                Log.i("take","pickImg??? ?????? fragment")
            }
        }
    }

    private fun requestPermission() {
        TedPermission.create()
            .setPermissionListener(object : PermissionListener {

                //????????? ???????????? ???
                override fun onPermissionGranted() {
                    UploadImgDialog("???????????? ?????????",2).show(parentFragmentManager,"takeImgDialog")
                }
                //????????? ???????????? ???
                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(activity, "????????? ?????? ??????", Toast.LENGTH_SHORT).show()
                }
            })
            .setDeniedMessage("????????? ????????? ??????????????????.")// ????????? ?????? ??? ???????????? Dialog Message
            .setPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.CAMERA)// ???????????? ??????
            .check()
    }

    private fun savePhoto(bitmap: Bitmap) {
        //?????? ????????? ???????????? ?????? ?????? ??????
        val folderPath = Environment.getExternalStorageDirectory().absolutePath + "/Pictures/"
        val timestamp : String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val fileName = "${timestamp}.jpeg"
        val folder = File(folderPath)
        if(!folder.isDirectory){//?????? ????????? ????????? ????????????
            folder.mkdir() // make directory??? ???????????? ??????????????? ?????? ????????????
        }
        //???????????? ?????? ??????
        val out = FileOutputStream(folderPath + fileName)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        Toast.makeText(activity,"????????? ????????? ?????????????????????.", Toast.LENGTH_SHORT).show()
    }
    private fun submit(){
        Log.e("dd",now)
        AddImg.AddUri(certiImage.toUri(), myId.toString()+ "%" + challengename + "%" + now) //5%1%1207")
    }
}