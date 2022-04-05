package com.rahilkarim.skpust.ui.MatrimonyUserDetailFrag

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.rahilkarim.skpust.ui.MatrimonyFrag.MatrimonyUserDetailModel
import com.rahilkarim.skpust.R
import com.rahilkarim.skpust.util.GlobalClass
import com.rahilkarim.skpust.util.Repository
import com.rahilkarim.skpust.util.skpustApplication
import matrimonyUserDetailSliderAdapter

import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.LinearLayout
import android.widget.TextView
import com.rahilkarim.skpust.databinding.FragmentMatrimonyUserDetailBinding


class MatrimonyUserDetailFrag : Fragment() {

    var TAG = "MatrimonyUserDetailFrag"
    private lateinit var activity: Context

    lateinit var globalClass: GlobalClass
    lateinit var binding: FragmentMatrimonyUserDetailBinding
    lateinit var repository: Repository
    lateinit var viewModel: MatrimonyUserDetailFragVM

    val args: MatrimonyUserDetailFragArgs by navArgs()

    lateinit var model: MatrimonyUserDetailModel;

    lateinit var toolbar: androidx.appcompat.widget.Toolbar;

    lateinit var bottomSheet : RelativeLayout
    lateinit var bottomSheetBehavior: BottomSheetBehavior<RelativeLayout>
    var bottomSheetState = 4

    lateinit var collapseBottomSheetLayout:RelativeLayout
    lateinit var collapseUserName: TextView
    lateinit var collapseUserOccupation: TextView
    lateinit var collapseUserDOB: TextView
    lateinit var collapseUserCity: TextView

    lateinit var expandView:View
    lateinit var expandBottomSheetLayout:LinearLayout
    lateinit var expandToolbar: androidx.appcompat.widget.Toolbar;
    lateinit var expandUserName: TextView
    lateinit var expandUserOccupation: TextView
    lateinit var expandUserDOB: TextView
    lateinit var expandUserCity: TextView
    lateinit var userReligion: TextView
    lateinit var userCast: TextView
    lateinit var userMotherTongue: TextView
    lateinit var userMaritalStatus: TextView
    lateinit var userBirthPlace: TextView
    lateinit var userBloodGroup: TextView
    lateinit var userQualification: TextView

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
        binding = FragmentMatrimonyUserDetailBinding.inflate(layoutInflater, container, false)

