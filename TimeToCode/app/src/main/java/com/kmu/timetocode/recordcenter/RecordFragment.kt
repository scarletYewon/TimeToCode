package com.kmu.timetocode.recordcenter

import android.content.Context
import android.net.Uri
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
    var photoList: ArrayList<Uri>?= ArrayList()

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


        adapter = photoList?.let { GridViewAdapter(requireContext(), it) }
        listView?.setAdapter(adapter)
        rootView?.findViewById<GridView>(R.id.recordList)?.adapter = adapter

        tmpChallenge?.setImageResource(R.drawable.ttcwhite)

        val storage: FirebaseStorage = FirebaseStorage.getInstance("gs://timetocode-13747.appspot.com/")
        val fileExt = arrayOf(".jpeg", ".jpg", "")

        for(i in fileExt)
            storage.getReference().child("UserImages_$fullName$i").downloadUrl
                .addOnSuccessListener { uri ->
                    Glide.with(requireActivity().getApplicationContext()).load(uri).into(tmpChallenge!!)
                    Log.d("기록센터 사진 불러오기", uri.toString())
                }

        val dd = "UserImages_" + UserProfile.getId().toString()+ "%" + title + "%" + now
        Log.e("ddddddd",dd)
//        val storage: FirebaseStorage = FirebaseStorage.getInstance("gs://timetocode-13747.appspot.com/")
        storage.getReference().child(dd ).downloadUrl
            .addOnSuccessListener { uri ->
                Log.e("photo", uri.toString())
                photoList?.add(uri)
                Log.e("photo", photoList?.get(0).toString())
            }
//            uri ->
//            Glide.with(requireActivity().getApplicationContext()).load(uri).into(holder.rc_image!!)
//            Log.d("기록 사진 불러오기", uri.toString())
//
//        var a = 5
//        for(i in 0..a){
//            storage.getReference().child(photoList?.get(i).toString()).downloadUrl
//                .addOnSuccessListener { uri ->
//                    Glide.with(requireActivity().getApplicationContext()).load(uri).into(GridViewAdapter(context,photoList))
//                    Log.d("dddddddddd", uri.toString())
//                }
//
//        }




        return rootView
    }

    /* 그리드뷰 어댑터 */
    inner class GridViewAdapter(val context: Context, val list: ArrayList<Uri>) : BaseAdapter() {
        override fun getCount(): Int { return list.size }

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

                view.tag = holder
            } else {
                holder = convertview.tag as ViewHolder
                view = convertview
            }


            return view //뷰 객체 반환
        }
        private inner class ViewHolder {
            var rc_title: TextView? = null
            var rc_image: ImageButton? = null
        }
    }
}