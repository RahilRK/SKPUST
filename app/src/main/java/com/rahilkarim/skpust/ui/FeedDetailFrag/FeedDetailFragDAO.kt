package com.rahilkarim.skpust.ui.FeedDetailFrag

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rahilkarim.skpust.ui.FeedFrag.FeedModel

@Dao
interface FeedDetailFragDAO {

    @Query("select * from feedDetail where id = :id")
    fun getFeedDetail(id: Int) :LiveData<FeedModel>
}