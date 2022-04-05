package com.rahilkarim.skpust.ui.MatrimonyFrag

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rahilkarim.skpust.util.Repository

class MatrimonyFragVM(private val repository: Repository): ViewModel() {

    var tag = "MatrimonyFragVM"

    val matrimonyUserList: LiveData<List<MatrimonyUserDetailModel>>
        get() = repository.matrimonyUserList

    fun searchMatrimonyUserFeedList(userName: String):LiveData<List<MatrimonyUserDetailModel>> {
        return repository.searchMatrimonyUserList(userName)
    }
}