package com.example.eshfeenygraduationproject.eshfeeny.viewmodel

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.MedicineRepoImpl
import com.example.domain.entity.CategoryResponse
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val repoImpl: MedicineRepoImpl
): ViewModel() {
    private val _medicine = MutableLiveData<CategoryResponse>()
    val medicine: LiveData<CategoryResponse>
        get() = _medicine
    @SuppressLint("LongLogTag")
    fun setMedicine(medicineId: Int){
        viewModelScope.launch {
            try {
                val response = repoImpl.getMedicineDetailsFromRemote(medicineId)
                _medicine.value = response
                Log.i("mvvm sh8aal All Medicines in details fragment ", toString())
            }catch (e:Exception){
                Log.e(TAG, "Error in MVVM details in all medicines", e)
            }
        }

    }
}