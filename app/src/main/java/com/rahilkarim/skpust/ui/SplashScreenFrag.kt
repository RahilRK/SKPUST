package com.rahilkarim.skpust.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rahilkarim.skpust.R
import com.rahilkarim.skpust.util.GlobalClass
import com.rahilkarim.skpust.util.skpustApplication
import com.rahilkarim.skpust.databinding.FragmentSplashScreenBinding
import com.rahilkarim.skpust.util.Constant.splashScreenTime

class SplashScreenFrag : Fragment() {

    var TAG = "SplashScreenFrag"
    private lateinit var activity: Context

    lateinit var globalClass: GlobalClass
    lateinit var binding: FragmentSplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = requireContext()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashScreenBinding.inflate(layoutInflater, container, false)

        init()
        nextPage()

        return binding.root
    }

    fun init() {
//        globalClass = GlobalClass.getInstance(activity)
        globalClass = (requireActivity().application as skpustApplication).globalClass
    }

    fun nextPage() {

        Handler(Looper.myLooper()!!).postDelayed(
            {
                if(globalClass.getString("mobileNo") == "") {
                    findNavController().navigate(R.id.action_splashScreenFrag_to_loginFrag)
                }
                else {
                    findNavController().navigate(R.id.action_splashScreenFrag_to_homeFrag)
                }
        },splashScreenTime)
    }
}