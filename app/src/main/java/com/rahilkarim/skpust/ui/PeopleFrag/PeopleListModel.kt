package com.rahilkarim.skpust.ui.PeopleFrag

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "peopleDetail")
data class PeopleListModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val userName:String,
    val userMobileNumber:String,
    val userDOB:String,
    val userGender:String,
    val userBloodGroup:String,
    val userImage:String,
    val userAddress:String,
    val userCity:String,
    val userState:String,
    val userPincode:String,
    val userCoOrdinates:String,
    val fatherName:String,
    val fatherImage:String,
    val motherName:String,
    val motherImage:String,
    val sisterName:String,
    val sisterImage:String,
    val brotherName:String,
    val brotherImage:String,
): Parcelable
