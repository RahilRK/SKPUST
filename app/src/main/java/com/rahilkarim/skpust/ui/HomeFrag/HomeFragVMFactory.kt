package com.rahilkarim.skpust.ui.HomeFrag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rahilkarim.skpust.util.Repository

class HomeFragVMFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeFragVM(repository) as T
    }
}