package com.rahilkarim.skpust.ui.BusinessDetailFrag

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rahilkarim.skpust.util.Repository

class BusinessDetailFragVM(private val repository: Repository): ViewModel() {

    var tag = "BusinessDetailFragVM"

    fun getBusinessDetail(id : Int):LiveData<BusinessDetailModel> {
        return repository.getBusinessDetail(id)
    }
}