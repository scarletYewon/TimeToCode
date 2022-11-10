package com.kmu.timetocode

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kmu.timetocode.databinding.FragmentAddChallenge1Binding
import com.kmu.timetocode.databinding.FragmentChallengeListBinding

class FragmentChallengeList : Fragment() {


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
        val challengeListArray = arrayListOf<ChallengeListModel>(
            ChallengeListModel("a","챌린지 이름","생성자","60","github", "알고리즘"),
            ChallengeListModel("a","챌린지 이름","생성자","60","github", "알고리즘"),
            ChallengeListModel("a","챌린지 이름","생성자","60","github", "알고리즘"),
            ChallengeListModel("a","챌린지 이름","생성자","60","github", "알고리즘"),
            ChallengeListModel("a","챌린지 이름","생성자","60","github", "알고리즘"),
            ChallengeListModel("a","챌린지 이름","생성자","60","github", "알고리즘")

        )

        binding.listChallenge.layoutManager = LinearLayoutManager(requireContext())
        binding.listChallenge.adapter = ChallengeListAdapter(requireContext(),challengeListArray)

        binding.listNew.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.listNew.adapter = ChallengeItemAdapter(requireContext(),challengeItemArray)

        binding.btnGoAddChallenge.setOnClickListener{
            val intent = Intent(getActivity(), AddChallenge::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

//    TODO: 찜 버튼 클릭 시 버튼 이미지 변경

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}