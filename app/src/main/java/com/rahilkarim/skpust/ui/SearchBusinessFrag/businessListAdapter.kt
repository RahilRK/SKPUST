package com.rahilkarim.skpust.ui.BusinessDetailFrag

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rahilkarim.skpust.databinding.HomebusinesslistItemBinding

class businessListAdapter(
    private val activity: Context,
    private val list: ArrayList<BusinessDetailModel>,
    private val onClick: homeBusinessListAdapterOnClick,
) : RecyclerView.Adapter<businessListAdapter.ViewHolder>() {

    var tag = "businessListAdapter"
    lateinit var binding: HomebusinesslistItemBinding

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        binding = HomebusinesslistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = list[position]
        Glide.with(activity)
            .load(model.businessImage1)
            .into(binding.businessImage);
        binding.businessName.text = model.businessName
        binding.businessCategory.text = model.businessCategory
        binding.businessLocation.text = "${model.businessCity}, ${model.businessState}"
        binding.businessContactNumber.text = "+91 ${model.businessContactNumber}"

        binding.businessImage.setOnClickListener {

            onClick.openBusinessDetail(position,model)
        }

        binding.businessContactNumber.setOnClickListener {

            onClick.callBusinessOwner(position,model)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return list.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
    }

    public interface homeBusinessListAdapterOnClick {

        fun openBusinessDetail(pos: Int, model: BusinessDetailModel)
        fun callBusinessOwner(pos: Int, model: BusinessDetailModel)
    }
}