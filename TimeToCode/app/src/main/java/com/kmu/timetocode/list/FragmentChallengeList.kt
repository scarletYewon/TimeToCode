package com.kmu.timetocode.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.kmu.timetocode.add.AddChallenge
import com.kmu.timetocode.databinding.FragmentChallengeListBinding
import com.kmu.timetocode.login.UserProfile
import org.json.JSONArray


class FragmentChallengeList : Fragment() {

//    private lateinit var tadapter : ChallengeListAdapter
    private lateinit var recyclerView: RecyclerView
//    private lateinit var challengeListArray : ArrayList<ChallengeListModel>





    var queue: RequestQueue?=null
    var challengeListAdapter: ChallengeListAdapter? = null
    var challengeListArray: ArrayList<ChallengeListModel> = ArrayList()
    var challengeItemArray: ArrayList<ChallengeItemModel> = ArrayList()

    private var _binding: FragmentChallengeListBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChallengeListBinding.inflate(inflater, container, false)


//        val challengeItemArray = arrayListOf<ChallengeItemModel>(
//            ChallengeItemModel("a","챌린지 이름","github"),
//            ChallengeItemModel("a","챌린지 이름","github"),
//            ChallengeItemModel("a","챌린지 이름","github"),
//            ChallengeItemModel("a","챌린지 이름","github"),
//            ChallengeItemModel("a","챌린지 이름","github"),
//            ChallengeItemModel("a","챌린지 이름","github")
//        )
//        val challengeListArray = arrayListOf<ChallengeListModel>(
//            ChallengeListModel("a","챌린지 이름","생성자","60","github", "알고리즘"),
//            ChallengeListModel("a","챌린지 이름","생성자","60","github", "알고리즘"),
//            ChallengeListModel("a","챌린지 이름","생성자","60","github", "알고리즘"),
//            ChallengeListModel("a","챌린지 이름","생성자","60","github", "알고리즘"),
//            ChallengeListModel("a","챌린지 이름","생성자","60","github", "알고리즘"),
//            ChallengeListModel("a","챌린지 이름","생성자","60","github", "알고리즘")
//
//        )


        showAllList()

        binding.listChallenge.setHasFixedSize(true)

        var manager = LinearLayoutManager(requireContext())
        binding.listChallenge.layoutManager = manager

        var chAdapter = ChallengeListAdapter(requireContext(),challengeListArray)

        binding.listChallenge.adapter = chAdapter
//        showAllList()

//        chAdapter.setOnItemClickListener(object : ChallengeListAdapter.OnItemClickListener{
//            override fun onItemClick(v: View, data: ChallengeListModel, pos : Int) {
//                Intent(this@MainActivity, ProfileDetailActivity::class.java).apply {
//                    putExtra("data", data)
//                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                }.run { startActivity(this) }
//            }
//
//        })


//        showNewList()
        binding. listNew.setHasFixedSize(true)

        var newManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.listNew.layoutManager = newManager

        var newAdapter = ChallengeItemAdapter(requireContext(),challengeItemArray)
        binding.listNew.adapter = newAdapter

//        binding.listNew.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
//        binding.listNew.adapter = ChallengeItemAdapter(requireContext(),challengeItemArray)

