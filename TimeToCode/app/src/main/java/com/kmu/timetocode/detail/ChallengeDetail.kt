package com.kmu.timetocode.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.firebase.storage.FirebaseStorage
import com.kmu.timetocode.NavActivity
import com.kmu.timetocode.R
import com.kmu.timetocode.databinding.ActivityChallengeDetailBinding
import com.kmu.timetocode.databinding.ChoiceTagBinding
import com.kmu.timetocode.login.UserProfile
import org.json.JSONArray
import org.json.JSONObject

class ChallengeDetail : AppCompatActivity() {
    var Challname = ""
    var queue: RequestQueue? = null

    private lateinit var binding: ActivityChallengeDetailBinding

    private var chId = 0
    var tagList : MutableList<String> = mutableListOf()
    var howList : MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChallengeDetailBinding.inflate(layoutInflater)
        val view = binding.root

        var intent = getIntent()
        var text = intent.getStringExtra("clickChallengeInList").toString()
        var clickList =text.split("%")
        var clickTitle = clickList[0]
        Challname = clickTitle
        showDetailData(clickTitle)
        var owner = intent.getStringExtra("whoMade").toString()
        binding.detailChallengeName.text = clickTitle
        binding.detailChallengeOwner.text = owner
        Toast.makeText(this, clickTitle, Toast.LENGTH_SHORT).show()
        setContentView(view)

