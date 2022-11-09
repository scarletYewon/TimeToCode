package com.kmu.timetocode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.kmu.timetocode.databinding.FragmentAddChallenge2Binding
import com.kmu.timetocode.databinding.FragmentAddChallenge3Binding

class FragmentAddChallenge3 : Fragment() {


    private var _binding: FragmentAddChallenge3Binding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddChallenge3Binding.inflate(inflater, container, false)

        binding.rGroupCount.setOnCheckedChangeListener{radioGroup, i->
            when(i){
                binding.rBtnCountOption1.id -> binding.selectCount.visibility = View.GONE
                binding.rBtnCountOption2.id -> binding.selectCount.visibility = View.VISIBLE
            }
        }

        binding.btnGoAdd4.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentAddChallenge3_to_fragmentAddChallenge4)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinnerCount()

    }

    private fun setupSpinnerCount() {
        val counts = resources.getStringArray(R.array.countSpinner)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, counts)
        binding.spinner.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}