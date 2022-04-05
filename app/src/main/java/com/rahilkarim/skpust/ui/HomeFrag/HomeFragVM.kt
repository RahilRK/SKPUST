package com.rahilkarim.skpust.ui.HomeFrag

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rahilkarim.skpust.util.Repository
import com.rahilkarim.skpust.ui.BusinessDetailFrag.BusinessDetailModel

class HomeFragVM(private val repository: Repository) : ViewModel() {

    var tag = "HomeFragVM"

    //    val businessList: LiveData<List<businessDetailModel>> = repository.businessList
    val businessList: LiveData<List<BusinessDetailModel>>
        get() = repository.businessList

    fun featureList() : ArrayList<FeatureListModel> {
        return repository.featureList()
    }
}