package com.rahilkarim.skpust.ui.BusinessDetailFrag

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BusinessDetailFragDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBusinessDetail(model: BusinessDetailModel)

    @Query("select * from businessDetail where id = :id")
    fun getBusinessDetail(id: Int) :LiveData<BusinessDetailModel>
}