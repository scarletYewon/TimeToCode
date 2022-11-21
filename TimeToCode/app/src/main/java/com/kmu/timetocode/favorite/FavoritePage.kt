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
    var favoriteListAdapter: FavoriteListAdapter? = null
    var queue: RequestQueue? = null
    val myId = UserProfile.getId() //5
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


        
        Log.e(showFavorList(myId).toString(),"시발")



        val list_array = arrayListOf<FavoriteListModel>(
            FavoriteListModel("a", "챌린지 이름", "생성자", "+60명", "github"),
            FavoriteListModel("a", "챌린지 이름", "생성자", "+60명", "github"),
            FavoriteListModel("a", "챌린지 이름", "생성자", "+60명", "github"),
        )
        var Adapter = FavoriteListAdapter(requireContext(), list_array)
        rootView?.findViewById<ListView>(R.id.listview_favorite_fragment)?.adapter = Adapter

        return rootView
    }

    private fun showFavorList(myId: Int) {
        val url = "https://android-pkfbl.run.goorm.io/UserFavoriteChallenge"
        val sr: StringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener { response: String? ->
                val challengeList = ArrayList<FavoriteListModel>()
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val userId = jsonObject.getInt("userId")
                        val nameChallenge = jsonObject.getString("nameChallenge")
                        val imageLink = jsonObject.getString("imageLink")
                        val madeIdUser = jsonObject.getInt("madeIdUser")
                        val tag1 = jsonObject.getString("firstTag")
                        val tag2 = jsonObject.getString("secondTag")
                        if (userId == myId){
                            challengeList.add(
                                FavoriteListModel(
                                    imageLink,
                                    nameChallenge,
                                    Integer.toString(madeIdUser),
                                    tag1,
                                    tag2
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    Log.e("FavorListJSON", response!!)
                }
                favoriteListAdapter= FavoriteListAdapter(requireContext(), challengeList)
            },
            Response.ErrorListener { error: VolleyError ->
                Log.e("Fuck","You"
                )
            }) {
            @Throws(java.lang.Error::class)
            override fun getParams(): MutableMap<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["idUser"] = myId.toString()
                return params
            }
        }
        sr.setShouldCache(false)
        queue = Volley.newRequestQueue(requireContext())
        queue!!.add<String>(sr)
    }
}
