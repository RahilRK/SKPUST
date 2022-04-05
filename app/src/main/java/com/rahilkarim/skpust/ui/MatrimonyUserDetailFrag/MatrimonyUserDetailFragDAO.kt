package com.rahilkarim.skpust.ui.MatrimonyUserDetailFrag

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rahilkarim.skpust.ui.MatrimonyFrag.MatrimonyUserDetailModel

@Dao
interface MatrimonyUserDetailFragDAO {

    @Query("select * from matrimonyUserDetail where userId = :userId")
    fun getMatrimonyUserDetail(userId: Int) :LiveData<MatrimonyUserDetailModel>
}