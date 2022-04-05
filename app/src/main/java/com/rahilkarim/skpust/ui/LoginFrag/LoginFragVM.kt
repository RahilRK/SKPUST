package com.rahilkarim.skpust.ui.LoginFrag

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rahilkarim.skpust.models.login.LoginRes
import com.rahilkarim.skpust.util.Constant.response_error
import com.rahilkarim.skpust.util.GlobalClass
import com.rahilkarim.skpust.util.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragVM(private val repository: Repository,
                  private val globalClass: GlobalClass): ViewModel() {

    var tag = "LoginFragVM"

//    fun setLoginData(mobileNumber:String) {
//        repository.setLoginData(mobileNumber)
//    }

    val loginRes: MutableLiveData<LoginRes> = MutableLiveData()
    val errorRes = MutableLiveData<String>()

    fun getDetail(mobileNo: String) {

        try {
            val response = repository.getDetail(mobileNo)
            response.enqueue(object : Callback<LoginRes> {
                override fun onResponse(call: Call<LoginRes>, response: Response<LoginRes>) {

                    response.body()?.let {
                        loginRes.postValue(response.body())
                    }?:run {
                        errorRes.postValue(response_error)
                    }
                }

                override fun onFailure(call: Call<LoginRes>, t: Throwable) {

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