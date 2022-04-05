package com.rahilkarim.skpust.ui.FeedDetailFrag

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
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rahilkarim.skpust.ui.FeedFrag.FeedModel
import com.rahilkarim.skpust.R
import com.rahilkarim.skpust.util.GlobalClass
import com.rahilkarim.skpust.util.Repository
import com.rahilkarim.skpust.util.skpustApplication
import com.rahilkarim.skpust.databinding.FragmentFeedDetailBinding
import feedDetailSliderAdapter

class FeedDetailFrag : Fragment() {

    var TAG = "BusinessDetailFrag"
    private lateinit var activity: Context

    lateinit var globalClass: GlobalClass
    lateinit var binding: FragmentFeedDetailBinding
    lateinit var repository: Repository
    lateinit var viewModel: FeedDetailFragVM

    val args: FeedDetailFragArgs by navArgs()

    lateinit var toolbar: Toolbar;

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
        binding = FragmentFeedDetailBinding.inflate(layoutInflater, container, false)

        init()
        setToolbar()
        getData()

        return binding.root
    }

    fun init() {
//        globalClass = GlobalClass.getInstance(activity)
        globalClass = (requireActivity().application as skpustApplication).globalClass
        repository = (requireActivity().application as skpustApplication).repository
        viewModel = ViewModelProvider(this, FeedDetailFragVMFactory(repository))
            .get(FeedDetailFragVM::class.java)
    }

    fun setToolbar() {

        toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setNavigationIconColor(resources.getColor(R.color.white))
//        toolbar.title = resources.getString(R.string.detail)
//        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        toolbar.setNavigationOnClickListener {
            getActivity()?.onBackPressed()
        }
    }

    fun Toolbar.setNavigationIconColor(@ColorInt color: Int) = navigationIcon?.mutate()?.let {
        it.setTint(color)
        this.navigationIcon = it
    }

    fun getData() {

        viewModel.getFeedDetail(args.feedId).observe(requireActivity(), {

//            globalClass.log(TAG,it.toString())

            val functionImage1 = it.functionImage1
            val functionImage2 = it.functionImage2
            val functionImage3 = it.functionImage3
            val functionImage4 = it.functionImage4

            val sliderImages = arrayListOf<String>()

            sliderImages.add(functionImage1)
            sliderImages.add(functionImage2)
            sliderImages.add(functionImage3)
            sliderImages.add(functionImage4)

            setText(it)
            setSliderAdapter(sliderImages)
            addCompositePageTransformer()
        })
    }

    fun setText(model: FeedModel) {

        Glide.with(activity)
            .load(model.functionImage1)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.functionLogo)

        binding.functionName.text = model.functionName
        binding.functionCity.text = "${model.functionCity}, ${model.functionState}"
        binding.functionLikeCount.text = model.functionLikeCount.toString()
        binding.functionCommentCount.text = model.functionCommentCount.toString()
        addReadMore(model.functionDetail, binding.functionDetail)
    }

    private fun addReadMore(text: String, textView: TextView) {

        val ss = SpannableString(text.substring(0, 270) + "... read more")
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

    fun setSliderAdapter(list: ArrayList<String>) {
        binding.feedImageSliderViewPager.adapter = feedDetailSliderAdapter(
            activity,
            list)
        binding.dotsIndicator.setViewPager2(binding.feedImageSliderViewPager)
    }

    fun addCompositePageTransformer() {
        binding.feedImageSliderViewPager.clipToPadding = false
        binding.feedImageSliderViewPager.clipChildren = false
        binding.feedImageSliderViewPager.offscreenPageLimit = 3
        binding.feedImageSliderViewPager.getChildAt(0).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page: View, position: Float ->
            val r = 1 - Math.abs(position)
            page.scaleY = 0.95f + r * 0.05f
        }
        binding.feedImageSliderViewPager.setPageTransformer(compositePageTransformer)
    }
}