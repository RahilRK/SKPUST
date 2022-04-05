package com.rahilkarim.skpust.ui.PeopleDetailFrag

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FamilyMemberListModel(
    val familyMemberName:String,
    val familyMemberImage:String,
    val familyMemberRelation:String,
): Parcelable
