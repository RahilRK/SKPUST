package com.rahilkarim.skpust.ui.FeedFrag

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rahilkarim.skpust.ui.BusinessDetailFrag.feedListAdapter
import com.rahilkarim.skpust.R
import com.rahilkarim.skpust.util.GlobalClass
import com.rahilkarim.skpust.util.Repository
import com.rahilkarim.skpust.util.skpustApplication
import com.rahilkarim.skpust.databinding.FragmentFeedBinding
import java.lang.Exception

class FeedFrag : Fragment(),feedListAdapter.feedListAdapterOnClick {

    var TAG = "FeedFrag"
    private lateinit var activity: Context

    lateinit var globalClass: GlobalClass
    lateinit var binding: FragmentFeedBinding
    lateinit var repository: Repository
    lateinit var viewModel: FeedFragVM

    lateinit var toolbar: androidx.appcompat.widget.Toolbar;

    private var arrayList = arrayListOf<FeedModel>()
    lateinit var adapter: feedListAdapter;

    var menu_search: MenuItem? = null
    var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = requireContext()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFeedBinding.inflate(layoutInflater, container, false)

        init()
        setToolbar()
        getData()

        return binding.root
    }

    fun init() {
//        globalClass = GlobalClass.getInstance(activity)
        globalClass = (requireActivity().application as skpustApplication).globalClass
        repository = (requireActivity().application as skpustApplication).repository
        viewModel = ViewModelProvider(this, FeedFragVMFactory(repository))
            .get(FeedFragVM::class.java)
    }

    fun setToolbar() {
        toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = resources.getString(R.string.feed)
    }

    fun getData() {

        viewModel.feedList.observe(
            viewLifecycleOwner,
            {
                arrayList.clear()
                arrayList = it as ArrayList<FeedModel>
                if (!arrayList.isEmpty()) {
                    setAdapter(arrayList)
                } else {
                    globalClass.toastlong("No data found")
                }
            })
    }

    fun setAdapter(arrayList: ArrayList<FeedModel>) {

        val recyclerview = binding.feedRecyclerView
        recyclerview.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false)
        recyclerview.setHasFixedSize(false)
        adapter = feedListAdapter(activity, arrayList,this)
        recyclerview.adapter = adapter
    }

    override fun showFeedDetail(pos: Int, model: FeedModel) {

        Log.e("showFeedDetail",model.functionName)
        val action =
            FeedFragDirections.actionFeedFragmentToFeedDetailFrag(model.id)
        findNavController().navigate(action)
    }

    var OpenRecipeReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            try {

                val getId = intent.getIntExtra("id",-1)
                val action =
                    FeedFragDirections.actionFeedFragmentToFeedDetailFrag(getId)
                findNavController().navigate(action)

            } catch (e: Exception) {
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.feed_optionmenu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        menu_search = menu.findItem(R.id.menu_search_feed)
        searchView = menu_search?.actionView as SearchView
        searchView!!.queryHint = "Enter name"
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                if (s.isNotEmpty()) {
                    getSearchData(s)
                } else {
                    globalClass.toastshort("Enter name")
                }
                return true
            }

            override fun onQueryTextChange(s: String): Boolean {
                if (s.isNotEmpty()) {
                    getSearchData(s)
                } else {
                    getData()
                }
                return true
            }
        })
    }

    fun getSearchData(businessName: String) {

        viewModel.searchFeedList(businessName).observe(
            viewLifecycleOwner,
            {
                arrayList.clear()
                arrayList = it as ArrayList<FeedModel>
                if (!arrayList.isEmpty()) {
                    setAdapter(arrayList)
                } else {
                    globalClass.toastshort("No data found")
                }
            })
    }

    override fun onResume() {
        super.onResume()

        LocalBroadcastManager.getInstance(activity).registerReceiver(
            OpenRecipeReceiver, IntentFilter("OpenRecipeReceiver")
        )
    }

    override fun onPause() {
        super.onPause()

        LocalBroadcastManager.getInstance(activity).unregisterReceiver(OpenRecipeReceiver)
    }
}