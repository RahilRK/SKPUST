package com.rahilkarim.skpust.ui.BusinessDetailFrag

import android.Manifest
import android.app.DownloadManager
import android.app.ProgressDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.*
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.rahilkarim.skpust.R
import com.rahilkarim.skpust.util.GlobalClass
import com.rahilkarim.skpust.util.Repository
import com.rahilkarim.skpust.util.skpustApplication
import com.rahilkarim.skpust.databinding.FragmentBusinessDetailBinding
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.rahilkarim.skpust.BuildConfig
import com.rahilkarim.skpust.util.Constant
import java.io.File
import java.lang.Exception
import java.lang.Math.abs

class BusinessDetailFrag : Fragment()
    ,businessImagesListAdapter.businessImagesListAdapterOnClick {

    var TAG = "BusinessDetailFrag"
    private lateinit var activity: Context

    lateinit var globalClass: GlobalClass
    lateinit var binding: FragmentBusinessDetailBinding
    lateinit var repository: Repository
    lateinit var viewModel: BusinessDetailFragVM

    val args: BusinessDetailFragArgs by navArgs()

    lateinit var toolbar: androidx.appcompat.widget.Toolbar;
    lateinit var collapsingToolbarLayout: CollapsingToolbarLayout;

    var businessImagesList = arrayListOf<String>()
    lateinit var adapter: businessImagesListAdapter;

    lateinit var model: BusinessDetailModel;

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
        binding = FragmentBusinessDetailBinding.inflate(layoutInflater, container, false)

        init()
        setToolbar()
        onClick()
        getData()

        return binding.root
    }

    fun init() {
//        globalClass = GlobalClass.getInstance(activity)
        globalClass = (requireActivity().application as skpustApplication).globalClass
        repository = (requireActivity().application as skpustApplication).repository
        viewModel = ViewModelProvider(this, BusinessDetailFragVMFactory(repository))
            .get(BusinessDetailFragVM::class.java)
    }

    fun setToolbar() {

        toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            getActivity()?.onBackPressed()
        }
        collapsingToolbarLayout = binding.collapsingtoolbar
//        collapsingToolbarLayout.title = resources.getString(R.string.shubham_developers)
        binding.appBar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                when (state) {
                    State.COLLAPSED -> { /* Do something */
//                        globalClass.log(TAG,"COLLAPSED")
                        toolbar.setNavigationIcon(R.drawable.ic_left_arrow)
                    }
                    State.EXPANDED -> { /* Do something */
//                        globalClass.log(TAG,"EXPANDED")
                        toolbar.setNavigationIcon(R.drawable.ic_left_arrow_white)
                    }
                    State.IDLE -> { /* Do something */
//                        globalClass.log(TAG,"IDLE")
                    }
                }
            }
        }
        )
    }

    fun onClick() {

        binding.businessContactNumber.setOnClickListener {

            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${model.businessContactNumber}")
            startActivity(intent)
        }

        binding.openBusinessLocation.setOnClickListener {

            globalClass.openGoogleMap(
                activity,
                "20.9518592,72.9284608"
            )
        }

        binding.fabShareBusiness.setOnClickListener {

            requestStoragePermission()
        }
    }

    fun getData() {

        viewModel.getBusinessDetail(args.businessDetailModelArgu.id).observe(requireActivity(), {

//            globalClass.log(TAG,it.toString())
            model = it
            setText(it)
            fillBusinessImageList(it)
        })
    }

    fun setText(model: BusinessDetailModel) {

        binding.swipeReferesh.isEnabled = false

        collapsingToolbarLayout.title = model.businessName
        binding.businessLocation.text = "${model.businessCity}, ${model.businessState}"
        binding.businessCategory.text = model.businessCategory
        addReadMore(model.businessDescription, binding.businessDescription)
        binding.businessFoundedDate.text = "Year ${model.businessFoundedDate}"
        binding.businessOwnerName.text = model.businessOwnerName
        binding.businessContactNumber.text = Html.fromHtml(
            context!!.resources.getString(
                R.string.openDialer,
                model.businessContactNumber
            ), HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        binding.businessEmailId.text = model.businessEmailId.lowercase()
        binding.businessAddress.text = model.businessAddress
    }

    fun fillBusinessImageList(model: BusinessDetailModel) {

        businessImagesList.clear()
        businessImagesList.add(model.businessImage1)
        businessImagesList.add(model.businessImage2)
        businessImagesList.add(model.businessImage3)

        //todo load 1st Image
        Glide.with(activity)
            .load(model.businessImage1)
            .into(binding.businessImage)

        setBusinessImageListAdapter(businessImagesList)
    }

    fun setBusinessImageListAdapter(arrayList: ArrayList<String>) {

        globalClass.log(TAG, "setBusinessImageListAdapter: ${arrayList.size}")
        val recyclerview = binding.businessImagesRecyclerView
        recyclerview.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        adapter = businessImagesListAdapter(activity, arrayList, this)
        recyclerview.adapter = adapter
    }

    override fun showImage(pos: Int, imagePath: String) {

        Glide.with(activity)
            .load(imagePath)
            .into(binding.businessImage)
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

    abstract class AppBarStateChangeListener : OnOffsetChangedListener {

        enum class State {
            EXPANDED, COLLAPSED, IDLE
        }

        private var mCurrentState = State.IDLE

        override fun onOffsetChanged(appBarLayout: AppBarLayout, i: Int) {
            if (i == 0 && mCurrentState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED)
                mCurrentState = State.EXPANDED
            } else if (abs(i) >= appBarLayout.totalScrollRange && mCurrentState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED)
                mCurrentState = State.COLLAPSED
            } else if (mCurrentState != State.IDLE) {
                onStateChanged(appBarLayout, State.IDLE)
                mCurrentState = State.IDLE
            }
        }

        abstract fun onStateChanged(
            appBarLayout: AppBarLayout?,
            state: State?
        )

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.businessdetail_optionmenu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.menu_share_business){

            requestStoragePermission()
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
                    .toString() + "/${model.businessName.replace("","_")}.png"
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
                    .putExtra(Intent.EXTRA_TEXT, model.businessName+" is popular "
                            +model.businessCategory+" at "+model.businessCity+", "+model.businessState+".\n"
                            +"Tap here to get more detail or download SKPUST app now.")
                startActivity(Intent.createChooser(intent, "Share on"))
            } else {
                if (!globalClass.isInternetPresent()) {
                    globalClass.toastlong(getString(R.string.noInternetConnection))
                    return
                }
                val imageUrl: String = model.businessBannerImage
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
            request.setTitle("/${model.businessName.replace(" ","_")}.png")
            request.setDescription("Please wait")
            //        request.setAllowedNetworkTypes(3);
//        request.setAllowedOverRoaming(false);
//        request.allowScanningByMediaScanner();
//        request.setVisibleInDownloadsUi(true);
//        request.setMimeType("application/pdf");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "/${model.businessName.replace("","_")}.png"
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

        LocalBroadcastManager.getInstance(activity).unregisterReceiver(DownloadCompleteReceiver)
    }
}