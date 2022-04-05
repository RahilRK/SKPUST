package com.rahilkarim.skpust.ui

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.rahilkarim.skpust.util.GlobalClass
import com.rahilkarim.skpust.util.skpustApplication
import com.rahilkarim.skpust.databinding.FragmentSendFeedbackBinding
import droidninja.filepicker.FilePickerBuilder
import java.util.ArrayList

import android.app.Activity

import android.content.Intent
import com.bumptech.glide.Glide
import droidninja.filepicker.FilePickerConst.KEY_SELECTED_MEDIA
import droidninja.filepicker.FilePickerConst.REQUEST_CODE_PHOTO
import android.database.Cursor
import android.provider.MediaStore
import java.lang.Exception

import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.rahilkarim.skpust.R


class SendFeedbackFrag : Fragment() {

    var TAG = "HomeFrag"
    private lateinit var activity: Context

    lateinit var globalClass: GlobalClass
    lateinit var binding: FragmentSendFeedbackBinding

    lateinit var toolbar: androidx.appcompat.widget.Toolbar;

    var selectedimagesArrayList = ArrayList<Uri>()

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
        binding = FragmentSendFeedbackBinding.inflate(layoutInflater, container, false)

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
        toolbar.title = resources.getString(R.string.send_feedback)
        toolbar.setNavigationOnClickListener {
            getActivity()?.onBackPressed()
        }
    }

    fun onClick() {

        binding.ivfeedbackimg.setOnClickListener {
            requestStoragePermission()
        }
    }

    fun requestStoragePermission() {

        Dexter.withActivity(requireActivity())
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        globalClass.log(TAG, "Storage permission Granted")
                        OpenImagePicker()
                    }
                    if (report.deniedPermissionResponses.size > 0) {
                        globalClass.log(TAG, "Storage permission Denied")
                        globalClass.toastlong("Storage permission required to choose image")
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            })
            .withErrorListener { error ->
                globalClass.log(TAG + "__requestStoragePermission", error.toString())
            }
            .onSameThread()
            .check()
    }

    fun OpenImagePicker() {
        selectedimagesArrayList.clear()
        FilePickerBuilder.instance
            .setMaxCount(1)
            .setSelectedFiles(selectedimagesArrayList)
            .setActivityTheme(R.style.LibAppTheme)
            .pickPhoto(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_PHOTO) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                selectedimagesArrayList.clear()
                data.getParcelableArrayListExtra<Uri>(KEY_SELECTED_MEDIA)
                    ?.let { selectedimagesArrayList.addAll(it) }

                if(selectedimagesArrayList.isNotEmpty()) {

                    val uri = selectedimagesArrayList.get(0)
                    globalClass.log(TAG,"path: "+uri.path)
                    globalClass.log(TAG,"getRealPathFromURI: "+getRealPathFromURI(activity,uri))

                    val requestOptions: RequestOptions = RequestOptions
                        .bitmapTransform(RoundedCorners(16))
                    Glide.with(activity)
                        .load(uri)
                        .apply(requestOptions)
                        .into(binding.ivfeedbackimg);
                }
                else {
                    globalClass.toastlong("Unable to load image")
                }
            }
        }
    }

    private fun getRealPathFromURI(context: Context, contentUri: Uri): String? {
        var cursor: Cursor? = null
        return try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, proj, null, null, null)
            val column_index: Int = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(column_index)
        } catch (e: Exception) {
            globalClass.log(TAG, "getRealPathFromURI Exception : $e")
            ""
        } finally {
            if (cursor != null) {
                cursor.close()
            }
        }
    }
}