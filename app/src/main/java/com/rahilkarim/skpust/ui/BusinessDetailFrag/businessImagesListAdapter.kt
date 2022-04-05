package com.rahilkarim.skpust.ui.BusinessDetailFrag

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rahilkarim.skpust.databinding.BusinessImagesListItemBinding
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.load.resource.bitmap.RoundedCorners


class businessImagesListAdapter(
    private val activity: Context,
    private val list: ArrayList<String>,
    private val onClick: businessImagesListAdapterOnClick,
) : RecyclerView.Adapter<businessImagesListAdapter.ViewHolder>() {

    var tag = "businessImagesListAdapter"
    lateinit var binding: BusinessImagesListItemBinding

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        binding = BusinessImagesListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding.root)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val imagePath = list[position]
        val requestOptions: RequestOptions = RequestOptions
            .bitmapTransform(RoundedCorners(16))
        Glide.with(activity)
            .load(imagePath)
            .apply(requestOptions)
            .into(binding.businessImage);

        binding.businessImage.setOnClickListener {

            onClick.showImage(position,imagePath)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return list.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
    }

    interface businessImagesListAdapterOnClick {

        fun showImage(pos: Int, imagePath: String)
    }
}