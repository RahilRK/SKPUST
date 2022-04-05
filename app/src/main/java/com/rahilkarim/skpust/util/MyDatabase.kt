package com.rahilkarim.skpust.util

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rahilkarim.skpust.ui.BusinessDetailFrag.BusinessDetailFragDAO
import com.rahilkarim.skpust.ui.BusinessDetailFrag.BusinessDetailModel
import com.rahilkarim.skpust.ui.FeedDetailFrag.FeedDetailFragDAO
import com.rahilkarim.skpust.ui.FeedFrag.FeedFragDAO
import com.rahilkarim.skpust.ui.FeedFrag.FeedModel
import com.rahilkarim.skpust.ui.HomeFrag.HomeFragDAO
import com.rahilkarim.skpust.ui.MatrimonyFrag.MatrimonyFragDAO
import com.rahilkarim.skpust.ui.MatrimonyFrag.MatrimonyUserDetailModel
import com.rahilkarim.skpust.ui.MatrimonyUserDetailFrag.MatrimonyUserDetailFragDAO
import com.rahilkarim.skpust.ui.PeopleDetailFrag.PeopleDetailFragDAO
import com.rahilkarim.skpust.ui.PeopleFrag.PeopleFragDAO
import com.rahilkarim.skpust.ui.PeopleFrag.PeopleListModel
import com.rahilkarim.skpust.R
import com.rahilkarim.skpust.ui.SearchBusinessFrag.SearchBusinessFragDAO
import com.rahilkarim.skpust.ui.SetUpPageFrag.SetUpPageFragDAO

@Database(
    entities =
    [BusinessDetailModel::class,
        PeopleListModel::class,
        FeedModel::class,
        MatrimonyUserDetailModel::class], version = 1
)
abstract class MyDatabase : RoomDatabase() {

    companion object {

        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {

            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDatabase(context)
                }
            }

            return INSTANCE!!
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            MyDatabase::class.java,
            context.resources.getString(R.string.app_name)
        )
            .build()
    }

    abstract fun setUpPageFragDAO(): SetUpPageFragDAO
    abstract fun homeFragDAO(): HomeFragDAO
    abstract fun businessDetailFragDAO(): BusinessDetailFragDAO
    abstract fun searchBusinessFragDAO(): SearchBusinessFragDAO
    abstract fun peopleFragDAO(): PeopleFragDAO
    abstract fun peopleDetailFragDAO(): PeopleDetailFragDAO
    abstract fun feedFragDAO(): FeedFragDAO
    abstract fun feedDetailFragDAO(): FeedDetailFragDAO
    abstract fun matrimonyFragDAO(): MatrimonyFragDAO
    abstract fun matrimonyUserDetailFragDAO(): MatrimonyUserDetailFragDAO
}