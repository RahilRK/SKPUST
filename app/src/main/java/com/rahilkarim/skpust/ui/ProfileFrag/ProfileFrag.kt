package com.rahilkarim.skpust.ui.ProfileFrag

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.rahilkarim.skpust.R
import com.rahilkarim.skpust.util.GlobalClass
import com.rahilkarim.skpust.util.skpustApplication
import com.rahilkarim.skpust.databinding.FragmentProfileBinding

class ProfileFrag : Fragment() {

    var TAG = "ProfileFrag"
    private lateinit var activity: Context

    lateinit var globalClass: GlobalClass
    lateinit var binding: FragmentProfileBinding

    lateinit var toolbar: androidx.appcompat.widget.Toolbar;

    var userImgUrl = "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fram.jpg?alt=media&token=1575add3-82d3-4f20-8a3b-c118e15343a4"

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
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        init()
        setToolbar()
        setFamilyImage()
        addReadMore(resources.getString(R.string.softwarecompany_desc), binding.businessDescription)

        return binding.root
    }

    fun init() {
//        globalClass = GlobalClass.getInstance(activity)
        globalClass = (requireActivity().application as skpustApplication).globalClass
    }

    fun setToolbar() {
        toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = resources.getString(R.string.profile)
        toolbar.setNavigationOnClickListener {
            getActivity()?.onBackPressed()
        }
    }

    fun setFamilyImage() {

        val requestOptions: RequestOptions = RequestOptions
            .bitmapTransform(RoundedCorners(16))
        Glide.with(activity)
            .load(userImgUrl)
            .apply(requestOptions)
            .into(binding.userImg);

        Glide.with(activity)
            .load(resources.getDrawable(R.drawable.mom))
            .apply(RequestOptions.circleCropTransform())
            .into(binding.motherImage);

        Glide.with(activity)
            .load(resources.getDrawable(R.drawable.dad))
            .apply(RequestOptions.circleCropTransform())
            .into(binding.fatherImage);

        Glide.with(activity)
            .load(resources.getDrawable(R.drawable.sister))
            .apply(RequestOptions.circleCropTransform())
            .into(binding.sisterImage);
    }

    private fun addReadMore(text: String, textView: TextView) {
        val ss = SpannableString(text.substring(0, 100) + "... read more")
        val clickableSpan: ClickableSpan = object : ClickableSpan() {

            override fun onClick(view: View) {
                addReadLess(text, textView)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ds.color = resources.getColor(R.color.primaryColor)
                } else {
                    ds.color = resources.getColor(R.color.primaryColor)
                }
            }
        }
        ss.setSpan(clickableSpan, ss.length - 10, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = ss
        textView.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun addReadLess(text: String, textView: TextView) {
        val ss = SpannableString("$text read less")
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                addReadMore(text, textView)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ds.color = resources.getColor(R.color.primaryColor)
                } else {
                    ds.color = resources.getColor(R.color.primaryColor)
                }
            }
        }
        ss.setSpan(clickableSpan, ss.length - 10, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = ss
        textView.movementMethod = LinkMovementMethod.getInstance()
    }
}