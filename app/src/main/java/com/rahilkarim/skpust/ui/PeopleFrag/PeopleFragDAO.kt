package com.rahilkarim.skpust.ui.PeopleFrag

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PeopleFragDAO {

    @Query("select * from peopleDetail")
    fun getPeopleList() :LiveData<List<PeopleListModel>>

    @Query("select * from peopleDetail where userName LIKE '%' ||:userName|| '%'")
    fun searchPeople(userName: String) :LiveData<List<PeopleListModel>>
}