        init()
        setToolbar()
//        onClick()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
        setBottomSheet(view)
    }

    fun init() {
//        globalClass = GlobalClass.getInstance(activity)
        globalClass = (requireActivity().application as skpustApplication).globalClass
        repository = (requireActivity().application as skpustApplication).repository
        viewModel = ViewModelProvider(this, MatrimonyUserDetailFragVMFactory(repository))
            .get(MatrimonyUserDetailFragVM::class.java)
    }

    fun setToolbar() {
        toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = ""
        toolbar.setNavigationOnClickListener {
            getActivity()?.onBackPressed()
        }
    }

    fun getData() {
        viewModel.getMatrimonyUserDetail(args.matrimonyUserIdArgs).observe(requireActivity(),{

            model = it
            globalClass.log(TAG,""+it)

            val userImage1 = it.userImage1
            val userImage2 = it.userImage2
            val userImage3 = it.userImage3

            val sliderImages = arrayListOf<String>()

            sliderImages.add(userImage1)
            sliderImages.add(userImage2)
            sliderImages.add(userImage3)

            setSliderAdapter(sliderImages)
            addCompositePageTransformer()

            setTextForCollapseBottomSheet()
            setTextForExpandBottomSheet()
        })
    }

    fun setSliderAdapter(arrayList: ArrayList<String>) {
        binding.matrimonyUserSliderViewPager.adapter = matrimonyUserDetailSliderAdapter(
            activity,
            arrayList)
        binding.dotsIndicator.setViewPager2(binding.matrimonyUserSliderViewPager)
    }

    fun addCompositePageTransformer() {
        binding.matrimonyUserSliderViewPager.clipToPadding = false
        binding.matrimonyUserSliderViewPager.clipChildren = false
        binding.matrimonyUserSliderViewPager.offscreenPageLimit = 3
        binding.matrimonyUserSliderViewPager.getChildAt(0).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page: View, position: Float ->
            val r = 1 - Math.abs(position)
            page.scaleY = 0.95f + r * 0.05f
        }
        binding.matrimonyUserSliderViewPager.setPageTransformer(compositePageTransformer)
    }

    fun setBottomSheet(view: View) {

        //todo collapse
        bindingCollapseView(view)

        //todo expand
        bindingExpandView(view)
        expandView.setOnClickListener {
            toggleBottomSheet()
        }

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.isHideable = false

        //todo for fullscreen
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.addBottomSheetCallback(object :BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

                globalClass.log(TAG,"bottomSheet state: $newState")
                bottomSheetState = newState

                if(newState == BottomSheetBehavior.STATE_EXPANDED ||
                    newState == BottomSheetBehavior.STATE_DRAGGING ||
                    newState == BottomSheetBehavior.STATE_SETTLING) {
                    showExpandView()
                }
                else if(newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    showCollapseView()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })

        //todo setPeek height
        collapseBottomSheetLayout.getViewTreeObserver()
            .addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    val availableHeight: Int = collapseBottomSheetLayout.getMeasuredHeight()
                    if (availableHeight > 0) {
                        collapseBottomSheetLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this)
                        //save height here and do whatever you want with it
                        globalClass.log(TAG,"peekheight: ${availableHeight}")
                        bottomSheetBehavior.peekHeight = availableHeight
                    }
                }
            })
    }

    fun bindingCollapseView(view: View) {

        bottomSheet = view.findViewById(R.id.bottomSheet)
        collapseBottomSheetLayout = view.findViewById(R.id.collapseBottomSheetLayout)
        collapseUserName = view.findViewById(R.id.collapseUserName)
        collapseUserOccupation = view.findViewById(R.id.collapseUserOccupation)
        collapseUserDOB = view.findViewById(R.id.collapseUserDOB)
        collapseUserCity = view.findViewById(R.id.collapseUserCity)
    }

    fun bindingExpandView(view: View) {
        expandView = view.findViewById(R.id.expandView)
        expandBottomSheetLayout = view.findViewById(R.id.expandBottomSheetLayout)
        expandToolbar = view.findViewById(R.id.expandToolbar)
        expandUserName = view.findViewById(R.id.expandUserName)
        expandUserOccupation = view.findViewById(R.id.expandUserOccupation)
        expandUserDOB = view.findViewById(R.id.expandUserDOB)
        expandUserCity = view.findViewById(R.id.expandUserCity)
        userReligion = view.findViewById(R.id.userReligion)
        userCast = view.findViewById(R.id.userCast)
        userMotherTongue = view.findViewById(R.id.userMotherTongue)
        userMaritalStatus = view.findViewById(R.id.userMaritalStatus)
        userBirthPlace = view.findViewById(R.id.userBirthPlace)
        userBloodGroup = view.findViewById(R.id.userBloodGroup)
        userQualification = view.findViewById(R.id.userQualification)
    }

    fun setTextForCollapseBottomSheet() {

        collapseUserName.text = model.userName
        collapseUserOccupation.text = "${model.userOccupation}, ${model.userCity}"
        collapseUserDOB.text = "${model.userDOB} yrs, ${model.userHeight}ft "
        collapseUserCity.text = "${model.userCity}, ${model.userState} "
    }

    fun setTextForExpandBottomSheet() {

        expandUserName.text = model.userName
        expandUserOccupation.text = "${model.userOccupation}, ${model.userCity}"
        expandUserDOB.text = "${model.userDOB} yrs, ${model.userHeight}ft "
        expandUserCity.text = "${model.userCity}, ${model.userState} "
        userReligion.text = model.userReligion
        userCast.text = model.userCast
        userMotherTongue.text = model.userMotherTongue
        userMaritalStatus.text = model.userMaritalStatus
        userBirthPlace.text = model.userBirthPlace
        userBloodGroup.text = model.userBloodGroup
        userQualification.text = model.userQualification
    }

    fun toggleBottomSheet() {

        if(bottomSheetState == BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        else if(bottomSheetState == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    fun setExpandToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(expandToolbar)
        expandToolbar.title = resources.getString(R.string.detail)
        expandToolbar.setNavigationOnClickListener {
            toggleBottomSheet()
        }
    }

    fun showCollapseView() {

        expandBottomSheetLayout.visibility = View.GONE
        collapseBottomSheetLayout.visibility = View.VISIBLE
        setToolbar()
    }

    fun showExpandView() {

        collapseBottomSheetLayout.visibility = View.GONE
        expandBottomSheetLayout.visibility = View.VISIBLE
        setExpandToolbar()
    }
}