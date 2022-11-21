package com.kmu.timetocode.add

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kmu.timetocode.R
import com.kmu.timetocode.databinding.FragmentAddChallenge3Binding

class FragmentAddChallenge3 : Fragment() {


    private var _binding: FragmentAddChallenge3Binding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddChallenge3Binding.inflate(inflater, container, false)
        binding.selectCount.visibility = View.GONE
        binding.interval.visibility = View.GONE

        binding.rGroupCount.setOnCheckedChangeListener{radioGroup, i->
            when(i){
                binding.rBtnCountOption1.id -> {
                    binding.selectCount.visibility = View.GONE
                    binding.interval.visibility = View.GONE
                }
                binding.rBtnCountOption2.id -> {
                    binding.selectCount.visibility = View.VISIBLE
                    binding.interval.visibility = View.VISIBLE
                }
            }
        }

        binding.interval.setOnClickListener{
            var listener = TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
                binding.editInterval.text = "${i}시간 ${i2}분"
            }
            var picker = TimePickerDialog(requireContext(),listener, 0, 0, true)
            picker.setMessage("인증 간격")
            picker.show()
        }

        binding.startTime.setOnClickListener{
            var listener = TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
                binding.editStartTime.text = "${i}시 ${i2}분"
            }
            var picker = TimePickerDialog(requireContext(),listener, 0, 0, true)
            picker.setMessage("시작시간")
            picker.show()
        }

        binding.endTime.setOnClickListener{
            var listener = TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
                binding.editEndTime.text = "${i}시 ${i2}분"
            }
            var picker = TimePickerDialog(requireContext(), listener, 23, 59, true)
            picker.setMessage("종료시간")
            picker.show()
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