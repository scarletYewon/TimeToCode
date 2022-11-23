package com.kmu.timetocode.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.chip.Chip
import com.kmu.timetocode.databinding.ChoiceTagBinding
import com.kmu.timetocode.databinding.FragmentAddChallenge4Binding
import com.kmu.timetocode.databinding.FragmentChallengeDetailBinding

class FragmentChallengeDetail : Fragment() {

    private var _binding: FragmentChallengeDetailBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChallengeDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupChip()
    }

    private fun setupChip() {
        val nameList =
            arrayListOf("Extra Soft", "Soft")
        for (name in nameList) {
            val chip = createChip(name)
            binding.detailChallengeTagGroup.addView(chip)
        }
    }

    private fun createChip(label: String): Chip {
        val chip = ChoiceTagBinding.inflate(layoutInflater).root
        chip.text = label
        return chip
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}