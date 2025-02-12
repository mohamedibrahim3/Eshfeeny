package com.example.eshfeenygraduationproject.eshfeeny.alarm.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eshfeenygraduationproject.databinding.FragmentTimePickerBinding
import com.example.eshfeenygraduationproject.eshfeeny.alarm.fragment.EditAlarmFragment
import com.example.eshfeenygraduationproject.eshfeeny.alarm.fragment.SetAlarmFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TimePickerFragment(private val alarmState: String) : BottomSheetDialogFragment() {

    // creating the binding variable and giving it' initial value to null
    private var binding: FragmentTimePickerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // giving the binding variable it's value
        binding = FragmentTimePickerBinding.inflate(inflater)

        // making a click listener to the setTime button to take the values from the time picker
        binding?.setTime?.setOnClickListener {
            val selectedTime = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, binding?.timePicker?.hour ?: 0)
                set(Calendar.MINUTE, binding?.timePicker?.minute ?: 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            parentFragment?.let {
                val formattedTime = SimpleDateFormat("h:mm a", Locale.getDefault()).format(selectedTime.time)
                when (alarmState) {
                    "set" -> (it as? SetAlarmFragment)?.onTimeSelected(formattedTime, selectedTime.timeInMillis)
                    "edit" -> (it as? EditAlarmFragment)?.onTimeSelected(formattedTime, selectedTime.timeInMillis)
                    else -> {}
                }
            }
            dismiss()
        }

        return binding?.root
    }
}