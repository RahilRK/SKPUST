package com.rahilkarim.skpust.ui.BusinessDetailFrag

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "businessDetail")
data class BusinessDetailModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val businessName:String,
    val businessCategory:String,
    val businessOwnerName:String,
    val businessContactNumber:String,
    val businessEmailId:String,
    val businessFoundedDate:String,
    val businessDescription:String,
    val businessBannerImage:String,
    val businessImage1:String,
    val businessImage2:String,
    val businessImage3:String,
    val businessAddress:String,
    val businessCity:String,
    val businessState:String,
    val businessPincode:String,
    val businessCoOrdinates:String,
):Parcelable
