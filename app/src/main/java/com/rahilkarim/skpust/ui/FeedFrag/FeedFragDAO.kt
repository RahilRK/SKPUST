package com.rahilkarim.skpust.ui.FeedFrag

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FeedFragDAO {

    @Query("select * from feedDetail")
    fun getFeedList() :LiveData<List<FeedModel>>

    @Query("select * from feedDetail where functionName LIKE '%' ||:feedName|| '%'")
    fun searchFeedList(feedName: String) :LiveData<List<FeedModel>>
}