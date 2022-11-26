package com.kmu.timetocode.list

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.kmu.timetocode.add.AddChallenge
import com.kmu.timetocode.databinding.FragmentChallengeListBinding
import com.kmu.timetocode.login.UserProfile
import org.json.JSONArray


class FragmentChallengeList : Fragment() {


    var queue: RequestQueue?=null
    var challengeListAdapter: ChallengeListAdapter? = null
    var challengeListArray: ArrayList<ChallengeListModel> = ArrayList()

    private var _binding: FragmentChallengeListBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChallengeListBinding.inflate(inflater, container, false)


        val challengeItemArray = arrayListOf<ChallengeItemModel>(
            ChallengeItemModel("a","챌린지 이름","github"),
            ChallengeItemModel("a","챌린지 이름","github"),
            ChallengeItemModel("a","챌린지 이름","github"),
            ChallengeItemModel("a","챌린지 이름","github"),
            ChallengeItemModel("a","챌린지 이름","github"),
            ChallengeItemModel("a","챌린지 이름","github")
        )
//        val challengeListArray = arrayListOf<ChallengeListModel>(
//            ChallengeListModel("a","챌린지 이름","생성자","60","github", "알고리즘"),
//            ChallengeListModel("a","챌린지 이름","생성자","60","github", "알고리즘"),
//            ChallengeListModel("a","챌린지 이름","생성자","60","github", "알고리즘"),
//            ChallengeListModel("a","챌린지 이름","생성자","60","github", "알고리즘"),
//            ChallengeListModel("a","챌린지 이름","생성자","60","github", "알고리즘"),
//            ChallengeListModel("a","챌린지 이름","생성자","60","github", "알고리즘")
//
//        )


        binding.listChallenge.setHasFixedSize(true)

        var manager = LinearLayoutManager(requireContext())
        binding.listChallenge.layoutManager = manager

        var chAdapter = ChallengeListAdapter(requireContext(),challengeListArray)

        binding.listChallenge.adapter = chAdapter
        showAllList()

        binding.listNew.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.listNew.adapter = ChallengeItemAdapter(requireContext(),challengeItemArray)

        binding.btnGoAddChallenge.setOnClickListener{
            val intent = Intent(context, AddChallenge::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun showAllList() {
        val myId = UserProfile.getId()
        val url = "https://android-pkfbl.run.goorm.io/challenge/allChallengeDataList"
        val sr = StringRequest(
            Request.Method.GET, url,
            { response: String? ->
                challengeListArray =
                    ArrayList<ChallengeListModel>()
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val nameTag = jsonObject.getString("nameChallenge")
                        val imageLink = jsonObject.getString("imageLink")
                        val madeName = jsonObject.getString("name")
                        val count = jsonObject.getInt("countUser")
                        val nameTagList = nameTag.split("%")
                        challengeListArray.add(
                            ChallengeListModel(
                                imageLink,
                                nameTagList[0],
                                madeName,
                                Integer.toString(count),
                                nameTagList[1],
                                nameTagList[2]
                            )
                        )
                    }
                } catch (e: Exception) {
                    Log.e("SearchListJSON", response!!)
                }
                challengeListAdapter = ChallengeListAdapter(requireContext(), challengeListArray)
                binding.listChallenge.adapter= challengeListAdapter
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