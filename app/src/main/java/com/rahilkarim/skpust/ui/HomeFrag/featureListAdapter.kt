package com.rahilkarim.skpust.ui.BusinessDetailFrag

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rahilkarim.skpust.ui.HomeFrag.FeatureListModel
import com.rahilkarim.skpust.databinding.FeaturelistItemBinding

class featureListAdapter(
    private val activity: Context,
    private val list: ArrayList<FeatureListModel>,
    private val onClick: featureListAdapterOnClick,
) : RecyclerView.Adapter<featureListAdapter.ViewHolder>() {

    var tag = "featureAdapter"
    lateinit var binding: FeaturelistItemBinding

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        binding = FeaturelistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = list[position]
        Glide.with(activity)
            .load(model.featureIcon)
            .into(binding.featureIcon);
        binding.featureName.text = model.featureName
        binding.featureIcon.setOnClickListener {
            onClick.onFeatureClick(position,model)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return list.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
    }

    public interface featureListAdapterOnClick {

        fun onFeatureClick(pos: Int, model: FeatureListModel)
    }
}