        binding.btnGoAddChallenge.setOnClickListener{
            val intent = Intent(context, AddChallenge::class.java)
            startActivity(intent)
        }
        return binding.root
    }



    private fun showAllList() {
        val url = "https://android-pkfbl.run.goorm.io/challenge/allChallengeDataList"
        val sr = StringRequest(
            Request.Method.GET, url,
            { response: String? ->
                challengeListArray =
                    ArrayList<ChallengeListModel>()
                challengeItemArray = ArrayList<ChallengeItemModel>()
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val nameTag = jsonObject.getString("nameChallenge")
                        val madeName = jsonObject.getString("name")
                        val count = jsonObject.getInt("countUser")
                        val nameTagList = nameTag.split("%")
                        FirebaseStorage.getInstance().getReference().child("UserImages_" + nameTag + ".jpeg").downloadUrl.addOnSuccessListener{
                            uri ->
                            challengeListArray.add(ChallengeListModel(uri.toString(), nameTagList[0], madeName, Integer.toString(count), nameTagList[1], nameTagList[2]))
                            var chAdapter = ChallengeListAdapter(requireContext(),challengeListArray)
                            binding.listChallenge.adapter = chAdapter
                        }.addOnFailureListener {
                            uri -> FirebaseStorage.getInstance().getReference().child("UserImages_" + nameTag).downloadUrl.addOnSuccessListener {
                                uri ->
                            challengeListArray.add(ChallengeListModel(uri.toString(), nameTagList[0], madeName, Integer.toString(count), nameTagList[1], nameTagList[2]))
                            var chAdapter = ChallengeListAdapter(requireContext(),challengeListArray)
                            binding.listChallenge.adapter = chAdapter

                        }.addOnFailureListener {
                            uri -> FirebaseStorage.getInstance().getReference().child("UserImages_" + nameTag + ".jpg").downloadUrl.addOnSuccessListener{
                                uri ->
                            challengeListArray.add(ChallengeListModel(uri.toString(), nameTagList[0], madeName, Integer.toString(count), nameTagList[1], nameTagList[2]))
                            var chAdapter = ChallengeListAdapter(requireContext(),challengeListArray)
                            binding.listChallenge.adapter = chAdapter
                        }

                        }
                        }
                    }

                    for(i in jsonArray.length()-1 downTo 0){
                        val jsonObject = jsonArray.getJSONObject(i)
                        val nameTag = jsonObject.getString("nameChallenge")
                        val name = jsonObject.getString("nameChallenge").split("%")[0]
                        val tag = jsonObject.getString("nameChallenge").split("%")[1]
                        val tag2 = jsonObject.getString("nameChallenge").split("%")[2]
                        val whoMade = jsonObject.getString("name")
                        FirebaseStorage.getInstance().getReference().child("UserImages_" + nameTag + ".jpeg").downloadUrl.addOnSuccessListener{
                                uri ->
                            challengeItemArray.add(ChallengeItemModel(uri.toString(), name, tag, tag2,whoMade))
                            var newAdapter = ChallengeItemAdapter(requireContext(),challengeItemArray)
                            binding.listNew.adapter = newAdapter
                        }.addOnFailureListener {
                                uri -> FirebaseStorage.getInstance().getReference().child("UserImages_" + nameTag).downloadUrl.addOnSuccessListener {
                                uri ->
                            challengeItemArray.add(ChallengeItemModel(uri.toString(), name, tag, tag2,whoMade))
                            var newAdapter = ChallengeItemAdapter(requireContext(),challengeItemArray)
                            binding.listNew.adapter = newAdapter

                        }.addOnFailureListener {
                                uri -> FirebaseStorage.getInstance().getReference().child("UserImages_" + nameTag + ".jpg").downloadUrl.addOnSuccessListener{
                                uri2 ->
                            challengeItemArray.add(ChallengeItemModel(uri2.toString(), name, tag, tag2,whoMade))
                            var newAdapter = ChallengeItemAdapter(requireContext(),challengeItemArray)
                            binding.listNew.adapter = newAdapter
                        }
                        }
                        }



//                    if (jsonArray.length() < 6){
//                        for (i in jsonArray.length()-1 downTo 0){
//                            val jsonObject = jsonArray.getJSONObject(i)
//                            val name = jsonObject.getString("nameChallenge").split("%")[0]
//                            val imageLink = jsonObject.getString("imageLink")
//                            val tag = jsonObject.getString("nameChallenge").split("%")[1]
//                            val tag2 = jsonObject.getString("nameChallenge").split("%")[2]
//                            val whoMade = jsonObject.getString("name")
//                            challengeItemArray.add(ChallengeItemModel(imageLink.toUri(),name,tag,tag2,whoMade))
//                            Log.i("item","아이템: ${challengeItemArray}")
//                        }
//                    }else{
//                        for (i in jsonArray.length()-1 downTo jsonArray.length()-7){
//                            val jsonObject = jsonArray.getJSONObject(i)
//                            val nameTag = jsonObject.getString("nameChallenge")
//                            val name = jsonObject.getString("nameChallenge").split("%")[0]
//
//                            val tag = jsonObject.getString("nameChallenge").split("%")[1]
//                            val tag2 = jsonObject.getString("nameChallenge").split("%")[2]
//                            val whoMade = jsonObject.getString("name")
//                            var imageLink = FirebaseStorage.getInstance().getReference()
//                                .child("UserImages_" + nameTag + ".jpeg").downloadUrl.addOnSuccessListener { uri ->
//                                    challengeItemArray.add(ChallengeItemModel(
//                                            uri,
//                                            name,
//                                            tag,
//                                            tag2,
//                                        whoMade
//                                        )
//                                    )
//
//                                    var newAdapter = ChallengeItemAdapter(requireContext(),challengeItemArray)
//                                    binding.listNew.adapter = newAdapter
//                                }.toString()
//                            challengeItemArray.add(ChallengeItemModel(imageLink.toUri(),name,tag,tag2,whoMade))
                            Log.i("item","아이템?: ${challengeItemArray}")
                        }


                } catch (e: Exception) {
                    Log.e("SearchListJSON", response!!)
                }
//                var manager = LinearLayoutManager(requireContext())
//
//                binding.listChallenge.layoutManager = manager
//                binding.listChallenge.setHasFixedSize(true)

//                var chAdapter = ChallengeListAdapter(requireContext(),challengeListArray)
//
//                binding.listChallenge.adapter = chAdapter
                Log.i("imageAdd","이미지addlist확인: ${challengeListArray}")



            }
        ) { error: VolleyError ->
            Log.e(
                "ChallengeList",
                "ee"
            )
        }
        sr.setShouldCache(false)
        queue = Volley.newRequestQueue(requireContext())
        queue!!.add(sr)
    }

    private fun showNewList() {
        val myId = UserProfile.getId()
        val url = "https://android-pkfbl.run.goorm.io/challenge/allChallengeDataList"
        val sr = StringRequest(
            Request.Method.GET, url,
            { response: String? ->
                challengeItemArray = ArrayList<ChallengeItemModel>()
                try {
                    val jsonArray = JSONArray(response)
                    if (jsonArray.length() < 6){
                        for (i in jsonArray.length()-1 downTo 0){
                            val jsonObject = jsonArray.getJSONObject(i)
                            val nameTag = jsonObject.getString("nameChallenge")
                            val imageLink = FirebaseStorage.getInstance().getReference().child("UserImages_" + nameTag).downloadUrl.addOnSuccessListener {
                                Log.d("Firebase", "사진 가져옴3")
                            }.toString()

                            val name = jsonObject.getString("nameChallenge").split("%")[0]
                            val tag = jsonObject.getString("nameChallenge").split("%")[1]
                            val tag2 = jsonObject.getString("nameChallenge").split("%")[2]
                            val madeName = jsonObject.getString("name")
//                            var imageLink = FirebaseStorage.getInstance().getReference()
//                                .child("UserImages_" + nameTag + ".jpeg").downloadUrl.addOnSuccessListener { uri ->
//                                    challengeItemArray.add(ChallengeItemModel(
//                                            uri,
//                                            name,
//                                            tag,
//                                            tag2,
//                                            madeName
//                                        )
//                                    )
//
//                                    var newAdapter = ChallengeItemAdapter(requireContext(),challengeItemArray)
//                                    binding.listNew.adapter = newAdapter
//                                }.toString()
                            challengeItemArray.add(ChallengeItemModel(imageLink,name,tag,tag2,madeName))
                            Log.i("item","아이템??: ${challengeItemArray}")
                        }
                    }else{
                        for (i in jsonArray.length()-1 downTo jsonArray.length()-7){
                            val jsonObject = jsonArray.getJSONObject(i)
                            val nameTag = jsonObject.getString("nameChallenge")
                            val name = jsonObject.getString("nameChallenge").split("%")[0]
                            val imageLink = FirebaseStorage.getInstance().getReference().child("UserImages_" + nameTag).downloadUrl.addOnSuccessListener {
                                Log.d("Firebase", "사진 가져옴4")
                            }.toString()
                            val tag = jsonObject.getString("nameChallenge").split("%")[1]
                            val tag2 = jsonObject.getString("nameChallenge").split("%")[2]
                            val madeName = jsonObject.getString("name")
//                            var imageLink = FirebaseStorage.getInstance().getReference()
//                                .child("UserImages_" + nameTag + ".jpeg").downloadUrl.addOnSuccessListener { uri ->
//                                    challengeItemArray.add(ChallengeItemModel(
//                                        uri,
//                                        name,
//                                        tag,
//                                        tag2,
//                                        madeName
//                                    )
//                                    )
//
//                                    var newAdapter = ChallengeItemAdapter(requireContext(),challengeItemArray)
//                                    binding.listNew.adapter = newAdapter
//                                }.toString()
//                            if (imageLink.isNullOrEmpty()) {
//                            imageLink = FirebaseStorage.getInstance().getReference()
//                                .child("UserImages_" + nameTag).downloadUrl.addOnSuccessListener { uri ->
//                                    challengeItemArray.add(ChallengeItemModel(
//                                        uri,
//                                        name,
//                                        tag,
//                                        tag2,
//                                        madeName
//                                    )
//                                    )
//
//                                    var chAdapter = ChallengeListAdapter(requireContext(),challengeListArray)
//
//                                    binding.listChallenge.adapter = chAdapter
//
//                                    var newAdapter = ChallengeItemAdapter(requireContext(),challengeItemArray)
//                                    binding.listNew.adapter = newAdapter
//                                }.toString()
//                        }
//                        if (imageLink.isNullOrEmpty()) {
//                            imageLink = FirebaseStorage.getInstance().getReference()
//                                .child("UserImages_" + nameTag + ".jpg").downloadUrl.addOnSuccessListener { uri ->
//
//                                    challengeItemArray.add(ChallengeItemModel(
//                                        uri,
//                                        name,
//                                        tag,
//                                        tag2,
//                                        madeName
//                                    )
//                                    )
//
//                                    var chAdapter = ChallengeListAdapter(requireContext(),challengeListArray)
//
//                                    binding.listChallenge.adapter = chAdapter
//
//                                    var newAdapter = ChallengeItemAdapter(requireContext(),challengeItemArray)
//                                    binding.listNew.adapter = newAdapter
//                                }.toString()}

                            challengeItemArray.add(ChallengeItemModel(imageLink,name,tag,tag2,madeName))
                            Log.i("item","아이템???: ${challengeItemArray}")
                        }
                    }
                } catch (e: Exception) {
                    Log.e("SearchListJSON", response!!)
                }
                var newAdapter = ChallengeItemAdapter(requireContext(),challengeItemArray)
                binding.listNew.adapter = newAdapter
            }
        ) { error: VolleyError ->
            Log.e(
                "searchList",
                "ee"
            )
        }
        sr.setShouldCache(false)
        queue = Volley.newRequestQueue(requireContext())
        queue!!.add(sr)
    }

//    TODO: 찜 버튼 클릭 시 버튼 이미지 변경

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}