package com.rahilkarim.skpust.ui.BusinessDetailFrag

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rahilkarim.skpust.ui.MatrimonyFrag.MatrimonyUserDetailModel
import com.rahilkarim.skpust.databinding.MatrimonyuserlistItemBinding

class matrimonyUserListAdapter(
    private val activity: Context,
    private val list: ArrayList<MatrimonyUserDetailModel>,
    private val onClick: matrimonyUserListAdapterOnClick,
) : RecyclerView.Adapter<matrimonyUserListAdapter.ViewHolder>() {

    var tag = "matrimonyUserListAdapter"
    lateinit var binding: MatrimonyuserlistItemBinding

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        binding = MatrimonyuserlistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = list[position]
        Glide.with(activity)
            .load(model.userImage1)
            .into(binding.userImage1);

        binding.userName.text = model.userName
        binding.userOccupation.text = "${model.userOccupation}, ${model.userCity}"
        binding.userDOB.text = "${model.userDOB} yrs, ${model.userHeight}ft "
        binding.userCity.text = "${model.userCity}, ${model.userState} "

        binding.cardView.setOnClickListener {

            onClick.matrimonyUserDetail(position,model)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return list.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
    }

    public interface matrimonyUserListAdapterOnClick {

        fun matrimonyUserDetail(pos: Int, model: MatrimonyUserDetailModel)
    }
}