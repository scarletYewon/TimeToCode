package com.kmu.timetocode.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.storage.FirebaseStorage
import com.kmu.timetocode.add.AddChallenge
import com.kmu.timetocode.databinding.FragmentChallengeListBinding
import com.kmu.timetocode.login.UserProfile
import org.json.JSONArray
import java.io.IOException
import java.lang.IllegalStateException


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
        showNewList()

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
                        val count = jsonObject.getInt("countUser").toString()
                        val nameTagList = nameTag.split("%")
                        FirebaseStorage.getInstance().getReference().child("UserImages_" + nameTag + ".jpeg").downloadUrl.addOnSuccessListener{
                            uri ->
                            challengeListArray.add(ChallengeListModel(uri.toString(), nameTagList[0], madeName, count, nameTagList[1], nameTagList[2]))
                            var chAdapter = ChallengeListAdapter(requireContext(),challengeListArray)
                            binding.listChallenge.adapter = chAdapter
                        }.addOnFailureListener {
                            uri -> FirebaseStorage.getInstance().getReference().child("UserImages_" + nameTag).downloadUrl.addOnSuccessListener {
                                uri1 ->
                            challengeListArray.add(ChallengeListModel(uri1.toString(), nameTagList[0], madeName, count, nameTagList[1], nameTagList[2]))
                            var chAdapter = ChallengeListAdapter(requireContext(),challengeListArray)
                            binding.listChallenge.adapter = chAdapter

                        }.addOnFailureListener {
                            uri1 -> FirebaseStorage.getInstance().getReference().child("UserImages_" + nameTag + ".jpg").downloadUrl.addOnSuccessListener{
                                uri2 ->
                            challengeListArray.add(ChallengeListModel(uri2.toString(), nameTagList[0], madeName, count, nameTagList[1], nameTagList[2]))
                            var chAdapter = ChallengeListAdapter(requireContext(),challengeListArray)
                            binding.listChallenge.adapter = chAdapter
                        }

                        }
                        }
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
                    for(i in jsonArray.length()-1 downTo 0){
                        val jsonObject = jsonArray.getJSONObject(i)
                        val nameTag = jsonObject.getString("nameChallenge")
                        val name = jsonObject.getString("nameChallenge").split("%")[0]
                        val tag = jsonObject.getString("nameChallenge").split("%")[1]
                        val tag2 = jsonObject.getString("nameChallenge").split("%")[2]
                        val whoMade = jsonObject.getString("name")
                        Log.i("여기는 new","시작은 : ${jsonArray.length()-1}, 현재 위치는: ${i}, 현재 챌린지는: ${name}")

                        var load = false
                        var loadRe = "F"
                        var load1 = "F1"
                        var load2 = "F2"
                        var load3 = "F3"

                        if (!load){FirebaseStorage.getInstance().getReference().child("UserImages_" + nameTag + ".jpg").downloadUrl.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                challengeItemArray.add(
                                    ChallengeItemModel(
                                        task.result.toString(),
                                        name,
                                        tag,
                                        tag2,
                                        whoMade
                                    )
                                )
                                var newAdapter =
                                    ChallengeItemAdapter(requireContext(), challengeItemArray)
                                binding.listNew.adapter = newAdapter
                                load = true
                                loadRe = "T"
                                load1 = "T1"
                                Log.i("여기는 new의 이미지1", "챌린지 : ${name} ")
                                Log.i("여기는 new의 이미지1", "new의 이미지 : ${task.result.toString()} ")
                            } else {
                                load = false
                                loadRe = "F1"
                                load1 = "F1"
                                Log.i("여기는 new의 이미지1의 실패", "챌린지 : ${load}:${loadRe}:${name} ")
                            }
                        }
                        }else{load = false}

                        if (!load){
                            FirebaseStorage.getInstance().getReference().child("UserImages_" + nameTag + ".jpeg").downloadUrl.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    challengeItemArray.add(
                                        ChallengeItemModel(
                                            task.result.toString(),
                                            name,
                                            tag,
                                            tag2,
                                            whoMade
                                        )
                                    )
                                    var newAdapter =
                                        ChallengeItemAdapter(requireContext(), challengeItemArray)
                                    binding.listNew.adapter = newAdapter
                                    load = true
                                    load2 = "T2"
                                    Log.i("여기는 new의 이미지2", "챌린지 : ${load}:${loadRe}:${name} ")
                                    Log.i("여기는 new의 이미지2", "new의 이미지 : ${task.result.toString()} ")
                                } else {
                                    load2 = "F2"
                                    Log.i("여기는 new의 이미지2의 실패", "챌린지 : ${load}:${loadRe}:${name} ")
                                }
                            }

                        }

                        if(!load){
                            FirebaseStorage.getInstance().getReference().child("UserImages_" + nameTag).downloadUrl.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    challengeItemArray.add(
                                        ChallengeItemModel(
                                            task.result.toString(),
                                            name,
                                            tag,
                                            tag2,
                                            whoMade
                                        )
                                    )
                                    var newAdapter =
                                        ChallengeItemAdapter(requireContext(), challengeItemArray)
                                    binding.listNew.adapter = newAdapter
                                    load = true
                                    load3 = "T3"
                                    Log.i("여기는 new의 이미지3", "챌린지 : ${load}:${loadRe}:${name} ")
                                    Log.i("여기는 new의 이미지3", "new의 이미지 : ${task.result.toString()} ")
                                } else {
                                    load3 = "F3"
                                    Log.i("여기는 new의 이미지3의 실패", "챌린지 : ${load}:${loadRe}:${name} ")

                                }
                            }
                        }


