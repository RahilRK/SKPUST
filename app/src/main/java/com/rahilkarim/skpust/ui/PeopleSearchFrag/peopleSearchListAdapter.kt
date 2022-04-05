package com.rahilkarim.skpust.ui.BusinessDetailFrag

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rahilkarim.skpust.databinding.PeopleSearchlistItemBinding
import com.rahilkarim.skpust.databinding.PeoplelistItemBinding
import com.rahilkarim.skpust.models.peopleList.PeopleList
import com.rahilkarim.skpust.ui.PeopleFrag.PeopleListModel


class peopleSearchListAdapter(
    private val activity: Context,
    private val list: ArrayList<PeopleList>,
//    private val onClick: peopleListAdapterOnClick,
) : RecyclerView.Adapter<peopleSearchListAdapter.ViewHolder>() {

    var tag = "peopleSearchListAdapter"
    lateinit var binding: PeopleSearchlistItemBinding

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        binding = PeopleSearchlistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = list[holder.adapterPosition]
        Glide.with(activity)
            .load(model.profileImage)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.userImage)
        binding.userName.text = model.userName + ": ${model.userID}"
        binding.userCity.text = "${model.nativePlace}, ${model.nativeState}"

        binding.cardView.setOnClickListener {

//            onClick.openPeopleDetail(position,model)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
    }

    public interface peopleListAdapterOnClick {

        fun openPeopleDetail(pos: Int, model: PeopleListModel)
    }

    fun add(movie: PeopleList) {
        list.add(movie)
        notifyItemInserted(list.size - 1)
    }

    fun addAll(moveResults: List<PeopleList>) {
        for (result in moveResults) {
            add(result)
        }
    }
}