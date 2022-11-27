package com.kmu.timetocode.cdp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.kmu.timetocode.MainFragment
import com.kmu.timetocode.NavActivity
import com.kmu.timetocode.R
import com.kmu.timetocode.favorite.FavoriteListAdapter
import com.kmu.timetocode.favorite.FavoriteListModel
import com.kmu.timetocode.login.UserProfile
import org.json.JSONArray

class Proceeding : Fragment() {
    var favoriteListAdapter: FavoriteListAdapter?=null
    var queue: RequestQueue? = null
    var myList: ListView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        val rootView : View = inflater.inflate(R.layout.fragment_proceeding, container, false)
        val backCertification = rootView?.findViewById<ImageButton>(R.id.backCertification)
        backCertification?.setOnClickListener { (activity as NavActivity?)!!.replaceFragment(
            MainFragment()
        ) }
        showDoneList()
        myList = rootView?.findViewById<ListView>(R.id.listview_proceed_fragment)

        return rootView
    }

    private fun showDoneList() {
        val myId = UserProfile.getId()
        val url = "https://android-pkfbl.run.goorm.io//userChallenge/processChallenge?idUser=" + myId
        val sr: StringRequest = object : StringRequest( Method.GET, url,
            Response.Listener { response: String? ->
                val challengeList = ArrayList<FavoriteListModel>()
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val nameChallenge = jsonObject.getString("nameChallenge")
                        val imageLink = jsonObject.getString("imageLink")
                        val madeIdUser = jsonObject.getString("name")
                        val countUser = jsonObject.getInt("countUser")
                        // 여기서 데이터 나눠줄거임
                        //받아올 challengeName
                        val challengeTag = nameChallenge.split("%").toTypedArray()
                        val ChallengeName = challengeTag[0]
                        val tag1 = challengeTag[1]
                        val tag2 = challengeTag[2]
                        challengeList.add(FavoriteListModel(imageLink, ChallengeName, madeIdUser, countUser, tag1, tag2))
                        Log.e("challengeList",challengeList.toString())
                    }
                } catch (e: Exception) {
                    Log.e("FavoriteListJSON", response!!)
                }
                favoriteListAdapter = FavoriteListAdapter(requireContext(), challengeList)
                Log.e("List",favoriteListAdapter.toString())
                myList?.setAdapter(favoriteListAdapter)
            },
            Response.ErrorListener { error: VolleyError ->
            }) {
            @Throws(java.lang.Error::class)
            override fun getParams(): MutableMap<String,String>? {
                val params: MutableMap<String, String> = HashMap()
                return params
            }
        }
        sr.setShouldCache(false)
        queue = Volley.newRequestQueue(requireContext())
        queue!!.add(sr)
    }
}