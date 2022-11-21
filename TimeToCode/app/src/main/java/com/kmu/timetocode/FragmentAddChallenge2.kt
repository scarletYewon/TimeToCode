package com.kmu.timetocode

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentManager
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

        binding.btnSelectBG.setOnClickListener{
            Toast.makeText(activity,"",Toast.LENGTH_LONG).show()
            TakeImgDialog("이미지 가져오기").show(parentFragmentManager,"takeImgDialog")

        }

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