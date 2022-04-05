package com.rahilkarim.skpust.ui.SearchBusinessFrag

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rahilkarim.skpust.util.Repository
import com.rahilkarim.skpust.ui.BusinessDetailFrag.BusinessDetailModel

class SearchBusinessFragVM(private val repository: Repository): ViewModel() {

    var tag = "SearchBusinessFragVM"

    val businessList: LiveData<List<BusinessDetailModel>>
        get() = repository.businessList

    fun searchBusinessList(businessName: String):LiveData<List<BusinessDetailModel>> {
        return repository.searchBusinessList(businessName)
    }
}