package com.rahilkarim.skpust.ui.PeopleFrag

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahilkarim.skpust.models.peopleList.PeopleListData
import com.rahilkarim.skpust.network.Resource
import com.rahilkarim.skpust.util.Constant
import com.rahilkarim.skpust.util.Constant.paginationLimit
import com.rahilkarim.skpust.util.GlobalClass
import com.rahilkarim.skpust.util.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PeopleFragVM(private val repository: Repository,
                   private val globalClass: GlobalClass): ViewModel() {

    var tag = "PeopleFragVM"

//    val peopleList: LiveData<List<PeopleListModel>>
//        get() = repository.peopleList
//
//    fun searchPeopleList(userName: String):LiveData<List<PeopleListModel>> {
//        return repository.searchPeopleList(userName)
//    }

    val peopleList: MutableLiveData<Resource<PeopleListData>> = MutableLiveData()
    var peopleListResponse: PeopleListData? = null

    val peopleDetailRes: MutableLiveData<PeopleListData> = MutableLiveData()
    private val errorRes = MutableLiveData<String>()

    init {
        getPersonList(paginationLimit, 0)
    }

    fun getPersonList(limit:Int, offset: Int) {

        try {
            val response = repository.getPersonList(limit,offset)
            response.enqueue(object : Callback<PeopleListData>{
                override fun onResponse(
                    call: Call<PeopleListData>,
                    response: Response<PeopleListData>) {

                    response.body()?.let {
                        peopleDetailRes.postValue(response.body())
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