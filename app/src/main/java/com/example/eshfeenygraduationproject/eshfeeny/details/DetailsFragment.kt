package com.example.eshfeenygraduationproject.eshfeeny.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.data.repository.MedicineRepoImpl
import com.example.eshfeenygraduationproject.databinding.FragmentDetailsBinding
import com.example.eshfeenygraduationproject.eshfeeny.medicine.SideEffectsAdapter
import com.example.eshfeenygraduationproject.eshfeeny.medicine.UsageAdapter
import com.example.eshfeenygraduationproject.eshfeeny.medicine.UseCaseAdapter
import com.example.eshfeenygraduationproject.eshfeeny.medicine.WarningAdapter
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.DetailsViewModel
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.DetailsViewModelFactory



class DetailsFragment : Fragment() {
    private var binding:FragmentDetailsBinding? = null
    private lateinit var viewModel: DetailsViewModel
    private val args by navArgs<DetailsFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater,container,false)
        val repo = MedicineRepoImpl()
        val viewModelFactory = DetailsViewModelFactory(repo)
        viewModel = ViewModelProvider(this,viewModelFactory)[DetailsViewModel::class.java]
        binding?.lifecycleOwner = viewLifecycleOwner

        viewModel.setMedicine(args.Id)
        viewModel.medicine.observe(viewLifecycleOwner){
            binding?.idTxtAmountVolumeDetails?.text = it.body()?.nameAr
            binding?.idTxtPriceDetails?.text = it.body()?.price.toString()
            binding?.idTxtDescrection?.text = it.body()?.description

//            binding?.idImgMedicineDetails?.context?.let { it1 -> Glide.with(it1).load(it.body()?.images?.get(0)).into() }
            binding?.root?.context?.let { it1 -> binding?.idImgMedicineDetails?.let { it2 ->
                Glide.with(it1).load(it.body()?.images?.get(0)).into(
                    it2
                )
            } }
            val useCaseAdapter = UseCaseAdapter(it.body()!!.useCases)
            binding?.idRvUseCaseDetails?.adapter = useCaseAdapter
            val sideEffectAdapter = SideEffectsAdapter(it.body()!!.sideEffects)
            binding?.idRvSideEffectDetails?.adapter = sideEffectAdapter

            val usageAdapter = UsageAdapter(it.body()!!.usage)
            binding?.idRvUsageDetails?.adapter = usageAdapter

            val warningAdapter = WarningAdapter(it.body()!!.warning)
            binding?.idRvWarningDetails?.adapter = warningAdapter


        }
        // Inflate the layout for this fragment
        return binding?.root
    }

}