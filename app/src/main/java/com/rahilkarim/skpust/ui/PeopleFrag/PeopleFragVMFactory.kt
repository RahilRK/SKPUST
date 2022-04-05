package com.rahilkarim.skpust.ui.PeopleFrag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rahilkarim.skpust.util.GlobalClass
import com.rahilkarim.skpust.util.Repository

class PeopleFragVMFactory(private val repository: Repository, private val globalClass: GlobalClass) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PeopleFragVM(repository, globalClass) as T
    }
}