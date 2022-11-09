package com.kmu.timetocode

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
            ChallengeListModel("a","챌린지 이름","생성자","+60명","github"),
            ChallengeListModel("a","챌린지 이름","생성자","+60명","github"),
            ChallengeListModel("a","챌린지 이름","생성자","+60명","github"),
            ChallengeListModel("a","챌린지 이름","생성자","+60명","github"),
            ChallengeListModel("a","챌린지 이름","생성자","+60명","github"),
            ChallengeListModel("a","챌린지 이름","생성자","+60명","github")

        )

        binding.listChallenge.layoutManager = LinearLayoutManager(requireContext())
        binding.listChallenge.adapter = ChallengeListAdapter(requireContext(),challengeListArray)

        binding.listNew.layoutManager = LinearLayoutManager(requireContext())
        binding.listNew.adapter = ChallengeItemAdapter(requireContext(),challengeItemArray)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}