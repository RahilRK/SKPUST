package com.rahilkarim.skpust.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.rahilkarim.skpust.R
import com.rahilkarim.skpust.util.Constant
import com.rahilkarim.skpust.util.GlobalClass
import com.rahilkarim.skpust.util.skpustApplication
import com.rahilkarim.skpust.databinding.FragmentContactUsBinding

class ContactUsFragment : Fragment() {

    var TAG = "HomeFrag"
    private lateinit var activity: Context

    lateinit var globalClass: GlobalClass
    lateinit var binding: FragmentContactUsBinding

    lateinit var toolbar: androidx.appcompat.widget.Toolbar;

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
        // Inflate the layout for this fragment
        binding = FragmentContactUsBinding.inflate(layoutInflater, container, false)

        init()
        setToolbar()
        onClick()

        return binding.root
    }

    fun init() {
//        globalClass = GlobalClass.getInstance(activity)
        globalClass = (requireActivity().application as skpustApplication).globalClass
    }

    fun setToolbar() {
        toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = resources.getString(R.string.contactus)
        toolbar.setNavigationOnClickListener {
            getActivity()?.onBackPressed()
        }
    }

    fun onClick() {

        binding.callUsCardView.setOnClickListener {

            globalClass.openDialer(activity,Constant.contactUsMobileNumber)
        }

        binding.emailUsCardView.setOnClickListener {

            val mailto = "mailto:" + Constant.contactUsEmailId +
                    "?subject=" + Uri.encode(
                """
                      Regarding ${resources.getString(R.string.app_name)} app: 
                      mobileNumber: ${globalClass.getString("mobileNumber")}
                      """.trimIndent()
            ) +
                    "&body=" + Uri.encode("")

            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse(mailto)

            try {
                startActivity(emailIntent)
            } catch (e: ActivityNotFoundException) {
                globalClass.toastlong("No email app found!")
            }
        }

        binding.whatAppUsCardView.setOnClickListener {

            globalClass.openWhatsApp(activity,Constant.contactUsMobileNumber)
        }
    }
}