        binding.btnJoinChallenge.setOnClickListener {
            joinChallenge()
        }
        binding.favorBtn.setOnClickListener {
            NewFavor()
        }
    }

    private fun setupChip(list: MutableList<String>, where:Int) {

        for (name in list) {
            val chip = createChip(name)
            when(where){
                1 -> binding.detailChallengeTagGroup.addView(chip)
                2 -> binding.detailChallengeHowChipGroup.addView(chip)
            }
        }
    }
    private fun createChip(label: String): Chip {
        val chip = ChoiceTagBinding.inflate(layoutInflater).root
        chip.text = label
        chip.isCheckable = false
        chip.isClickable = false
        return chip
    }

    private fun setUpFreq(freq :Int){
        when(freq){
            1 -> {howList.add("매일")
                Log.i("여기는 freq","이건 리스트: ${howList}")}
            2 -> howList.add("평일 매일")
            3 -> howList.add("주말")
        }
    }

    private fun setUpCount(count :Int){
        when(count){
            1-> howList.add("하루 1번")
            else->{howList.add("하루 ${count}번")}
        }
    }

    private fun setUpTime(start:String,end:String){

        var startTime = "00:00"
        var endTime = "23:59"
        when(start.length){
            1-> startTime = "00:0"+start
            2-> startTime = "00:"+start
            3-> startTime = "0"+start.substring(0,1)+":"+start.substring(1)
            4 -> startTime = start.substring(0,2)+":"+start.substring(2)
        }
        when(end.length){
            1-> endTime = "00:0"+end
            2-> endTime = "00:"+end
            3-> endTime = "0"+end.substring(0,1)+":"+end.substring(1)
            4 -> endTime = end.substring(0,2)+":"+end.substring(2)
        }

        if (startTime =="00:00" && endTime =="23:59"){
            howList.add("24시간")
        }else{
            howList.add("$startTime~$endTime")
        }
    }

    private fun setUpPeriod(period:Int){
        when(period){
            7 -> howList.add("1주 동안")
            14 -> howList.add("2주 동안")
            21 -> howList.add("3주 동안")
            28 -> howList.add("4주 동안")
            50 -> howList.add("50일 동안")
            100 -> howList.add("100일 동안")
            100000000 -> howList.add("계속")
        }
    }

    private fun NewFavor() {
        val myId = UserProfile.getId()
        val url = "https://android-pkfbl.run.goorm.io/UserFavoriteChallenge/post"
        val sr: StringRequest = object : StringRequest(Method.POST, url,
            Response.Listener { response: String? ->
                Toast.makeText(
                    this,
                    Challname +" 찜 완료",
                    Toast.LENGTH_SHORT
                ).show()
            },
            Response.ErrorListener { error: VolleyError ->
            }) {
            @Throws(java.lang.Error::class)
            override fun getParams(): MutableMap<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["idUser"] = myId.toString()
                params["nameChallenge"] = Challname
                Log.e("params",params.toString())
                return params
            }
        }
        sr.setShouldCache(false)
        queue = Volley.newRequestQueue(this)
        queue!!.add(sr)
    }

    private fun joinChallenge() {
        val myId = UserProfile.getId()
        val url = "https://android-pkfbl.run.goorm.io/userChallenge/add"
        val sr: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response: String? ->
                // 챌린지 참여 완료 확인하기
                Toast.makeText(this, "함께 열심히 해봐요!!", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error: VolleyError? ->

                Toast.makeText(
                    this,
                    "인터넷 연결을 확인해주세요.",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            @Throws(Error::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["idChallenge"] = chId.toString()
                params["idUser"] = myId.toString()
                Log.i("params", params.toString())

                return params
            }
        }
        sr.setShouldCache(false)
        queue = Volley.newRequestQueue(this)
        queue!!.add(sr)

    }

    private fun showDetailData(title: String) {
        val url =
            "https://android-pkfbl.run.goorm.io/challenge/Detail?nameChallenge=" + title
        val sr: StringRequest = object : StringRequest(Method.GET, url,
            Response.Listener { response: String? ->
                try {
                    val jsonArray = JSONArray(response)
                    val jsonObject = jsonArray.getJSONObject(0)
                    val id = jsonObject.getInt("idChallenge")

                    val nameTag = jsonObject.getString("nameChallenge").split(" %").get(0)

                    FirebaseStorage.getInstance().getReference()
                        .child("UserImages_" + nameTag + ".jpeg").downloadUrl.addOnSuccessListener { uri ->
                            Glide.with(this)
                                .load(uri)
                                .into(binding.challengeMainImg)
                        }.addOnFailureListener { uri ->
                            FirebaseStorage.getInstance().getReference()
                                .child("UserImages_" + nameTag).downloadUrl.addOnSuccessListener { uri1 ->
                                    Glide.with(this)
                                        .load(uri1)
                                        .into(binding.challengeMainImg)
                                }.addOnFailureListener { uri1 ->
                                    FirebaseStorage.getInstance().getReference()
                                        .child("UserImages_" + nameTag + ".jpg").downloadUrl.addOnSuccessListener { uri2 ->
                                            Glide.with(this)
                                                .load(uri2)
                                                .into(binding.challengeMainImg)
                                        }
                                }
                        }
                    val chCount = jsonObject.getInt("count")
                    val prtcp = jsonObject.getInt("countUser")
                    val completeUser = jsonObject.getInt("countCompleteUser")
                    val introduce = jsonObject.getString("intruduce")
                    val freq = jsonObject.getInt("frequency")
                    val possibleStart = jsonObject.getInt("possibleStartTime").toString()
                    val possibleEnd = jsonObject.getInt("possibleEndTime").toString()
                    val interval = jsonObject.getString("countInterval")
                    val period = jsonObject.getInt("endDate")
                    val how = jsonObject.getString("certificationWay")
//                    val howImg = jsonObject.getString("certificationWayImageLink")
                    val post = jsonObject.getInt("challengePostCount")
                    // 여기서 데이터 입력
                    val nameTagList = nameTag.split("%")
                    Log.i("여기는 데이터", "${nameTagList[0]}")
                    Toast.makeText(this, "${nameTagList[0]}", Toast.LENGTH_SHORT).show()

                    for ( i in 1 until nameTagList.size){
                        tagList.add(nameTagList[i])
                    }
                    setupChip(tagList,1)
                    Log.i("이건 tag리스트", "태그 : ${tagList}")

                    setUpFreq(freq)
                    setUpCount(chCount)
                    setUpTime(possibleStart, possibleEnd)
                    setUpPeriod(period)

                    setupChip(howList,2)


                    chId = id
                    // binding.challengeMainImg.setImageURI(imageLink.toUri())
                    binding.detailChallengePtcpCount.text = prtcp.toString()
                    binding.detailChallengeFinishCount.text = completeUser.toString()
                    binding.detailChallengeExpText.text = introduce
                    binding.detailChallengeHowText.text = how
//                    if (howImg.contains("content")) {
//                        binding.detailChallengeHowImg.setImageURI(howImg.toUri())
//                    } else {
//                        binding.detailChallengeHowImg.visibility = View.GONE
//                    }
                } catch (e: Exception) {
                    Log.e("Challenge Counting JSON", response!!)
                }
            },
            Response.ErrorListener { error: VolleyError ->
            }) {
            @Throws(java.lang.Error::class)
            override fun getParams(): MutableMap<String, String>? {
                val params: MutableMap<String, String> = HashMap()
//                    params["idUser"] = myId.toString()
//                    Log.e(params.toString(),"params")
                return params
            }
        }
        sr.setShouldCache(false)
        queue = Volley.newRequestQueue(this)
        queue!!.add(sr)
    }
}