package com.rahilkarim.skpust.ui.MatrimonyUserDetailFrag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rahilkarim.skpust.util.Repository

class MatrimonyUserDetailFragVMFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MatrimonyUserDetailFragVM(repository) as T
    }
}