package com.rahilkarim.skpust.ui.BusinessDetailFrag

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rahilkarim.skpust.ui.FeedFrag.FeedModel
import com.rahilkarim.skpust.ui.FeedFrag.feedImageSlider.feedViewPagerAdapter
import com.rahilkarim.skpust.databinding.FeedRecyclerViewpagerItemBinding
import java.util.HashMap


class feedListAdapter(
    private val activity: Context,
    private val list: ArrayList<FeedModel>, private val onClick: feedListAdapterOnClick
) : RecyclerView.Adapter<feedListAdapter.ViewHolder>() {

    var tag = "feedListAdapter"
    lateinit var binding: FeedRecyclerViewpagerItemBinding

    private val mViewPageStates = HashMap<Int, Int>()

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        binding = FeedRecyclerViewpagerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding.root)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = list[position]

        Glide.with(activity)
            .load(model.functionImage1)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.functionLogo)

        binding.functionName.text = model.functionName
        binding.functionCity.text = "${model.functionCity}, ${model.functionState}"
        binding.functionLikeCount.text = model.functionLikeCount.toString()
        binding.functionCommentCount.text = model.functionCommentCount.toString()

        configurePagerHolder(position,model)

        binding.feedViewPageSlides.setOnClickListener {

            Toast.makeText(activity,"aa",Toast.LENGTH_SHORT).show()
//            onClick.showFeedDetail(position,model)
        }
    }

    private fun configurePagerHolder(position: Int,model: FeedModel) {

        val model = list[position]

        val functionImage1 = list[position].functionImage1
        val functionImage2 = list[position].functionImage2
        val functionImage3 = list[position].functionImage3
        val functionImage4 = list[position].functionImage4

        val sliderImages = arrayListOf<String>()

        sliderImages.add(functionImage1)
        sliderImages.add(functionImage2)
        sliderImages.add(functionImage3)
        sliderImages.add(functionImage4)

        model.feedPageImagesList = sliderImages

        val feedViewPagerAdp = feedViewPagerAdapter(
            model.feedPageImagesList,
            activity,
            model
        )
        binding.feedViewPageSlides.adapter = feedViewPagerAdp
        binding.feedimagesDotsIndicator.setViewPager(binding.feedViewPageSlides)

        if (mViewPageStates.containsKey(position)) {
            binding.feedViewPageSlides.currentItem = mViewPageStates[position]!!
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        if (holder is ViewHolder) {

            mViewPageStates[holder.getAdapterPosition()] = binding.feedViewPageSlides.currentItem
            super.onViewRecycled(holder)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return list.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

    }

    interface feedListAdapterOnClick {

        fun showFeedDetail(pos: Int, model: FeedModel)
    }
}