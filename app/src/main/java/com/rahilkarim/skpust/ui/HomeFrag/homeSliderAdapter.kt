import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.rahilkarim.skpust.ui.BusinessDetailFrag.BusinessDetailModel
import com.rahilkarim.skpust.databinding.HomesliderItemBinding

class homeSliderAdapter(
    private val activity: Context,
    private val list: ArrayList<BusinessDetailModel>,
    private val onClick: homeSliderAdapterOnClick,
    private val viewPager2: ViewPager2
) : RecyclerView.Adapter<homeSliderAdapter.ViewHolder>() {

    var tag = "homeSliderAdapter"
    lateinit var binding: HomesliderItemBinding

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        binding = HomesliderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = list[position]
        Glide.with(activity)
            .load(model.businessBannerImage)
            .into(binding.businessImageItem);

        if(position == list.size - 2) {
            viewPager2.post { runn }
        }

        binding.businessImageItem.setOnClickListener {

            onClick.businessDetail(position,model)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return list.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
    }

    private val runn = Runnable {
        list.addAll(list)
        notifyDataSetChanged()
    }

    public interface homeSliderAdapterOnClick {

        fun businessDetail(pos: Int, model: BusinessDetailModel)
    }
}