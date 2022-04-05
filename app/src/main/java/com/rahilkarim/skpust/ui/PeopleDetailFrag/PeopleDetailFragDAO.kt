package com.rahilkarim.skpust.ui.PeopleDetailFrag

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rahilkarim.skpust.ui.PeopleFrag.PeopleListModel

@Dao
interface PeopleDetailFragDAO {

    @Query("select * from peopleDetail where id = :id")
    fun getPeopleDetail(id: Int) :LiveData<PeopleListModel>
}