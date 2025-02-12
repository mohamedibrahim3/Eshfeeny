package com.example.eshfeenygraduationproject.eshfeeny.alarm.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eshfeenygraduationproject.databinding.FragmentAlarmDurationBinding
import com.example.eshfeenygraduationproject.eshfeeny.alarm.fragment.EditAlarmFragment
import com.example.eshfeenygraduationproject.eshfeeny.alarm.fragment.SetAlarmFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AlarmDurationFragment : BottomSheetDialogFragment() {

    private var binding: FragmentAlarmDurationBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAlarmDurationBinding.inflate(inflater)

        binding?.durationConfButton?.setOnClickListener {
            val duration = binding?.durationInputText?.text.toString().toIntOrNull()
            if (duration == null) {
                binding?.durationInputText?.error = "الرجاء ادخال مدة"
            } else {
                (parentFragment as? SetAlarmFragment)?.alarmDuration(duration)
                (parentFragment as? EditAlarmFragment)?.alarmDuration(duration)
                dismiss()
            }
        }

        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}