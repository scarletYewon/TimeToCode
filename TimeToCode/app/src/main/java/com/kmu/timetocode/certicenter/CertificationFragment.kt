package com.kmu.timetocode.certicenter

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.kmu.timetocode.*
import com.kmu.timetocode.login.UserProfile
import com.kmu.timetocode.recordcenter.RecordFragment
import org.json.JSONArray


class CertificationFragment : Fragment() {
    var adapter: ListViewAdapter?=null
    var queue: RequestQueue?=null
    var myList: ListView?= null

    lateinit var model: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        model = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        val rootView = inflater.inflate(R.layout.fragment_certification, container, false)
        val listView = view?.findViewById<ListView>(R.id.list)

        val backCertification = rootView?.findViewById<ImageButton>(R.id.backCertification)
        backCertification?.setOnClickListener { (activity as NavActivity?)!!.replaceFragment(MainFragment()) }

        myList = rootView?.findViewById<ListView>(R.id.list)

//         challenge 추가하는 부분
//        adapter!!.addItem(Challenge("Github Commit\n", "생성자1", R.drawable.ttcwhite))
//        adapter!!.addItem(Challenge("BOJ algorithm\n", "maker2", R.drawable.ttcwhite))
        showMyList()

        listView?.setAdapter(adapter)
        rootView?.findViewById<ListView>(R.id.list)?.adapter = adapter

        return rootView
    }

    inner class ListViewAdapter(val context: Context, val list: ArrayList<Challenge>): BaseAdapter() {
        override fun getCount(): Int { return list.size }

        fun addItem(item: Challenge) { list.add(item) }
        override fun getItem(position: Int): Any { return list[position] }
        override fun getItemId(position: Int): Long { return position.toLong() }

        override fun getView(position: Int, convertview: View?, parent: ViewGroup?): View {
            val view: View
            val holder: ViewHolder

            if (convertview == null) {
                view = LayoutInflater.from(parent?.context).inflate(R.layout.listview_challenge, parent, false)
                holder = ViewHolder()

                holder.ch_title = view.findViewById(R.id.challengeTitle)
                holder.ch_maker = view.findViewById(R.id.challengeMaker)
                holder.ch_image = view.findViewById(R.id.challengeImage)
                holder.btn_certificaion = view.findViewById(R.id.btnCertification)
                holder.btn_gallery = view.findViewById(R.id.btnGallery)

                val fullName = list[position]
                holder.ch_title?.text = fullName.title?.split("%")?.get(0)
                holder.ch_maker?.text = list[position].maker

                holder.btn_certificaion?.setOnClickListener { (activity as NavActivity?)!!.replaceFragment(Certifbox()) }
                holder.btn_gallery?.setOnClickListener {
                    model.sendMessage(holder.ch_title?.text.toString())
                    Log.d("test sendMessage", list[position].title.toString())
                    (activity as NavActivity?)!!.replaceFragment(RecordFragment()) }
                view.tag = holder
            } else {
                holder = convertview.tag as ViewHolder
                view = convertview
            }

            val challengeItem = list[position] // 챌린지 list 안의 챌린지 한개

//            val resourceId = context.resources.getIdentifier(challengeItem.resId.toString(), "drawable", context.packageName)
//            holder.ch_image?.setImageResource(resourceId)
//            challengeItem.resId?.let { holder.ch_image?.setImageResource(it) };

            return view //뷰 객체 반환
        }
        private inner class ViewHolder {
            var ch_title: TextView? = null
            var ch_maker: TextView? = null
            var ch_image: ImageButton? = null
            var btn_certificaion: Button? = null
            var btn_gallery: Button? = null
        }
    }

    private fun showMyList() {
        val myId = UserProfile.getId()
        val url = "https://android-pkfbl.run.goorm.io/userChallenge/UserChallengeList?idUser=" + myId
        val sr: StringRequest = object : StringRequest( Method.GET, url,
            Response.Listener { response: String? ->
                val challengeList = ArrayList<Challenge>()
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        Log.d("test challenge list in certiCenter", jsonObject.toString())
                        val nameChallenge = jsonObject.getString("nameChallenge")
                        var imageLink = jsonObject.getString("imageLink")
                        val madeIdUser = jsonObject.getString("name")
                        imageLink = imageLink.split(":").get(1).substring(1)
                        Log.d("test challenge image in certiCenter", imageLink)
                        challengeList.add(Challenge(nameChallenge, madeIdUser, imageLink))
                    }
                } catch (e: Exception) {
                    Log.e("MyListJSON", response!!)
                }
                adapter = ListViewAdapter(requireContext(), challengeList)
                myList?.setAdapter(adapter);
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