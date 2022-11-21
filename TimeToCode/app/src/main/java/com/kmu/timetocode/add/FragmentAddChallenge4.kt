package com.kmu.timetocode.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kmu.timetocode.databinding.FragmentAddChallenge3Binding
import com.kmu.timetocode.databinding.FragmentAddChallenge4Binding


class FragmentAddChallenge4 : Fragment() {

    private var _binding: FragmentAddChallenge4Binding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddChallenge4Binding.inflate(inflater, container, false)

    // TODO: 챌린지 생성 후 어디로 가야하는지 결정

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}