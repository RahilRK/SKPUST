package com.rahilkarim.skpust.ui.SetUpPageFrag

import androidx.room.*
import com.rahilkarim.skpust.ui.BusinessDetailFrag.BusinessDetailModel
import com.rahilkarim.skpust.ui.FeedFrag.FeedModel
import com.rahilkarim.skpust.ui.MatrimonyFrag.MatrimonyUserDetailModel
import com.rahilkarim.skpust.ui.PeopleFrag.PeopleListModel

@Dao
interface SetUpPageFragDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBusinessDetail(model: BusinessDetailModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPeopleDetail(model: PeopleListModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFeedDetail(model: FeedModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMatrimonyUserDetail(model: MatrimonyUserDetailModel)
}