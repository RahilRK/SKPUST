package com.rahilkarim.skpust.ui.BusinessDetailFrag

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rahilkarim.skpust.ui.PeopleDetailFrag.FamilyMemberListModel
import com.rahilkarim.skpust.databinding.FamilymemberlistItemBinding

class familyMemberListAdapter(
    private val activity: Context,
    private val list: ArrayList<FamilyMemberListModel>,
) : RecyclerView.Adapter<familyMemberListAdapter.ViewHolder>() {

    var tag = "familyMemberListAdapter"
    lateinit var binding: FamilymemberlistItemBinding

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        binding = FamilymemberlistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = list[position]
        Glide.with(activity)
            .load(model.familyMemberImage)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.familyMemberImage);
        binding.familyMemberName.text = model.familyMemberName
        binding.familyMemberRelation.text = model.familyMemberRelation
        binding.familyMemberImage.setOnClickListener {
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return list.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
    }
}