//                        FirebaseStorage.getInstance().getReference().child("UserImages_" + nameTag + ".jpg").downloadUrl.addOnCompleteListener(
//                            OnCompleteListener { task ->
//                            if(task.isSuccessful) {
//                                challengeItemArray.add(ChallengeItemModel(task.result.toString(), name, tag, tag2,whoMade))
//                                var newAdapter = ChallengeItemAdapter(requireContext(),challengeItemArray)
//                                binding.listNew.adapter = newAdapter
//                                Log.i("여기는 new의 이미지1", "챌린지 : ${name} ")
//                                Log.i("여기는 new의 이미지1", "new의 이미지 : ${task.result.toString()} ")
//                            } else {
//                                Log.i("여기는 new의 이미지1의 실패", "챌린지 : ${name} ")
//                            }
//                        })
//                        FirebaseStorage.getInstance().getReference().child("UserImages_" + nameTag + ".jpeg").downloadUrl.addOnCompleteListener(
//                            OnCompleteListener { task ->
//                                if(task.isSuccessful) {
//                                    challengeItemArray.add(ChallengeItemModel(task.result.toString(), name, tag, tag2,whoMade))
//                                    var newAdapter = ChallengeItemAdapter(requireContext(),challengeItemArray)
//                                    binding.listNew.adapter = newAdapter
//                                    Log.i("여기는 new의 이미지2", "챌린지 : ${name} ")
//                                    Log.i("여기는 new의 이미지2", "new의 이미지 : ${task.result.toString()} ")
//                                } else {
//
//                                    Log.i("여기는 new의 이미지2의 실패", "챌린지 : ${name} ")
//                                }
//                            })
//                        FirebaseStorage.getInstance().getReference().child("UserImages_" + nameTag).downloadUrl.addOnCompleteListener(
//                            OnCompleteListener { task ->
//                                if(task.isSuccessful) {
//                                    challengeItemArray.add(ChallengeItemModel(task.result.toString(), name, tag, tag2,whoMade))
//                                    var newAdapter = ChallengeItemAdapter(requireContext(),challengeItemArray)
//                                    binding.listNew.adapter = newAdapter
//                                    Log.i("여기는 new의 이미지3", "챌린지 : ${name} ")
//                                    Log.i("여기는 new의 이미지3", "new의 이미지 : ${task.result.toString()} ")
//                                } else {
//                                    Log.i("여기는 new의 이미지3의 실패", "챌린지 : ${name} ")
//                                }
//                            })

