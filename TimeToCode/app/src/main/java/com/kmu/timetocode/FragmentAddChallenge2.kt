package com.kmu.timetocode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kmu.timetocode.databinding.FragmentAddChallenge1Binding
import com.kmu.timetocode.databinding.FragmentAddChallenge2Binding


class FragmentAddChallenge2 : Fragment() {

    private var _binding: FragmentAddChallenge2Binding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddChallenge2Binding.inflate(inflater, container, false)

        binding.btnGoAdd3.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentAddChallenge2_to_fragmentAddChallenge3)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}