package com.kmu.timetocode.recordcenter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.kmu.timetocode.MainFragment
import com.kmu.timetocode.MyViewModel
import com.kmu.timetocode.NavActivity
import com.kmu.timetocode.R
import com.kmu.timetocode.certicenter.CertificationFragment
import com.kmu.timetocode.login.UserProfile
import org.json.JSONArray


class RecordFragment : Fragment() {
    var adapter : GridViewAdapter?=null
    var queue: RequestQueue?=null
    var myList: GridView?=null
    var title: String?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        val rootView = inflater.inflate(R.layout.fragment_record, container, false)
        val listView = view?.findViewById<GridView>(R.id.recordList)

        val backRecord = rootView?.findViewById<ImageButton>(R.id.backRecord)
        backRecord?.setOnClickListener { (activity as NavActivity?)!!. replaceFragment(CertificationFragment()) }

        myList = rootView?.findViewById<GridView>(R.id.recordList)

        val challengeTitle = rootView?.findViewById<TextView>(R.id.challenge_title)

        val model = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)

        title = model.getMessage()
        Log.d("test getMessage", "현재 챌린지 페이지 이름은 " + title.toString())
        challengeTitle?.text = title

        getChallengeId()

//        adapter.addItem(Record(1, "제목", R.drawable.ttcwhite))
//        adapter.addItem(Record(2, "제목", R.drawable.ttcwhite))
//        adapter.addItem(Record(3, "제목", R.drawable.ttcwhite))
//        adapter.addItem(Record(4, "제목", R.drawable.ttcwhite))

        listView?.setAdapter(adapter)
        rootView?.findViewById<GridView>(R.id.recordList)?.adapter = adapter

        return rootView
    }

    /* 그리드뷰 어댑터 */
    inner class GridViewAdapter(val context: Context, val list: ArrayList<Record>) : BaseAdapter() {
        override fun getCount(): Int { return list.size }

        fun addItem(item: Record) { list.add(item) }
        override fun getItem(position: Int): Any { return list[position] }
        override fun getItemId(position: Int): Long { return position.toLong() }

        override fun getView(position: Int, convertview: View?, parent: ViewGroup?): View {
            val view: View
            val holder: ViewHolder

            if (convertview == null) {
                view = LayoutInflater.from(parent?.context).inflate(R.layout.gridview_record, parent, false)
                holder = ViewHolder()

                holder.rc_number = view.findViewById(R.id.recordNumber)
                holder.rc_title = view.findViewById(R.id.recordTitle)
                holder.rc_image = view.findViewById(R.id.recordImage)
                holder.back_record = view.findViewById(R.id.backRecord)

                holder.rc_number?.text = list[position].num.toString()
                holder.rc_title?.text = list[position].title
                list[position].resId?.let {holder.rc_image?.setImageResource(it)}

                holder.back_record?.setOnClickListener { (activity as NavActivity?)!!.replaceFragment(MainFragment()) }
                view.tag = holder
            } else {
                holder = convertview.tag as ViewHolder
                view = convertview
            }

            val recordItem = list[position]
            val resourceId = context.resources.getIdentifier(recordItem.resId.toString(), "drawable", context.packageName)
            holder.rc_image?.setImageResource(resourceId)

            return view //뷰 객체 반환
        }
        private inner class ViewHolder {
            var rc_number: TextView? = null
            var rc_title: TextView? = null
            var rc_image: ImageButton? = null
            var back_record: Button? = null
        }
    }

    private fun getChallengeId() {
        val url = "https://android-pkfbl.run.goorm.io/challenge/nameChallenge?nameChallenge=" + title
        val sr: StringRequest = object : StringRequest( Method.GET, url,
            Response.Listener { response: String? ->
                try {
                    val jsonArray = JSONArray(response)
                    val jsonObject = jsonArray.getJSONObject(0)
                    val idChallenge = jsonObject.getInt("idChallenge")
                    val nameChallenge = jsonObject.getString("nameChallenge")
                    val introduce = jsonObject.getString("intruduce")
                    val imageLink = jsonObject.getString("imageLink")
                    val frequency = jsonObject.getInt("frequency")
                    val possibleStartTime = jsonObject.getInt("possibleStartTime")
                    val possibleEndTime = jsonObject.getInt("possibleEndTime")
                    val count = jsonObject.getInt("count")
                    val countInterval = jsonObject.getString("countInterval")
                    val challengePostCount = jsonObject.getInt("challengePostCount")
                    val madeIdUser = jsonObject.getInt("madeIdUser")
                    val countUser = jsonObject.getInt("countUser")
                    val certificationWay = jsonObject.get("certificationWay")
                    val certificationWayImageLink = jsonObject.get("certificationWayImageLink")
                    val endDate = jsonObject.getString("endDate")
                    Log.d("test get challengeID", idChallenge.toString())
                    showMyList(idChallenge.toString())
                } catch (e: Exception) {
                    Log.e("Challenge name to id", response!!)
                }
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

    private fun showMyList(chID:String) {
        val myId = UserProfile.getId()
        val url = "https://android-pkfbl.run.goorm.io/challengePost/all?idUser=" + myId + "?idChallenge=" + chID
        val sr: StringRequest = object: StringRequest(Method.GET, url,
        Response.Listener{response: String? ->
            val recordList = ArrayList<Record>()
            try {
                val jsonArray = JSONArray(response)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val postPhoto = jsonObject.get("postPhoto")
                    val idUser = jsonObject.get("idUser")
                    val idChallenge_ = jsonObject.get("idChallenge")
                    Log.d("test", jsonObject.toString())
                }
            } catch (e: Exception) {
                Log.e("MyListJSON", response!!)
            }
            adapter = GridViewAdapter(requireContext(), recordList)
            myList?.setAdapter(adapter)
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