//                        FirebaseStorage.getInstance().getReference().child("UserImages_" + nameTag).downloadUrl.addOnCompleteListener {
//                            if (it.isSuccessful){
//                                challengeItemArray.add(ChallengeItemModel(it.toString(), name, tag, tag2,whoMade))
//                                var newAdapter = ChallengeItemAdapter(requireContext(),challengeItemArray)
//                                binding.listNew.adapter = newAdapter
//                                Log.i("여기는 new의 이미지3", "챌린지 : ${name} ")
//                                Log.i("여기는 new의 이미지3", "new의 이미지 : ${it.toString()} ")
//                            }
//                        }

//
//                    if (jsonArray.length() < 6){
//                        for (i in jsonArray.length()-1 downTo 0){
//                            val jsonObject = jsonArray.getJSONObject(i)
//                            val nameTag = jsonObject.getString("nameChallenge")
//                            val imageLink = FirebaseStorage.getInstance().getReference().child("UserImages_" + nameTag).downloadUrl.addOnSuccessListener {
//                                Log.d("Firebase", "사진 가져옴3")
//                            }.toString()
//
//                            val name = jsonObject.getString("nameChallenge").split("%")[0]
//                            val tag = jsonObject.getString("nameChallenge").split("%")[1]
//                            val tag2 = jsonObject.getString("nameChallenge").split("%")[2]
//                            val madeName = jsonObject.getString("name")
////                            var imageLink = FirebaseStorage.getInstance().getReference()
////                                .child("UserImages_" + nameTag + ".jpeg").downloadUrl.addOnSuccessListener { uri ->
////                                    challengeItemArray.add(ChallengeItemModel(
////                                            uri,
////                                            name,
////                                            tag,
////                                            tag2,
////                                            madeName
////                                        )
////                                    )
////
////                                    var newAdapter = ChallengeItemAdapter(requireContext(),challengeItemArray)
////                                    binding.listNew.adapter = newAdapter
////                                }.toString()
//                            challengeItemArray.add(ChallengeItemModel(imageLink,name,tag,tag2,madeName))
//                            Log.i("item","아이템??: ${challengeItemArray}")
//                        }
//                    }else{
//                        for (i in jsonArray.length()-1 downTo jsonArray.length()-7){
//                            val jsonObject = jsonArray.getJSONObject(i)
//                            val nameTag = jsonObject.getString("nameChallenge")
//                            val name = jsonObject.getString("nameChallenge").split("%")[0]
//                            val imageLink = FirebaseStorage.getInstance().getReference().child("UserImages_" + nameTag).downloadUrl.addOnSuccessListener {
//                                Log.d("Firebase", "사진 가져옴4")
//                            }.toString()
//                            val tag = jsonObject.getString("nameChallenge").split("%")[1]
//                            val tag2 = jsonObject.getString("nameChallenge").split("%")[2]
//                            val madeName = jsonObject.getString("name")
////                            var imageLink = FirebaseStorage.getInstance().getReference()
////                                .child("UserImages_" + nameTag + ".jpeg").downloadUrl.addOnSuccessListener { uri ->
////                                    challengeItemArray.add(ChallengeItemModel(
////                                        uri,
////                                        name,
////                                        tag,
////                                        tag2,
////                                        madeName
////                                    )
////                                    )
////
////                                    var newAdapter = ChallengeItemAdapter(requireContext(),challengeItemArray)
////                                    binding.listNew.adapter = newAdapter
////                                }.toString()
////
////
                    }

            }catch (e: IllegalStateException){
                    Log.e("StorageException", "찾을 수 없음")
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



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}