package com.example.eshfeenygraduationproject.eshfeeny.search_for_medicines

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eshfeenygraduationproject.databinding.FragmentDentalCareBinding


class Dental_CareFragment : Fragment() {
    private var binding:FragmentDentalCareBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDentalCareBinding.inflate(layoutInflater)

        // Inflate the layout for this fragment
        return binding?.root
    }
}