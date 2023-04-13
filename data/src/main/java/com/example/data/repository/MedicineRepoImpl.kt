package com.example.data.repository

import com.example.data.remote.apis.UserRetrofitInstance
import com.example.domain.entity.CategoryResponse

class MedicineRepoImpl {
    //get medicine امساك
    suspend fun getMedicineFromRemoteForEmsaak(): CategoryResponse =
        UserRetrofitInstance.medicineApi.getMedicineFromEmsaak()

    //get medicine كحه
    suspend fun getMedicineFromRemoteForKo7aa(): CategoryResponse =
        UserRetrofitInstance.medicineApi.getMedicineFromKo7aa()

    //get medicine مغص
    suspend fun getMedicineFromRemoteForM8aas(): CategoryResponse =
        UserRetrofitInstance.medicineApi.getMedicineFromM8aas()

    //get medicine الحموضة و سوء الهضم
    suspend fun getMedicineFromRemoteFor7modaAndSo2Hadm(): CategoryResponse =
        UserRetrofitInstance.medicineApi.getMedicineFrom7modaAndSo2Hadm()

    //get medicine الفيتامينات و المكملات الغذائية
    suspend fun getMedicineFromRemoteForVetamenAndMa2kolat(): CategoryResponse =
        UserRetrofitInstance.medicineApi.getMedicineFromVetamenAndMa2kolat()

    //get medicine تقوية المناعة
    suspend fun getMedicineFromRemoteForT2wyaaElmna3a(): CategoryResponse =
        UserRetrofitInstance.medicineApi.getMedicineFromT2wyaaElmna3a()

    //get medicine مسكنات
    suspend fun getMedicineFromRemoteForMosknaat(): CategoryResponse =
        UserRetrofitInstance.medicineApi.getMedicineFromMosknaat()

    //get medicine مضادات حيوية
    suspend fun getMedicineFromRemoteForModat7aywee(): CategoryResponse =
        UserRetrofitInstance.medicineApi.getMedicineFromModat7aywee()

    //get All Medicine
    suspend fun getMedicineFromRemoteForAllMedicines(): CategoryResponse =
        UserRetrofitInstance.medicineApi.getMedicineFromAllMedicines()

    suspend fun getMedicineDetailsFromRemote(medicineId: Int): CategoryResponse{
        val response = UserRetrofitInstance.medicineApi.getMedicineDetails(medicineId)
        if(response.isSuccessful){
            val detailsResponse = response.body()
            return CategoryResponse(
                id = detailsResponse?._id ?: 0,
                nameAr = detailsResponse?.nameAr ?: "",
                nameEn = detailsResponse?.nameEn ?: "",
                amount = detailsResponse?.amount ?: "",
                images = detailsResponse?.images ?: "",
                price = detailsResponse?.price ?: 0,
                usage = detailsResponse?.usage ?: "",
                volume = detailsResponse?.volume ?: "",
                warning = detailsResponse?.warning ?: "",
                sideEffects = detailsResponse?.sideEffects ?: "",
                description = detailsResponse?.description ?: "",
                useCase = detailsResponse?.useCases ?: ""
                // map other properties as needed
            )
        }else {
            throw Exception("Failed to fetch medicine details")
        }
    }


}