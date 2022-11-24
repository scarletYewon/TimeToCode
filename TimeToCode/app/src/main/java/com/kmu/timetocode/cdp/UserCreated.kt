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

class UserCreated : Fragment() {
    var favoriteListAdapter: FavoriteListAdapter?=null
    var queue: RequestQueue? = null
    var myList: ListView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        val rootView : View = inflater.inflate(R.layout.fragment_user_created, container, false)
//        val mActivity = activity as MainActivity
//        val btn_change = rootView.findViewById(R.id.button_change);
//        btn_change.setOnClickListener{
//            activity.changeFragment(2)
//        }

        val backCertification = rootView?.findViewById<ImageButton>(R.id.backCertification)
        backCertification?.setOnClickListener { (activity as NavActivity?)!!.replaceFragment(
            MainFragment()
        ) }

        showDoneList()
//        val list_array = arrayListOf<FavoriteListModel>(
//            FavoriteListModel("a","챌린지","생성자",60,"github","algorithm"),
//            FavoriteListModel("a","챌린지","생성자",60,"github","algorithm"),
//            FavoriteListModel("a","챌린지","생성자",60,"github","algorithm"),
//            FavoriteListModel("a","챌린지","생성자",60,"github","algorithm"),
//            FavoriteListModel("a","챌린지","생성자",60,"github","algorithm"),
//            FavoriteListModel("a","챌린지","생성자",60,"github","algorithm")
//        )
//        var Adapter = FavoriteListAdapter(requireContext(),list_array)
        myList = rootView?.findViewById<ListView>(R.id.listview_created_fragment)

        return rootView
    }
    private fun showDoneList() {
        val myId = UserProfile.getId()
        val url = "https://android-pkfbl.run.goorm.io/userChallenge/userUploadChallenge?idUser=" + myId
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