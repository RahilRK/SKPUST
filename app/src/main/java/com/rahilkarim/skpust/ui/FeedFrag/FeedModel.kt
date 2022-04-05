package com.rahilkarim.skpust.ui.FeedFrag

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "feedDetail")
data class FeedModel @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val functionName: String,
    val functionDetail: String,
    val functionDate: String,
    val functionCity: String,
    val functionState: String,
    val functionImage1: String,
    val functionImage2: String,
    val functionImage3: String,
    val functionImage4: String,
    val functionLikeCount: Int,
    val functionCommentCount: Int,
    @Ignore
    var feedPageImagesList: ArrayList<String>? = null
)
