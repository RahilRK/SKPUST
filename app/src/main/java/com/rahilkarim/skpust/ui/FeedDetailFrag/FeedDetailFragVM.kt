package com.rahilkarim.skpust.ui.FeedDetailFrag

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rahilkarim.skpust.ui.FeedFrag.FeedModel
import com.rahilkarim.skpust.util.Repository

class FeedDetailFragVM(private val repository: Repository): ViewModel() {

    var tag = "FeedDetailFragVM"

    fun getFeedDetail(id : Int):LiveData<FeedModel> {
        return repository.getFeedDetail(id)
    }

}