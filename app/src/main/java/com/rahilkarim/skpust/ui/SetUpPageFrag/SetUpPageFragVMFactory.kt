package com.rahilkarim.skpust.ui.SetUpPageFrag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rahilkarim.skpust.util.Repository

class SetUpPageFragVMFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SetUpPageFragVM(repository) as T
    }
}