package com.kmu.timetocode.recordcenter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.kmu.timetocode.MyViewModel
import com.kmu.timetocode.NavActivity
import com.kmu.timetocode.R
import com.kmu.timetocode.certicenter.CertificationFragment
import com.kmu.timetocode.login.UserProfile
import org.json.JSONArray
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class RecordFragment : Fragment() {
    var adapter : GridViewAdapter?=null
    var queue: RequestQueue?=null
    var myList: GridView?=null
    var title: String?=null
    var now = LocalDate.now().format(DateTimeFormatter.ofPattern("MMdd"))
    var challengeId : String?=null

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
        val tmpChallenge = rootView?.findViewById<ImageView>(R.id.tmpChallengeImage)

        val model = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)

        val fullName = model.getMessage()
        title = fullName?.split("%")?.get(0)
        Log.d("test getMessage", "현재 챌린지 페이지 이름은 " + title.toString())
        challengeTitle?.text = title

        getChallengeId()

//        adapter?.addItem(Record(1, R.drawable.ttcwhite))
//        adapter?.addItem(Record(2, R.drawable.ttcwhite))
//        adapter?.addItem(Record(3, R.drawable.ttcwhite))
//        adapter?.addItem(Record(4, R.drawable.ttcwhite))

        listView?.setAdapter(adapter)
        rootView?.findViewById<GridView>(R.id.recordList)?.adapter = adapter

        tmpChallenge?.setImageResource(R.drawable.ttcwhite)

        val storage: FirebaseStorage = FirebaseStorage.getInstance("gs://timetocode-13747.appspot.com/")
        val fileExt = arrayOf(".jpeg", ".jpg", "")

        for(i in fileExt)
            storage.getReference().child("UserImages_" + fullName + i).downloadUrl
                .addOnSuccessListener { uri ->
                    Glide.with(requireActivity().getApplicationContext()).load(uri).into(tmpChallenge!!)
                    Log.d("기록센터 사진 불러오기", uri.toString())
                }

        var recordList:ArrayList<Record>



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

                holder.rc_title = view.findViewById(R.id.recordTitle)
                holder.rc_image = view.findViewById(R.id.recordImage)


                holder.rc_title?.text = list[position].chId.toString()
                list[position].resId?.let {holder.rc_image?.setImageResource(it)}

                view.tag = holder
            } else {
                holder = convertview.tag as ViewHolder
                view = convertview
            }

            val recordItem = list[position]
            val resourceId = context.resources.getIdentifier(recordItem.resId.toString(), "drawable", context.packageName)
            holder.rc_image?.setImageResource(resourceId)
            val storage: FirebaseStorage = FirebaseStorage.getInstance("gs://timetocode-13747.appspot.com/")
            val fileExt = arrayOf(".jpeg", ".jpg", "")
            for(i in fileExt)
                storage.getReference().child("UserImages_" + UserProfile.getId().toString()+ "%" + title + "%" + now).downloadUrl
                    .addOnSuccessListener { uri ->
                        Glide.with(requireActivity().getApplicationContext()).load(uri).into(holder.rc_image!!)
                        Log.d("기록 사진 불러오기", uri.toString())
                        }

            return view //뷰 객체 반환
        }
        private inner class ViewHolder {
            var rc_title: TextView? = null
            var rc_image: ImageButton? = null
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
        val url = "https://android-pkfbl.run.goorm.io/challengePost/all?idUser=$myId&idChallenge=$chID"
        val sr: StringRequest = object: StringRequest(Method.GET, url,
        Response.Listener{response: String? ->
            val recordList = ArrayList<Record>()
            try {
                val jsonArray = JSONArray(response)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    Log.d("test record list in recordCenter", jsonObject.toString())
                    val postPhoto = jsonObject.get("postPhoto")
                    val idUser = jsonObject.get("idUser")
                    val idChallenge = jsonObject.getInt("idChallenge")
                    recordList.add(Record(idChallenge, R.drawable.ttcwhite))
                    challengeId = idChallenge.toString()
                }
            } catch (e: Exception) {
                Log.e("MyRecordJSON", response!!)
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