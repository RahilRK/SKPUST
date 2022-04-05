package com.rahilkarim.skpust.ui.HomeFrag

import android.Manifest
import android.app.DownloadManager
import android.app.ProgressDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.rahilkarim.skpust.BuildConfig
import com.rahilkarim.skpust.util.GlobalClass
import com.rahilkarim.skpust.util.Repository
import com.rahilkarim.skpust.util.skpustApplication
import com.rahilkarim.skpust.ui.BusinessDetailFrag.BusinessDetailModel
import com.rahilkarim.skpust.ui.BusinessDetailFrag.featureListAdapter
import com.rahilkarim.skpust.ui.MainActivity
import com.rahilkarim.skpust.R
import com.rahilkarim.skpust.util.Constant
import com.rahilkarim.skpust.databinding.FragmentHomeBinding
import homeSliderAdapter
import java.io.File
import java.lang.Exception
import android.widget.TextView
import android.widget.FrameLayout
import com.rahilkarim.skpust.util.Constant.homeSliderDelayTime

class HomeFrag : Fragment(),
    homeSliderAdapter.homeSliderAdapterOnClick,
    featureListAdapter.featureListAdapterOnClick {

    var TAG = "HomeFrag"
    private lateinit var activity: Context

    lateinit var globalClass: GlobalClass
    lateinit var binding: FragmentHomeBinding
    lateinit var repository: Repository
    lateinit var viewModel: HomeFragVM

    lateinit var toolbar: androidx.appcompat.widget.Toolbar;

    val sliderHandler = Handler(Looper.myLooper()!!)

    private var featureArrayList = arrayListOf<FeatureListModel>()

    private var redCircle: FrameLayout? = null
    private var countTextView: TextView? = null
    private val alertCount = 0

    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = requireContext()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        init()
        setToolbar()
        onClick()
        getData()
        setFeatureListAdapter()

        return binding.root
    }

    fun init() {
//        globalClass = GlobalClass.getInstance(activity)
        globalClass = (requireActivity().application as skpustApplication).globalClass
        repository = (requireActivity().application as skpustApplication).repository
        viewModel = ViewModelProvider(this, HomeFragVMFactory(repository))
            .get(HomeFragVM::class.java)
    }

    fun setToolbar() {
        toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = resources.getString(R.string.home)
    }

    fun onClick() {

    }

    fun getData() {

        viewModel.businessList.observe(viewLifecycleOwner, {

            globalClass.log(TAG, "${it.size}")

            setSliderAdapter(it)
            addCompositePageTransformer()
        })
    }

    fun setSliderAdapter(list: List<BusinessDetailModel>) {
        binding.businessSliderViewPager.adapter = homeSliderAdapter(
            activity,
            list as ArrayList<BusinessDetailModel>,
            this,
            binding.businessSliderViewPager
        )
        binding.dotsIndicator.setViewPager2(binding.businessSliderViewPager)

    }

    override fun businessDetail(pos: Int, model: BusinessDetailModel) {

        val action = HomeFragDirections.actionHomeFragmentToBusinessDetailFrag(model)
        findNavController().navigate(action)


        globalClass.log(TAG, model.businessName)
    }

    fun addCompositePageTransformer() {
        binding.businessSliderViewPager.clipToPadding = false
        binding.businessSliderViewPager.clipChildren = false
        binding.businessSliderViewPager.offscreenPageLimit = 3
        binding.businessSliderViewPager.getChildAt(0).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page: View, position: Float ->
            val r = 1 - Math.abs(position)
            page.scaleY = 0.95f + r * 0.05f
        }
        binding.businessSliderViewPager.setPageTransformer(compositePageTransformer)

        binding.businessSliderViewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    sliderHandler.removeCallbacks(sliderRunnable)
                    sliderHandler.postDelayed(sliderRunnable, homeSliderDelayTime)
                }
            })
    }

    private val sliderRunnable: Runnable = Runnable {

        binding.businessSliderViewPager.currentItem =
            binding.businessSliderViewPager.currentItem + 1
    }

    fun setFeatureListAdapter() {
        featureArrayList = viewModel.featureList()
        val recyclerview = binding.recyclerView
        recyclerview.layoutManager = GridLayoutManager(activity, 3)
        val featureListAdapter = featureListAdapter(activity, featureArrayList, this)
        recyclerview.adapter = featureListAdapter
    }

    override fun onFeatureClick(pos: Int, model: FeatureListModel) {

        if (model.featureName == resources.getString(R.string.feed)) {
            findNavController().navigate(R.id.action_homeFragment_to_feedFragment)
        } else if (model.featureName == "Search\nBusiness") {
            findNavController().navigate(R.id.action_homeFragment_to_searchBusinessFrag)
        } else if (model.featureName == resources.getString(R.string.people)) {
            findNavController().navigate(R.id.action_homeFragment_to_peopleFragment)
        } else if (model.featureName == "Search\nPeople") {
            findNavController().navigate(R.id.action_homeFragment_to_peopleSearchFrag)
        }else if (model.featureName == resources.getString(R.string.matrimony)) {
            findNavController().navigate(R.id.action_homeFragment_to_matrimonyFrag)
        } else if (model.featureName == resources.getString(R.string.profile)) {

            findNavController().navigate(R.id.action_homeFragment_to_profileFrag)
        } else if (model.featureName == "Share") {

            requestStoragePermission()
        } else if (model.featureName == resources.getString(R.string.contactus)) {

            findNavController().navigate(R.id.action_homeFragment_to_contactUsFragment)

        } else if (model.featureName == "Send\nFeedback") {

            findNavController().navigate(R.id.action_homeFragment_to_sendFeedbackFrag)

        } else if (model.featureName == "SKPUST\n" +
            "History") {

            findNavController().navigate(R.id.action_homeFragment_to_historyFrag)

//            val url = Constant.skpusthistory
//            val i = Intent(Intent.ACTION_VIEW)
//            i.data = Uri.parse(url)
//            startActivity(i)

        } else if (model.featureName == resources.getString(R.string.logout)) {

            (activity as MainActivity).logout("Menu")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_optionmenu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {

        val alertMenuItem = menu.findItem(R.id.activity_main_alerts_menu_item)
        val rootView = alertMenuItem.actionView as FrameLayout

        redCircle = rootView.findViewById<View>(R.id.view_alert_red_circle) as FrameLayout
        countTextView = rootView.findViewById<View>(R.id.view_alert_count_textview) as TextView
        countTextView!!.text = "3"

        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.menu_search_business) {
            findNavController().navigate(R.id.action_homeFragment_to_searchBusinessFrag)
        }
        return super.onOptionsItemSelected(item)
    }

    //todo shareApp
    fun requestStoragePermission() {
        Dexter.withActivity(requireActivity())
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        shareApp()
                    }
                    if (report.deniedPermissionResponses.size > 0) {
                        globalClass.toastlong("Storage permission required!")
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            })
            .withErrorListener { }
            .onSameThread()
            .check()
    }

    fun shareApp() {
        try {
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .toString() + "/${resources.getString(R.string.app_name)}.png"
            )
            if (file.exists()) {
                val uri = FileProvider.getUriForFile(
                    activity,
                    BuildConfig.APPLICATION_ID,
                    file
                )
                val intent = ShareCompat.IntentBuilder.from(requireActivity())
                    .setStream(uri) // uri from FileProvider
                    .setType("image/*")
                    .intent
                    .setAction(Intent.ACTION_SEND) //Change if needed
                    .setDataAndType(uri, "image/*")
                    .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    .putExtra(Intent.EXTRA_SUBJECT, "EXTRA_SUBJECT")
                    .putExtra(Intent.EXTRA_TEMPLATE, "EXTRA_TEMPLATE")
                    .putExtra(
                        Intent.EXTRA_TEXT,
                        resources.getString(R.string.app_fullname) + " is social app for Kadva Patel Samaj.\n"
                                + "Tap here & download ${resources.getString(R.string.app_name)} app now: https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
                    )
                startActivity(Intent.createChooser(intent, "Share on"))
            } else {
                if (!globalClass.isInternetPresent()) {
                    globalClass.toastlong(getString(R.string.noInternetConnection))
                    return
                }
                val imageUrl: String = Constant.appShareIconUrl
                downloadAppShareImage(imageUrl)
            }
        } catch (e: Exception) {
            val error = Log.getStackTraceString(e)
            globalClass.log(TAG, "shareApp: $error")
            globalClass.toastlong("Something went wrong in share, please try again!")
        }
    }

    fun downloadAppShareImage(appShareImage: String?): Boolean {
        try {
            showprogress("Hold on", "Please wait...")

            val result: Long
            val request = DownloadManager.Request(Uri.parse(appShareImage))
            request.setTitle("/${resources.getString(R.string.app_name)}.png")
            request.setDescription("Please wait")
            //        request.setAllowedNetworkTypes(3);
//        request.setAllowedOverRoaming(false);
//        request.allowScanningByMediaScanner();
//        request.setVisibleInDownloadsUi(true);
//        request.setMimeType("application/pdf");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "/${resources.getString(R.string.app_name)}.png"
            )

            //todo get download status
            val manager = activity.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            result = manager.enqueue(request)
            val ImageDownloadQuery = DownloadManager.Query()
            //set the query filter to our previously Enqueued download
            ImageDownloadQuery.setFilterById(result)

            //Query the download manager about downloads that have been requested.
            val cursor = manager.query(ImageDownloadQuery)
            if (cursor.moveToFirst()) {
                //column for download  status
                val columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                val status = cursor.getInt(columnIndex)
                var statusText = ""
                return if (status == DownloadManager.STATUS_FAILED) {
                    statusText = "STATUS_FAILED"
                    globalClass.log(TAG, statusText)
                    false
                } else {
                    statusText = "STATUS_SUCCESSFUL"
                    globalClass.log(TAG, statusText)
                    true
                }
            }
        } catch (e: Exception) {
            val error = Log.getStackTraceString(e)
            globalClass.log(TAG, "downloadAppShareImage: $error")
            globalClass.toastlong("Something went wrong in share, please try again!")
            return false
        }
        return false
    }

    var DownloadCompleteReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            try {
                globalClass.log(TAG, "DownloadCompleteReceiver: onReceive")
                Handler(Looper.myLooper()!!).postDelayed({
                    hideprogress()
                    shareApp()
                }, 1000)
            } catch (e: Exception) {
                val error = Log.getStackTraceString(e)
                globalClass.log(TAG, "DownloadCompleteReceiver: $error")
                globalClass.toastlong("Something went wrong in share, please try again!")
            }
        }
    }

    fun showprogress(title: String?, message: String?) {
        progressDialog = ProgressDialog(activity)
        progressDialog?.setCancelable(false)
        progressDialog?.setTitle(title)
        progressDialog?.setMessage(message)
        progressDialog?.show()
    }

    fun hideprogress() {
        if (progressDialog != null && progressDialog!!.isShowing()) {
            progressDialog!!.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()

        LocalBroadcastManager.getInstance(activity).registerReceiver(
            DownloadCompleteReceiver, IntentFilter(Constant.DownloadCompleteReceiver)
        )
    }

    override fun onPause() {
        super.onPause()

        sliderHandler.removeCallbacks(sliderRunnable)
        LocalBroadcastManager.getInstance(activity).unregisterReceiver(DownloadCompleteReceiver)
    }
}