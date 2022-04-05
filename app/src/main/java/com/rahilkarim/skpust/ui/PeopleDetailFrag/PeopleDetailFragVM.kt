package com.rahilkarim.skpust.ui.PeopleDetailFrag

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rahilkarim.skpust.ui.PeopleFrag.PeopleListModel
import com.rahilkarim.skpust.util.Repository

class PeopleDetailFragVM(private val repository: Repository): ViewModel() {

    var tag = "PeopleDetailFragVM"

    fun peopleDetail(id : Int):LiveData<PeopleListModel> {
        return repository.getPeopleDetail(id)
    }
}