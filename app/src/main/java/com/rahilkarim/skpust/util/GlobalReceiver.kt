package com.rahilkarim.skpust.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager

public class GlobalReceiver : BroadcastReceiver() {

    var tag = "GlobalReceiver"

    lateinit var globalClass: GlobalClass

    override fun onReceive(context: Context?, intent: Intent?) {

        init(context)

        if (intent!!.action != null && intent!!.action.equals(
                "android.intent.action.DOWNLOAD_COMPLETE",
                ignoreCase = true
            )
        ) {
            globalClass.log(tag, "DOWNLOAD_COMPLETE")
            val downloadCompleteIntent = Intent(Constant.DownloadCompleteReceiver)
            LocalBroadcastManager.getInstance(context!!).sendBroadcast(downloadCompleteIntent)
        }
    }

    fun init(context: Context?) {
        globalClass = GlobalClass.getInstance(context!!)
    }
}