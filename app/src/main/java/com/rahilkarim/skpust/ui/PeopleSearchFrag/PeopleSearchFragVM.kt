package com.rahilkarim.skpust.ui.PeopleSearchFrag

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rahilkarim.skpust.models.peopleList.PeopleListData
import com.rahilkarim.skpust.util.Constant
import com.rahilkarim.skpust.util.GlobalClass
import com.rahilkarim.skpust.util.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PeopleSearchFragVM(private val repository: Repository,
                         private val globalClass: GlobalClass): ViewModel() {

    var tag = "PeopleSearchFragVM"

//    val peopleList: LiveData<List<PeopleListModel>>
//        get() = repository.peopleList
//
//    fun searchPeopleList(userName: String):LiveData<List<PeopleListModel>> {
//        return repository.searchPeopleList(userName)
//    }

    val peopleSearchDetailRes: MutableLiveData<PeopleListData> = MutableLiveData()
    private val errorRes = MutableLiveData<String>()

    init {
//        getPersonList(paginationLimit, 0)
    }

    fun getPeopleSearchList(limit:Int, offset: Int, searchKeyword: String) {

        try {
            val response = repository.getPeopleSearchList(limit,offset,searchKeyword)
            response.enqueue(object : Callback<PeopleListData>{
                override fun onResponse(
                    call: Call<PeopleListData>,
                    response: Response<PeopleListData>) {

                    response.body()?.let {
                        peopleSearchDetailRes.postValue(response.body())
                    }?:run {
                        errorRes.postValue(Constant.response_error)
                    }
                }

                override fun onFailure(call: Call<PeopleListData>, t: Throwable) {

                    val error = t.toString()
                    globalClass.log(tag,error)
                    errorRes.postValue(error)
                }
            })
        }
        catch (e: Exception) {

            val error = Log.getStackTraceString(e)
            globalClass.log(tag,error)
            errorRes.postValue(error)
        }
    }
}