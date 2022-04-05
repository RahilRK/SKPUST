package com.rahilkarim.skpust.ui.FeedDetailFrag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rahilkarim.skpust.util.Repository

class FeedDetailFragVMFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FeedDetailFragVM(repository) as T
    }
}