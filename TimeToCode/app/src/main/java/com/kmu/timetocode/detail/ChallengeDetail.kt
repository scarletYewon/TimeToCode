package com.kmu.timetocode.detail

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.kmu.timetocode.R
import com.kmu.timetocode.databinding.ActivityChallengeDetailBinding
import org.json.JSONObject

class ChallengeDetail : AppCompatActivity() {
    var queue: RequestQueue? = null

    private lateinit var binding : ActivityChallengeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChallengeDetailBinding.inflate(layoutInflater)
        val view = binding.root


        var intent = getIntent()
        var text = intent.getStringExtra("clickChallengeInList").toString()
        var clickList =text.split("%")
        var clickTitle = clickList[0]
        showDetailData(text)
        binding.detailChallengeName.text = clickTitle
        Toast.makeText(this, clickTitle, Toast.LENGTH_SHORT).show()
        setContentView(view)
    }

    private fun showDetailData(title:String){
        val url = "https://android-pkfbl.run.goorm.io/challenge/nameChallenge?nameChallenge=" + title
        val sr: StringRequest = object : StringRequest(Method.GET, url,
            Response.Listener { response: String? ->
                try {
                    val jsonObject = JSONObject(response)
                    val id = jsonObject.getInt("idChallenge")
                    val who = jsonObject.getInt("madeIdUser")
                    val nameTag = jsonObject.getString("nameChallenge").split(" %").get(0)
                    val imageLink = jsonObject.getString("imageLink")
                    val chCount = jsonObject.getInt("count")
                    val prtcp = jsonObject.getInt("countUser")
                    val introduce = jsonObject.getString("intruduce")
                    val freq = jsonObject.getInt("frequency")
                    val start = jsonObject.getInt("possibleStartTime")
                    val end = jsonObject.getInt("possibleEndTime")
                    val interval = jsonObject.getString("countInterval")
                    val period = jsonObject.getInt("endDate")
                    val how = jsonObject.getString("certificationWay")
                    val howImg = jsonObject.getString("certificationWayImageLink")
                    val post = jsonObject.getInt("challengePostCount")
                    // 여기서 데이터 입력
                    val nameTagList = nameTag.split("%")
                    Log.i("여기는 데이터","${nameTagList[0]}")
                    Toast.makeText(this,"${nameTagList[0]}",Toast.LENGTH_SHORT).show()

                    binding.detailChallengeName.text = nameTag

                    binding.detailChallengePtcpCount.text = chCount.toString()
                    binding.detailChallengeExpText.text = introduce
                    binding.detailChallengeHowText.text = how
                } catch (e: Exception) {
                    Log.e("Challenge Counting JSON", response!!)
                }
            },
            Response.ErrorListener { error: VolleyError ->
            }) {
            @Throws(java.lang.Error::class)
            override fun getParams(): MutableMap<String,String>? {
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