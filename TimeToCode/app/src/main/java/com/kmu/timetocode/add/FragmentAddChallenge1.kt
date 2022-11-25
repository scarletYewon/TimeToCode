package com.kmu.timetocode.add

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.android.material.chip.Chip
import androidx.navigation.fragment.findNavController
import com.kmu.timetocode.R
import com.kmu.timetocode.databinding.FragmentAddChallenge1Binding

class FragmentAddChallenge1 : Fragment() {

    private val model: AddChallengeViewModel by activityViewModels()

    private var _binding: FragmentAddChallenge1Binding? = null
    private val binding get() = _binding!!

    private var nameFlag = false
    private var tagFlag = false
    private var infoFlag = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 뷰 바인딩 설정
        _binding = FragmentAddChallenge1Binding.inflate(inflater, container, false)

        binding.editChallengeName.addTextChangedListener(nameListener)
        binding.editChallengeInfo.addTextChangedListener(infoListener)

        val chipList = ArrayList<String>()

        // 다음버튼 클릭 시 입력 데이터 ViewModel에 저장하고 이동
        binding.btnGoAdd2.setOnClickListener{

            val name = binding.editChallengeName.text.toString()+"%"+chipList[0]+"%"+chipList[1]
            val introduce = binding.editChallengeInfo.text.toString()
            model.addData1(name,chipList[0],chipList[1],introduce)
            findNavController().navigate(R.id.action_fragmentAddChallenge1_to_fragmentAddChallenge2)
        }

        // 태그 생성
        binding.btnChallengeAddTag.setOnClickListener {
            val string = binding.editChallengeTagAdd.text

            if (string.isNullOrEmpty()) {
                Toast.makeText(activity,"태그 내용을 입력해주세요",Toast.LENGTH_SHORT).show()
            } else {
                binding.tagChipGroup.addView(Chip(requireContext()).apply {
                    text = string
                    isCloseIconVisible = true
                    setOnCloseIconClickListener {
                        binding.tagChipGroup.removeView(this)
                        val delItem = this.text
                        chipList.remove(delItem.toString())
                        tagCheck(chipList)
                    }
                })
                chipList.add("#" + string.toString())
                tagCheck(chipList)
                binding.editChallengeTagAdd.text.clear()
            }

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 다음버튼 비활성화
        binding.btnGoAdd2.isEnabled = false
    }

    // 챌린지명 입력 확인
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

    // 태그 필수 2개 생성 확인
    private fun tagCheck(chipList: ArrayList<String>) {
        tagFlag = chipList.size == 2
        flagCheck()
    }

    // 챌린지 소개 입력 확인
    private val infoListener = object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            infoFlag = binding.editChallengeInfo.text.isNotEmpty()
            flagCheck()
        }
    }

    // 입력 조건에 따른 다음버튼 활성화 여부 확인
    private fun flagCheck() {
        binding.btnGoAdd2.isEnabled = nameFlag && tagFlag && infoFlag
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}