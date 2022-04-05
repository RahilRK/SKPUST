package com.rahilkarim.skpust.ui.MatrimonyFrag

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matrimonyUserDetail")
data class MatrimonyUserDetailModel(
    @PrimaryKey(autoGenerate = true)
    val userId :Int,
    val userName :String,
    val userMobileNumber :String,
    val userDOB :String,
    val userGender :String,
    val userImage1 :String,
    val userImage2 :String,
    val userImage3 :String,
    val userQualification :String,
    val userOccupation :String,
    val userMotherTongue :String,
    val userReligion :String,
    val userCast :String,
    val userMaritalStatus :String,
    val userHeight :String,
    val userBloodGroup :String,
    val userBirthPlace :String,
    val userCity :String,
    val userState :String,
)
