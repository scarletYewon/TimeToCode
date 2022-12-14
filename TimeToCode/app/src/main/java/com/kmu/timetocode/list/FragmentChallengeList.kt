package com.kmu.timetocode.list

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import com.kmu.timetocode.login.CustomProgressDialog
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

    var dialog: CustomProgressDialog? = null
    var blur: View? = null

    var listCount : Int = 0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChallengeListBinding.inflate(inflater, container, false)

        blur = binding.blur
        dialog = CustomProgressDialog(requireContext())
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGoAddChallenge.setOnClickListener{
            val intent = Intent(context, AddChallenge::class.java)
            startActivity(intent)
        }

        blur!!.visibility = View.VISIBLE
        dialog!!.show()

        showAllList()
        showNewList()

        binding.listChallenge.setHasFixedSize(true)

        var manager = LinearLayoutManager(requireContext())
        binding.listChallenge.layoutManager = manager

        var chAdapter = ChallengeListAdapter(requireContext(),challengeListArray)

        binding.listChallenge.adapter = chAdapter

        binding. listNew.setHasFixedSize(true)

        var newManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.listNew.layoutManager = newManager

        var newAdapter = ChallengeItemAdapter(requireContext(),challengeItemArray)
        binding.listNew.adapter = newAdapter
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
                        listCount = 0
                        FirebaseStorage.getInstance().getReference().child("UserImages_" + nameTag + ".jpeg").downloadUrl.addOnSuccessListener{
                            uri ->
                            challengeListArray.add(ChallengeListModel(uri.toString(), nameTagList[0], madeName, count, nameTagList[1], nameTagList[2]))
                            var chAdapter = ChallengeListAdapter(requireContext(),challengeListArray)
                            binding.listChallenge.adapter = chAdapter
                            listCount += 1
                            if(jsonArray.length()-1 == listCount){
                                blur!!.visibility = View.INVISIBLE
                                dialog!!.dismiss()
                            }

                        }.addOnFailureListener {
                            uri -> FirebaseStorage.getInstance().getReference().child("UserImages_" + nameTag).downloadUrl.addOnSuccessListener {
                                uri1 ->
                            challengeListArray.add(ChallengeListModel(uri1.toString(), nameTagList[0], madeName, count, nameTagList[1], nameTagList[2]))
                            var chAdapter = ChallengeListAdapter(requireContext(),challengeListArray)
                            binding.listChallenge.adapter = chAdapter
                            listCount += 1
                            if(jsonArray.length()-1 == listCount){
                                blur!!.visibility = View.INVISIBLE
                                dialog!!.dismiss()
                            }

                        }.addOnFailureListener {
                            uri1 -> FirebaseStorage.getInstance().getReference().child("UserImages_" + nameTag + ".jpg").downloadUrl.addOnSuccessListener{
                                uri2 ->
                            challengeListArray.add(ChallengeListModel(uri2.toString(), nameTagList[0], madeName, count, nameTagList[1], nameTagList[2]))
                            var chAdapter = ChallengeListAdapter(requireContext(),challengeListArray)
                            binding.listChallenge.adapter = chAdapter
                            listCount += 1
                            if(jsonArray.length()-1 == listCount){
                                blur!!.visibility = View.INVISIBLE
                                dialog!!.dismiss()
                            }
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
                Log.i("imageAdd","?????????addlist??????: ${challengeListArray}")



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
                    if(jsonArray.length() > 5){
                        for(i in jsonArray.length()-1 downTo jsonArray.length()-8){
                            val jsonObject = jsonArray.getJSONObject(i)
                            val nameTag = jsonObject.getString("nameChallenge")
                            val imageLink = jsonObject.getString("imageLink")
                            val name = jsonObject.getString("nameChallenge").split("%")[0]
                            val tag = jsonObject.getString("nameChallenge").split("%")[1]
                            val tag2 = jsonObject.getString("nameChallenge").split("%")[2]
                            val whoMade = jsonObject.getString("name")
                            Log.i("????????? new","????????? : ${jsonArray.length()-1}, ?????? ?????????: ${i}, ?????? ????????????: ${name}")


                            challengeItemArray.add(
                                ChallengeItemModel(
                                    imageLink,
                                    name,
                                    tag,
                                    tag2,
                                    whoMade, nameTag
                                )
                            )

                        }
                    }else{
                    for(i in jsonArray.length()-1 downTo 0){
                        val jsonObject = jsonArray.getJSONObject(i)
                        val nameTag = jsonObject.getString("nameChallenge")
                        val imageLink = jsonObject.getString("imageLink")
                        val name = jsonObject.getString("nameChallenge").split("%")[0]
                        val tag = jsonObject.getString("nameChallenge").split("%")[1]
                        val tag2 = jsonObject.getString("nameChallenge").split("%")[2]
                        val whoMade = jsonObject.getString("name")
                        Log.i("????????? new","????????? : ${jsonArray.length()-1}, ?????? ?????????: ${i}, ?????? ????????????: ${name}")


                        challengeItemArray.add(
                            ChallengeItemModel(
                                imageLink,
                                name,
                                tag,
                                tag2,
                                whoMade, nameTag
                            )
                        )

                    }
                    }
                    Log.i("ddd","????????????: ${challengeItemArray.size}")
                    for (i in 0 until  challengeItemArray.size-1){
                        Log.i("??????","?????????:${i}")
                        FirebaseStorage.getInstance().getReference().child("UserImages_" + challengeItemArray.get(i).imgName + ".jpeg").downloadUrl.addOnSuccessListener{
                                uri ->
                            challengeItemArray.set(i, ChallengeItemModel(uri.toString(),
                                challengeItemArray.get(i).title,
                                challengeItemArray.get(i).tag,
                                challengeItemArray.get(i).tag2,
                                challengeItemArray.get(i).whoMade,
                                challengeItemArray.get(i).imgName)

                            )
                            var newAdapter = ChallengeItemAdapter(requireContext(),challengeItemArray)
                            binding.listNew.adapter = newAdapter
                        }.addOnFailureListener {
                                uri -> FirebaseStorage.getInstance().getReference().child("UserImages_" + challengeItemArray.get(i).imgName).downloadUrl.addOnSuccessListener {
                                uri1 ->
                            challengeItemArray.set(i, ChallengeItemModel(uri1.toString(),
                                challengeItemArray.get(i).title,
                                challengeItemArray.get(i).tag,
                                challengeItemArray.get(i).tag2,
                                challengeItemArray.get(i).whoMade,
                                challengeItemArray.get(i).imgName)

                            )
                            var newAdapter = ChallengeItemAdapter(requireContext(),challengeItemArray)
                            binding.listNew.adapter = newAdapter

                        }.addOnFailureListener {
                                uri1 -> FirebaseStorage.getInstance().getReference().child("UserImages_" + challengeItemArray.get(i).imgName + ".jpg").downloadUrl.addOnSuccessListener{
                                uri2 ->
                            challengeItemArray.set(i, ChallengeItemModel(uri2.toString(),
                                challengeItemArray.get(i).title,
                                challengeItemArray.get(i).tag,
                                challengeItemArray.get(i).tag2,
                                challengeItemArray.get(i).whoMade,
                                challengeItemArray.get(i).imgName)

                            )
                            var newAdapter = ChallengeItemAdapter(requireContext(),challengeItemArray)
                            binding.listNew.adapter = newAdapter
                        }

                        }
                        }

                    }

            }catch (e: IllegalStateException){
                    Log.e("StorageException", "?????? ??? ??????")
            } catch (e: Exception) {
                    Log.e("SearchListJSON", response!!)
            }
                try{
                    Log.i("ddd","????????????: ${challengeItemArray.size}")
                    for (i in 0 until  challengeItemArray.size-1){
                        Log.i("??????","?????????:${i}")
                        FirebaseStorage.getInstance().getReference().child("UserImages_" + challengeItemArray.get(i).imgName + ".jpeg").downloadUrl.addOnSuccessListener{
                                uri ->
                            challengeItemArray.set(i, ChallengeItemModel(uri.toString(),
                                challengeItemArray.get(i).title,
                                challengeItemArray.get(i).tag,
                                challengeItemArray.get(i).tag2,
                                challengeItemArray.get(i).whoMade,
                                challengeItemArray.get(i).imgName)

                            )
                            var newAdapter = ChallengeItemAdapter(requireContext(),challengeItemArray)
                            binding.listNew.adapter = newAdapter
                        }.addOnFailureListener {
                                uri -> FirebaseStorage.getInstance().getReference().child("UserImages_" + challengeItemArray.get(i).imgName).downloadUrl.addOnSuccessListener {
                                uri1 ->
                            challengeItemArray.set(i, ChallengeItemModel(uri1.toString(),
                                challengeItemArray.get(i).title,
                                challengeItemArray.get(i).tag,
                                challengeItemArray.get(i).tag2,
                                challengeItemArray.get(i).whoMade,
                                challengeItemArray.get(i).imgName)

                            )
                            var newAdapter = ChallengeItemAdapter(requireContext(),challengeItemArray)
                            binding.listNew.adapter = newAdapter

                        }.addOnFailureListener {
                                uri1 -> FirebaseStorage.getInstance().getReference().child("UserImages_" + challengeItemArray.get(i).imgName + ".jpg").downloadUrl.addOnSuccessListener{
                                uri2 ->
                            challengeItemArray.set(i, ChallengeItemModel(uri2.toString(),
                                challengeItemArray.get(i).title,
                                challengeItemArray.get(i).tag,
                                challengeItemArray.get(i).tag2,
                                challengeItemArray.get(i).whoMade,
                                challengeItemArray.get(i).imgName)

                            )
                            var newAdapter = ChallengeItemAdapter(requireContext(),challengeItemArray)
                            binding.listNew.adapter = newAdapter
                        }

                        }
                        }
                    }
                }catch (e: Exception){
                    Log.e("StorageException", "?????? ??? ??????")
                }
                catch(e: IndexOutOfBoundsException){
                    Log.e("IndexOutException", "?????? ??? ??????")
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