package com.kmu.timetocode.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.kmu.timetocode.*
import com.kmu.timetocode.login.UserProfile
import org.json.JSONArray


class FavoritePage : Fragment() {
    var favoriteListAdapter: FavoriteListAdapter?=null
    var queue: RequestQueue? = null//5
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_favorite_page, container, false)

        val backCertification = rootView?.findViewById<ImageButton>(R.id.backCertification)
        backCertification?.setOnClickListener {
            (activity as NavActivity?)!!.replaceFragment(
                MainFragment()
            )
        }
//        val btn = rootView?.findViewById<LinearLayout>(R.id.hello)
//        btn?.setOnClickListener {
//            showFavorList(myId)
//        }
        showFavorList()
//        Log.e(Fav)
//
//        val list_array = arrayListOf(
//            FavoriteListModel("a", "챌린지 이름", "생성자", 60, "github","algorithm"),
//            FavoriteListModel("a", "챌린지 이름", "생성자", 70, "github","algorithm"),
//            FavoriteListModel("a", "챌린지 이름", "생성자", 80, "github","algorithm"),
//        )
//        var Adapter = FavoriteListAdapter(requireContext(), list_array)
        rootView?.findViewById<ListView>(R.id.listview_favorite_fragment)?.adapter = favoriteListAdapter

        return rootView
    }

    private fun showFavorList() {
        val myId = UserProfile.getId()
        val url = "https://android-pkfbl.run.goorm.io/challenge/challengeFavorite?idUser=" + myId
        val sr: StringRequest = object : StringRequest(
            Method.GET, url,
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
                        val tag1 = jsonObject.getString("tagName1")
                        val tag2 = jsonObject.getString("tagName2")
                        challengeList.add(FavoriteListModel(imageLink, nameChallenge, madeIdUser, countUser, tag1, tag2))
                        Log.e(challengeList.toString(),"hellooooo")
                    }
                    Log.e(challengeList.toString(),"hello")
                } catch (e: Exception) {
                    Log.e("hi",e.toString())
                    Log.e("FavoriteListJSON", response!!)
                }
                Log.e("gogo",response!!)
                Log.e("challist",challengeList.get(0).title)
                favoriteListAdapter = FavoriteListAdapter(requireContext(), challengeList)
            },
            Response.ErrorListener { error: VolleyError ->
                Log.e("Favor","error")
            }) {
            @Throws(java.lang.Error::class)
            override fun getParams(): MutableMap<String,String>? {
                val params: MutableMap<String, String> = HashMap()
//                params["idUser"] = myId.toString()
//                Log.e(params.toString(),"params")
                return params
            }
        }
        sr.setShouldCache(false)
        queue = Volley.newRequestQueue(requireContext())
        queue!!.add(sr)
    }
}
