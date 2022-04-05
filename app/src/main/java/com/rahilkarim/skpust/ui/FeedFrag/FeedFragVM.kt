package com.rahilkarim.skpust.ui.FeedFrag

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rahilkarim.skpust.util.Repository

class FeedFragVM(private val repository: Repository): ViewModel() {

    var tag = "FeedFragVM"

    val feedList: LiveData<List<FeedModel>>
        get() = repository.feedList

    fun searchFeedList(feedName: String):LiveData<List<FeedModel>> {
        return repository.searchFeedList(feedName)
    }
}