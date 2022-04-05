package com.rahilkarim.skpust.ui.LoginFrag

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rahilkarim.skpust.BuildConfig
import com.rahilkarim.skpust.R
import com.rahilkarim.skpust.util.GlobalClass
import com.rahilkarim.skpust.util.Repository
import com.rahilkarim.skpust.util.skpustApplication
import com.rahilkarim.skpust.databinding.FragmentLoginBinding
import com.rahilkarim.skpust.models.login.LoginData
import com.rahilkarim.skpust.util.Constant
import com.rahilkarim.skpust.util.Constant.testMobileNumber
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFrag : Fragment(R.layout.fragment_login) {

    var TAG = "LoginFrag"
    private lateinit var activity: Context

    lateinit var globalClass: GlobalClass
    lateinit var binding: FragmentLoginBinding
    lateinit var repository: Repository
    lateinit var viewModel: LoginFragVM

    lateinit var toolbar: androidx.appcompat.widget.Toolbar;

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = requireContext()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        init()
        setToolbar()
        onClick()
        observeData()

        return binding.root
    }

    fun init() {
//        globalClass = GlobalClass.getInstance(activity)
        globalClass = (requireActivity().application as skpustApplication).globalClass
        repository = (requireActivity().application as skpustApplication).repository
        viewModel = ViewModelProvider(this, LoginFragVMFactory(repository, globalClass))
            .get(LoginFragVM::class.java)
    }

    fun setToolbar() {
        toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
//        toolbar.setNavigationIcon(R.drawable.ic_menu_close_clear_cancel)
        toolbar.title = ""
        toolbar.setNavigationOnClickListener {

            requireActivity().onBackPressed()
        }
    }

    fun onClick() {

        if (BuildConfig.DEBUG) {
            binding.mobileNumber.setText(testMobileNumber)
        }

        binding.loginFab.setOnClickListener {

            val mobileNumber = binding.mobileNumber.text.toString()
            if (mobileNumber.length != 10) {
                binding.mobileNumbertf.error = "Invalid mobile number"
            } else {
                /*viewModel.setLoginData(mobileNumber)
                globalClass.toastshort(getString(R.string.login_successfully))
                findNavController().navigate(R.id.action_loginFrag_to_setUpPageFrag)*/

                if (globalClass.isInternetPresent()) {

                    showProgressBar()
                    viewModel.getDetail(mobileNumber)

                } else {

                    globalClass.toastlong(getString(R.string.noInternetConnection))
                }
            }
        }
    }

    /*fun getData() {
        viewModel.loginRes.observe(viewLifecycleOwner) { response ->

            when (response) {
                is Resource.Success -> {

                    hideProgressBar()
                    response.data?.let { result ->

                        val status = result.status
                        val message = result.message

                        if (status) {

                            val dataModel = result.data
                            nextPage(dataModel)

                        } else {
                            globalClass.showSnackBar(parentView, message)
                        }
                    }
                }
                is Resource.Error -> {

                    hideProgressBar()
                    response.message?.let { message ->

                        globalClass.log(TAG, "response Error: $message")
                        globalClass.toastshort(message)
                    }
                }
                is Resource.Loading -> {

                    showProgressBar()
                }
            }
        }
    }*/

    fun observeData() {

        viewModel.loginRes.observe(viewLifecycleOwner) { response ->

            hideProgressBar()
            val status = response.status
            val message = response.message
            if(status) {

                val dataModel = response.data
                nextPage(dataModel)
                globalClass.toastlong("Login Successfully")
            }
            else {
                globalClass.log(TAG,message)
                globalClass.toastlong(message)
            }
        }

        viewModel.errorRes.observe(viewLifecycleOwner) { errorMessage ->

            hideProgressBar()
            globalClass.log(TAG,errorMessage)
            globalClass.toastlong(Constant.network_error)
        }
    }

    private fun nextPage(dataModel: LoginData) {

        globalClass.setStringData("userID", dataModel.userID)
        globalClass.setStringData("userName", dataModel.userName)
        globalClass.setStringData("mobileNo", dataModel.mobileNo)

        findNavController().navigate(R.id.action_loginFrag_to_setUpPageFrag)
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }
}