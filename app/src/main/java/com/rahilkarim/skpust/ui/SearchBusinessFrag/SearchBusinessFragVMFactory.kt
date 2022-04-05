package com.rahilkarim.skpust.ui.SearchBusinessFrag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rahilkarim.skpust.util.Repository

class SearchBusinessFragVMFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchBusinessFragVM(repository) as T
    }
}