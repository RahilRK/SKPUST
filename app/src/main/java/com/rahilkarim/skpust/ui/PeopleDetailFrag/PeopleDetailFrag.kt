package com.rahilkarim.skpust.ui.PeopleDetailFrag

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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.bumptech.glide.request.RequestOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.rahilkarim.skpust.BuildConfig
import com.rahilkarim.skpust.ui.BusinessDetailFrag.familyMemberListAdapter
import com.rahilkarim.skpust.ui.PeopleFrag.PeopleListModel
import com.rahilkarim.skpust.R
import com.rahilkarim.skpust.util.Constant
import com.rahilkarim.skpust.util.GlobalClass
import com.rahilkarim.skpust.util.Repository
import com.rahilkarim.skpust.util.skpustApplication
import com.rahilkarim.skpust.databinding.FragmentPeopleDetailBinding
import java.io.File
import java.lang.Exception

class PeopleDetailFrag : Fragment() {

    var TAG = "PeopleDetailFrag"
    private lateinit var activity: Context

    lateinit var globalClass: GlobalClass
    lateinit var binding: FragmentPeopleDetailBinding
    lateinit var repository: Repository
    lateinit var viewModel: PeopleDetailFragVM

    val args: PeopleDetailFragArgs by navArgs()

    var familyMemberArrayList = arrayListOf<FamilyMemberListModel>()
    lateinit var adapter: familyMemberListAdapter

    lateinit var toolbar: androidx.appcompat.widget.Toolbar

    lateinit var model: PeopleListModel

    var progressDialog: ProgressDialog? = null

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
        binding = FragmentPeopleDetailBinding.inflate(layoutInflater, container, false)

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
        viewModel = ViewModelProvider(this, PeopleDetailFragVMFactory(repository))
            .get(PeopleDetailFragVM::class.java)
    }

    fun setToolbar() {
        toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = resources.getString(R.string.detail)
        toolbar.setNavigationOnClickListener {
            getActivity()?.onBackPressed()
        }
    }

    fun onClick() {

        binding.userMobileNumber.setOnClickListener {
            globalClass.openDialer(activity,model.userMobileNumber)
        }

        binding.openPeopleLocation.setOnClickListener {

            globalClass.openGoogleMap(
                activity,
                "20.9518592,72.9284608"
            )
        }

        binding.fabSharePeople.setOnClickListener {

            requestStoragePermission()
        }
    }

    fun getData() {
        viewModel.peopleDetail(args.userid).observe(requireActivity(),{

            model = it
            globalClass.log(TAG,""+it)
            setText(it)
        })
    }

    fun setText(model: PeopleListModel) {

        Glide.with(activity)
            .load(model.userImage)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.userImage)

        binding.userName.text = model.userName
//        binding.userMobileNumber.text = "Contact no - +91 ${model.userMobileNumber}"
        binding.userMobileNumber.text = Html.fromHtml(
            context!!.resources.getString(
                R.string.openDialer,
                model.userMobileNumber
            ), HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        binding.userDOB.text = "DOB - ${globalClass.changedateTimeFormat(
            "yyyy-mm-dd",
            "dd MMM yyyy",
            model.userDOB
        )}"
        binding.userBloodGroup.text = "Blood group - ${model.userBloodGroup}"
        binding.userAddress.text = model.userAddress
        binding.userCity.text = "${model.userCity}, ${model.userState}"

        fillFamilyMemberList(model)
        addReadMore(resources.getString(R.string.softwarecompany_desc), binding.businessDescription)
    }

    fun fillFamilyMemberList(model: PeopleListModel) {

        familyMemberArrayList.clear()
        familyMemberArrayList.add(FamilyMemberListModel(model.motherName,model.motherImage,"Mother"))
        familyMemberArrayList.add(FamilyMemberListModel(model.fatherName,model.fatherImage,"Father"))
        familyMemberArrayList.add(FamilyMemberListModel(model.sisterName,model.sisterImage,"Sister"))
        familyMemberArrayList.add(FamilyMemberListModel(model.brotherName,model.brotherImage,"Brother"))

        setFamilyMemberListAdapter(familyMemberArrayList)
    }

    fun setFamilyMemberListAdapter(arrayList: ArrayList<FamilyMemberListModel>) {

        globalClass.log(TAG, "setBusinessImageListAdapter: ${arrayList.size}")
        val recyclerview = binding.familyMemberRecyclerView
        recyclerview.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)
        adapter = familyMemberListAdapter(activity, arrayList)
        recyclerview.adapter = adapter
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
                    .toString() + "/${model.id}.png"
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
                        Intent.EXTRA_TEXT, model.userName+" is on SKPUST.\n"
                            +"Tap here to connect with ${model.userName} or download SKPUST app now.")
                startActivity(Intent.createChooser(intent, "Share on"))
            } else {
                if (!globalClass.isInternetPresent()) {
                    globalClass.toastlong(getString(R.string.noInternetConnection))
                    return
                }
                val imageUrl: String = model.userImage
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
            request.setTitle("/${model.id}.png")
            request.setDescription("Please wait")
            //        request.setAllowedNetworkTypes(3);
//        request.setAllowedOverRoaming(false);
//        request.allowScanningByMediaScanner();
//        request.setVisibleInDownloadsUi(true);
//        request.setMimeType("application/pdf");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "/${model.id}.png"
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