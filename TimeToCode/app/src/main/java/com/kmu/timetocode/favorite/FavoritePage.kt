package com.kmu.timetocode.favorite

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
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
    private fun CancelFav(clickTitle: String) {
        val myId = UserProfile.getId()
        val url = "https://android-pkfbl.run.goorm.io/UserFavoriteChallenge/delete"
        val sr: StringRequest = object : StringRequest(Method.POST, url,
            Response.Listener { response: String? ->
                Toast.makeText(
                    requireContext(),
                    clickTitle.split("%").toTypedArray()[0] + " 찜 취소 완료",
                    Toast.LENGTH_SHORT
                ).show()
                showFavorList()
            },
            Response.ErrorListener { error: VolleyError ->
            }) {
            @Throws(java.lang.Error::class)
            override fun getParams(): MutableMap<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["idUser"] = myId.toString()
                params["nameChallenge"] = clickTitle
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
                        Challname =  ChallengeName
//                        val tag1 = challengeTag[1]
//                        val tag2 = challengeTag[2]
                        challengeList.add(FavorListModel(imageLink, nameChallenge, madeIdUser))
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
//      override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//                val currentItem = list[position]
//                holder.view_title?.text = currentItem.title
//                holder.view_owner?.text = currentItem.owner
//            }
        override fun getView(position: Int, convertview: View?, parent: ViewGroup?): View {
            val view: View
            val holder : ViewHolder
            if (convertview == null) {

                view = LayoutInflater.from(parent?.context).inflate(R.layout.favor_list_item,parent,false)
                holder = ViewHolder()

                holder.view_image = view.findViewById(R.id.imageArea)
                holder.view_title = view.findViewById(R.id.title)
                holder.view_owner = view.findViewById(R.id.owner)
                holder.view_cancel = view.findViewById(R.id.cancelFav)
                view.tag = holder

                val challengeItem = list[position] // 챌린지 list 안의 챌린지 한개

                val fullName = challengeItem.title
                holder.view_title?.text = fullName?.split("%")?.get(0)
                holder.view_owner?.text = challengeItem.owner
                Log.d("test", "UserImages_" + fullName)

                val storage: FirebaseStorage = FirebaseStorage.getInstance("gs://timetocode-13747.appspot.com/")
                val fileExt = arrayOf(".jpeg", ".jpg", "")
                for(i in fileExt)
                    storage.getReference().child("UserImages_" + fullName + i).downloadUrl
                        .addOnSuccessListener { uri ->
                            Glide.with(context).load(uri.toString().toUri()).into(holder.view_image!!)
                        }
//                holder.view_cancel?.setOnClickListener {
//                    CancelFav()
//                    Log.e("click","ok")
//                }
//

            } else {
                holder = convertview.tag as ViewHolder
                view = convertview
            }
            val currentItem = list[position]
            holder.view_cancel?.setOnClickListener {
                val clicktitle: String = currentItem.title
                CancelFav(clicktitle)
            }
            return view
        }
        override fun getCount(): Int {
            return list.size
        }
        override fun getItem(position: Int): Any = list.get(position)
        override fun getItemId(position: Int): Long = position.toLong()
        inner class ViewHolder {
            var view_image : ImageView? = null
            var view_title: TextView? = null
            var view_owner: TextView? = null
            var view_cancel: TextView? = null
        }
    }
}
