package com.kmu.timetocode.favorite

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
    var Challname = ""
    var favoriteListAdapter: FavorListAdapter?=null
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
    private fun CancelFav() {
        val myId = UserProfile.getId()
        val url = "https://android-pkfbl.run.goorm.io/UserFavoriteChallenge/delete"
        val sr: StringRequest = object : StringRequest(Method.POST, url,
            Response.Listener { response: String? ->
            },
            Response.ErrorListener { error: VolleyError ->
            }) {
            @Throws(java.lang.Error::class)
            override fun getParams(): MutableMap<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["idUser"] = myId.toString()
                params["idChallenge"] = Challname
                Log.e("params",params.toString())
                return params
            }
        }
        sr.setShouldCache(false)
        queue = Volley.newRequestQueue(requireContext())
        queue!!.add(sr)
    }
    private fun showFavorList() {
        val myId = UserProfile.getId()
        val url = "https://android-pkfbl.run.goorm.io/challenge/challengeFavorite?idUser=" + myId
        val sr: StringRequest = object : StringRequest( Method.GET, url,
            Response.Listener { response: String? ->
                val challengeList = ArrayList<FavorListModel>()
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val nameChallenge = jsonObject.getString("nameChallenge")
                        val imageLink = jsonObject.getString("imageLink")
                        val madeIdUser = jsonObject.getString("name")
//                        val countUser = jsonObject.getInt("countUser")
                        // 여기서 데이터 나눠줄거임
                        //받아올 challengeName
                        val challengeTag = nameChallenge.split("%").toTypedArray()
                        val ChallengeName = challengeTag[0]
//                        val tag1 = challengeTag[1]
//                        val tag2 = challengeTag[2]
                        Challname = ChallengeName
                        challengeList.add(FavorListModel(imageLink, ChallengeName, madeIdUser))
                        Log.e("challengeList",challengeList.toString())
                    }
                } catch (e: Exception) {
                    Log.e("FavoriteListJSON", response!!)
                }
                favoriteListAdapter = FavorListAdapter(requireContext(), challengeList)
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
    inner class FavorListAdapter(val context: Context, val list: ArrayList<FavorListModel>): BaseAdapter() {
        var queue: RequestQueue? = null
        override fun getView(position: Int, convertview: View?, parent: ViewGroup?): View {
            val view: View
            val holder : ViewHolder

            if (convertview == null) {
                view = LayoutInflater.from(parent?.context).inflate(R.layout.favor_list_item,parent,false)
                holder = ViewHolder()

                holder.view_image = view.findViewById(R.id.imageArea)
                holder.view_title = view.findViewById(R.id.title)
                holder.view_owner = view.findViewById(R.id.owner)

                view.tag = holder
            } else {
                holder = convertview.tag as ViewHolder
                view = convertview
            }
            val item = list[position]
            holder.view_title?.text = item.title
            holder.view_owner?.text = item.owner

            val favorCancelBtn = view.findViewById<TextView>(R.id.cancelFav)
            favorCancelBtn.setOnClickListener {
                CancelFav()
                Log.e("click","ok")
            }
            return view
        }
        override fun getCount(): Int {
            return list.size
        }
        override fun getItem(position: Int): Any = list.get(position)
        override fun getItemId(position: Int): Long = position.toLong()
        private inner class ViewHolder {
            var view_image : ImageView? = null
            var view_title: TextView? = null
            var view_owner: TextView? = null
        }
    }
}
