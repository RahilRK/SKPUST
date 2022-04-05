package com.rahilkarim.skpust.network

import com.rahilkarim.skpust.models.login.LoginRes
import com.rahilkarim.skpust.models.peopleList.PeopleListData
import com.rahilkarim.skpust.util.Constant.developerKey
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MyApiRequest {

    @Headers("Developerkey: $developerKey")
    @FormUrlEncoded
    @POST("login")
    fun getDetail(
        @Field("mobileNo") name: String,
    ) : Call<LoginRes>

    @Headers("Developerkey: $developerKey")
    @FormUrlEncoded
    @POST("getPersonList")
    fun getPersonList(
        @Field("userID") name: String,
        @Field("limit") limit: Int,
        @Field("offset") offset: Int,
    ) : Call<PeopleListData>

    @Headers("Developerkey: $developerKey")
    @FormUrlEncoded
    @POST("getPeopleSearchList")
    fun getPeopleSearchList(
        @Field("userID") name: String,
        @Field("limit") limit: Int,
        @Field("offset") offset: Int,
        @Field("searchKeyword") searchKeyword: String,
    ) : Call<PeopleListData>
}