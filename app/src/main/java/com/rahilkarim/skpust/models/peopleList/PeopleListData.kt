package com.rahilkarim.skpust.models.peopleList

data class PeopleListData(
    val `data`: ArrayList<PeopleList>,
    val message: String,
    val status: Boolean,
    val totalRecord: Int
)