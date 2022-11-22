package com.kmu.timetocode.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
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
    var queue: RequestQueue? = null
    var myList: ListView? = null
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
        myList = rootView?.findViewById<ListView>(R.id.listview_favorite_fragment)
        showFavorList()
        return rootView
    }

    private fun showFavorList() {
        val myId = UserProfile.getId()
        val url = "https://android-pkfbl.run.goorm.io/challenge/challengeFavorite?idUser=" + myId
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
                        val tag1 = jsonObject.getString("tagName1")
                        val tag2 = jsonObject.getString("tagName2")
                        challengeList.add(FavoriteListModel(imageLink, nameChallenge, madeIdUser, countUser, tag1, tag2))
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
