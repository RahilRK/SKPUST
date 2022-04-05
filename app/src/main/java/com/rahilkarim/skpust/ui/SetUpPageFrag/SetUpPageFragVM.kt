package com.rahilkarim.skpust.ui.SetUpPageFrag

import androidx.lifecycle.ViewModel
import com.rahilkarim.skpust.ui.BusinessDetailFrag.BusinessDetailModel
import com.rahilkarim.skpust.ui.FeedFrag.FeedModel
import com.rahilkarim.skpust.ui.MatrimonyFrag.MatrimonyUserDetailModel
import com.rahilkarim.skpust.ui.PeopleFrag.PeopleListModel
import com.rahilkarim.skpust.util.Repository

class SetUpPageFragVM(private val repository: Repository): ViewModel() {

    var tag = "SetUpPageFragVM"

    suspend fun addBusinessDetail(arrayList: ArrayList<BusinessDetailModel>) {

        repository.addBusinessDetail(arrayList)
    }

    suspend fun addPeopleDetail(arrayList: ArrayList<PeopleListModel>) {

        repository.addPeopleDetail(arrayList)
    }

    suspend fun addFeedDetail(arrayList: ArrayList<FeedModel>) {

        repository.addFeedDetail(arrayList)
    }

    suspend fun addMatrimonyUserDetail(arrayList: ArrayList<MatrimonyUserDetailModel>) {

        repository.addMatrimonyUserDetail(arrayList)
    }
}