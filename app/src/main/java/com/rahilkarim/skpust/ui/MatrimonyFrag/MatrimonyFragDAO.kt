package com.rahilkarim.skpust.ui.MatrimonyFrag

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MatrimonyFragDAO {

    @Query("select * from matrimonyUserDetail")
    fun getMatrimonyUserList() :LiveData<List<MatrimonyUserDetailModel>>

    @Query("select * from matrimonyUserDetail where userName LIKE '%' ||:userName|| '%'")
    fun searchMatrimonyUserList(userName: String) :LiveData<List<MatrimonyUserDetailModel>>
}