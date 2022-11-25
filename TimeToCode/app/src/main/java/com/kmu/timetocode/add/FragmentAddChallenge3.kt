package com.kmu.timetocode.add

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kmu.timetocode.R
import com.kmu.timetocode.databinding.FragmentAddChallenge3Binding

class FragmentAddChallenge3 : Fragment() {

    private val model: AddChallengeViewModel by activityViewModels()

    private var _binding: FragmentAddChallenge3Binding? = null

    private val binding get() = _binding!!

    private var count = 1
    private var startTime = "0000"
    private var endTime = "2359"

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
                    count = 1
                }
                binding.rBtnCountOption2.id -> {
                    binding.selectCount.visibility = View.VISIBLE
                    binding.interval.visibility = View.VISIBLE
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("서비스 준비 중입니다.")
                        .setMessage("다음 업데이트를 기다려주세요. 감사합니다:)")
                        .setPositiveButton("확인",
                            DialogInterface.OnClickListener { dialog, id ->
                                binding.rBtnCountOption1.isChecked=true
                            })
                    // 다이얼로그를 띄워주기
                    builder.show()

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
                var h ="23"
                var m = "59"
                h = if (i < 10){"0${i}"} else{"$i"}
                m = if (i2 < 10){"0${i2}"}else{"$i2"}
                binding.editStartTime.text = "${h}시 ${m}분"
                startTime = "${h}${m}"
            }
            var picker = TimePickerDialog(requireContext(),listener, 0, 0, true)
            picker.setMessage("시작시간")
            picker.show()
        }

        binding.endTime.setOnClickListener{
            var listener = TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
                var h ="23"
                var m = "59"
                h = if (i < 10){"0${i}"} else{"$i"}
                m = if (i2 < 10){"0${i2}"}else{"$i2"}
                var check = h+m

                if(startTime.toInt() > check.toInt() ) {
                    Toast.makeText(activity, "종료시간은 시작시간 이후여야 합니다.", Toast.LENGTH_SHORT).show()
                }else{
                    binding.editEndTime.text = h+"시"+m+"분"
                    endTime = "${h}${m}"
                }

//                var test = "0203"
//                var test2 = test.toInt()
//                Toast.makeText(activity,"${test2}",Toast.LENGTH_SHORT).show()

            }
            var picker = TimePickerDialog(requireContext(), listener, 23, 59, true)
            picker.setMessage("종료시간")
            picker.show()
        }



        binding.btnGoAdd4.setOnClickListener{
            val start = startTime.toInt()
            val end = endTime.toInt()
            model.addData3(1,count,"0",start,end,7)

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