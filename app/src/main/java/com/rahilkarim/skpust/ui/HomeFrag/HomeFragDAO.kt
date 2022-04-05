package com.rahilkarim.skpust.ui.HomeFrag

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rahilkarim.skpust.ui.BusinessDetailFrag.BusinessDetailModel

@Dao
interface HomeFragDAO {

    @Query("select * from businessDetail")
    fun getBusinessList() :LiveData<List<BusinessDetailModel>>
}