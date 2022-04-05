package com.rahilkarim.skpust.util

import androidx.lifecycle.LiveData
import com.rahilkarim.skpust.ui.BusinessDetailFrag.BusinessDetailModel
import com.rahilkarim.skpust.ui.FeedFrag.FeedModel
import com.rahilkarim.skpust.ui.HomeFrag.FeatureListModel
import com.rahilkarim.skpust.ui.MatrimonyFrag.MatrimonyUserDetailModel
import com.rahilkarim.skpust.ui.PeopleFrag.PeopleListModel
import com.rahilkarim.skpust.R
import com.rahilkarim.skpust.models.login.LoginRes
import com.rahilkarim.skpust.models.peopleList.PeopleListData
import com.rahilkarim.skpust.network.RetrofitClient
import retrofit2.Response

class Repository(private val globalClass: GlobalClass,
                 private val myDatabase: MyDatabase) {

    var tag = "Repository"

    //todo other
    fun setLoginData(mobileNumber:String) {
        globalClass.setStringData("mobileNumber",mobileNumber)
    }

    fun clearDB() {
        myDatabase.clearAllTables()
    }

    //todo business
    suspend fun addBusinessDetail(arrayList: ArrayList<BusinessDetailModel>) {

        for(i in 0 until arrayList.size) {
            val model = arrayList.get(i)
            myDatabase.setUpPageFragDAO().addBusinessDetail(model)
        }
    }

    val businessList: LiveData<List<BusinessDetailModel>>
        get() = myDatabase.homeFragDAO().getBusinessList()

    fun searchBusinessList(businessName : String):LiveData<List<BusinessDetailModel>> {
        return myDatabase.searchBusinessFragDAO().searchBusiness(businessName)
    }

    fun getBusinessDetail(id : Int):LiveData<BusinessDetailModel> {
        return myDatabase.businessDetailFragDAO().getBusinessDetail(id)
    }

    //todo feature
    fun featureList() : ArrayList<FeatureListModel> {

        val array = arrayListOf<FeatureListModel>()
        array.add(FeatureListModel("Feed", R.drawable.ic_feed))
        array.add(FeatureListModel("Search\nBusiness", R.drawable.ic_search_business))
        array.add(FeatureListModel("People", R.drawable.ic_people))
        array.add(FeatureListModel("Search\nPeople", R.drawable.ic_search_people))
        array.add(FeatureListModel("Matrimony", R.drawable.ic_matrimony))
        array.add(FeatureListModel("Emergency\nSOS", R.drawable.ic_emergency))
        array.add(FeatureListModel("Live Aarti", R.drawable.ic_live_streaming))
        array.add(FeatureListModel("Profile", R.drawable.ic_user))
        array.add(FeatureListModel("Send\nFeedback", R.drawable.ic_send_feedback))
        array.add(FeatureListModel("SKPUST\nHistory", R.drawable.ic_history))
        array.add(FeatureListModel("Contact Us", R.drawable.ic_contactus))
        array.add(FeatureListModel("Share", R.drawable.ic_share))
        array.add(FeatureListModel("Logout", R.drawable.ic_logout))

        return array
    }

    //todo people
    suspend fun addPeopleDetail(arrayList: ArrayList<PeopleListModel>) {

        for(i in 0 until arrayList.size) {
            val model = arrayList.get(i)
            myDatabase.setUpPageFragDAO().addPeopleDetail(model)
        }
    }

    val peopleList: LiveData<List<PeopleListModel>>
        get() = myDatabase.peopleFragDAO().getPeopleList()

    fun searchPeopleList(userName : String):LiveData<List<PeopleListModel>> {
        return myDatabase.peopleFragDAO().searchPeople(userName)
    }

    fun getPeopleDetail(id : Int):LiveData<PeopleListModel> {
        return myDatabase.peopleDetailFragDAO().getPeopleDetail(id)
    }

    //todo feed
    suspend fun addFeedDetail(arrayList: ArrayList<FeedModel>) {

        for(i in 0 until arrayList.size) {
            val model = arrayList.get(i)
            myDatabase.setUpPageFragDAO().addFeedDetail(model)
        }
    }

    val feedList: LiveData<List<FeedModel>>
        get() = myDatabase.feedFragDAO().getFeedList()

    fun getFeedDetail(id : Int):LiveData<FeedModel> {
        return myDatabase.feedDetailFragDAO().getFeedDetail(id)
    }

    fun searchFeedList(feedName : String):LiveData<List<FeedModel>> {
        return myDatabase.feedFragDAO().searchFeedList(feedName)
    }

    //todo matrimony
    suspend fun addMatrimonyUserDetail(arrayList: ArrayList<MatrimonyUserDetailModel>) {

        for(i in 0 until arrayList.size) {
            val model = arrayList.get(i)
            myDatabase.setUpPageFragDAO().addMatrimonyUserDetail(model)
        }
    }

    val matrimonyUserList: LiveData<List<MatrimonyUserDetailModel>>
        get() = myDatabase.matrimonyFragDAO().getMatrimonyUserList()

    fun searchMatrimonyUserList(userName : String):LiveData<List<MatrimonyUserDetailModel>> {
        return myDatabase.matrimonyFragDAO().searchMatrimonyUserList(userName)
    }

    fun getMatrimonyUserDetail(userId : Int):LiveData<MatrimonyUserDetailModel> {
        return myDatabase.matrimonyUserDetailFragDAO().getMatrimonyUserDetail(userId)
    }

    //todo api call
    fun getDetail(mobileNo: String) =
        RetrofitClient.apiCall.getDetail(mobileNo)


    fun getPersonList(limit:Int, offset: Int) =
        RetrofitClient.apiCall.getPersonList(
            globalClass.getUserId(),
            limit,
            offset)

    fun getPeopleSearchList(limit:Int, offset: Int, searchKeyword: String) =
        RetrofitClient.apiCall.getPeopleSearchList(
            globalClass.getUserId(),
            limit,
            offset,
            searchKeyword)
}