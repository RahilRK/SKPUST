package com.rahilkarim.skpust.ui.SearchBusinessFrag

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rahilkarim.skpust.ui.BusinessDetailFrag.BusinessDetailModel

@Dao
interface SearchBusinessFragDAO {

    @Query("select * from businessDetail where businessName LIKE '%' ||:businessName|| '%'")
    fun searchBusiness(businessName: String) :LiveData<List<BusinessDetailModel>>
}