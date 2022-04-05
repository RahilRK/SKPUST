package com.rahilkarim.skpust.ui.MatrimonyUserDetailFrag

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rahilkarim.skpust.ui.MatrimonyFrag.MatrimonyUserDetailModel
import com.rahilkarim.skpust.util.Repository

class MatrimonyUserDetailFragVM(private val repository: Repository): ViewModel() {

    var tag = "MatrimonyUserDetailFragVM"

    fun getMatrimonyUserDetail(id : Int):LiveData<MatrimonyUserDetailModel> {
        return repository.getMatrimonyUserDetail(id)
    }
}