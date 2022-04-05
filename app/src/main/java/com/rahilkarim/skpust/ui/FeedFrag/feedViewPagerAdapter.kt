package com.rahilkarim.skpust.ui.FeedFrag.feedImageSlider

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.rahilkarim.skpust.ui.FeedFrag.FeedModel
import com.rahilkarim.skpust.R

import java.util.ArrayList



class feedViewPagerAdapter(
    list: List<String>?,
    private val mContext: Context,
    private val model: FeedModel,
) : PagerAdapter() {

    private val mInflater: LayoutInflater

    private val mPagerList = ArrayList<String>()

    init {

        if (list != null && list.isNotEmpty())
            mPagerList.addAll(list)

        this.mInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val rootView = mInflater.inflate(R.layout.feed_viewpager_item, container, false)

        val holder = ViewHolder(rootView)

        val data = mPagerList[position]

        holder.imageView.setOnClickListener {

            val intent = Intent("OpenRecipeReceiver")
            intent.putExtra("id",model.id)
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent)
        }

        Glide.with(mContext)
            .load(data)
            .into(holder.imageView)

        container.addView(rootView)

        return rootView
    }

    override fun getCount(): Int {

        return mPagerList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        // Don't call the super
        // super.destroyItem(container, position, object);

        container.removeView(`object` as View)
    }

    /**
     * [android.support.v7.widget.RecyclerView.ViewHolder]
     */
    internal inner class ViewHolder(rootView: View) {

        var imageView:ImageView

        init {
            imageView= rootView.findViewById(R.id.imageViewViewpager)
        }
    }

    interface feedViewPagerImageOnClick {

        fun imageOnClick()
    }
}
