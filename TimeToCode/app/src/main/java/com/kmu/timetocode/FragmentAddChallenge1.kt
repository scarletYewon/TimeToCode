package com.kmu.timetocode

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.chip.Chip
import com.kmu.timetocode.databinding.ChoiceTagBinding
import androidx.navigation.fragment.findNavController
import com.kmu.timetocode.databinding.FragmentAddChallenge1Binding

class FragmentAddChallenge1 : Fragment() {

    private var _binding: FragmentAddChallenge1Binding? = null

    private val binding get() = _binding!!

    private var nameFlag = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 뷰 바인딩 설정
        _binding = FragmentAddChallenge1Binding.inflate(inflater, container, false)

        binding.editChallengeName.addTextChangedListener(nameListener)
        
        binding.btnGoAdd2.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentAddChallenge1_to_fragmentAddChallenge2)
        }


        return binding.root


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupChip()

        // 모두 입력했을 때만 버튼 활성화
        // 입력 변화값 확인 필요
        binding.btnGoAdd2.isEnabled = false
//        if (binding.editChallengeName.text.isNotEmpty()&&binding.editChallengeInfo.text.isNotEmpty()){
//            binding.btnGoAdd2.isEnabled = true
//        }

    }

    private fun setupChip() {
        val nameList =
            arrayListOf("Extra Soft", "Soft", "Medium", "Hard", "Extra Hard")
        for (name in nameList) {
            val chip = createChip(name)
            binding.challengeAllTags.addView(chip)
        }
    }

    private fun createChip(label: String): Chip {
        val chip = ChoiceTagBinding.inflate(layoutInflater).root
        chip.text = label
        return chip
    }

    private val nameListener = object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            nameFlag = binding.editChallengeName.text.isNotEmpty()
            flagCheck()
        }
    }

    fun flagCheck() {
        binding.btnGoAdd2.isEnabled = nameFlag
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}