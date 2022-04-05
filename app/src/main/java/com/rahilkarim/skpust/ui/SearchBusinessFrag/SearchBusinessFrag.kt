package com.rahilkarim.skpust.ui.SearchBusinessFrag

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rahilkarim.skpust.ui.BusinessDetailFrag.BusinessDetailModel
import com.rahilkarim.skpust.R
import com.rahilkarim.skpust.util.GlobalClass
import com.rahilkarim.skpust.util.Repository
import com.rahilkarim.skpust.util.skpustApplication
import com.rahilkarim.skpust.databinding.FragmentSearchBusinessBinding
import com.rahilkarim.skpust.ui.BusinessDetailFrag.businessListAdapter

class SearchBusinessFrag : Fragment(),
    businessListAdapter.homeBusinessListAdapterOnClick {

    var TAG = "SearchBusinessFrag"
    private lateinit var activity: Context

    lateinit var globalClass: GlobalClass
    lateinit var binding: FragmentSearchBusinessBinding
    lateinit var repository: Repository
    lateinit var viewModel: SearchBusinessFragVM

    lateinit var toolbar: androidx.appcompat.widget.Toolbar;

    private var arrayList = arrayListOf<BusinessDetailModel>()
    lateinit var adapter: businessListAdapter;

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
        binding = FragmentSearchBusinessBinding.inflate(layoutInflater, container, false)

        init()
        setToolbar()
        onClick()
        getData()

        return binding.root
    }

    fun init() {
//        globalClass = GlobalClass.getInstance(activity)
        globalClass = (requireActivity().application as skpustApplication).globalClass
        repository = (requireActivity().application as skpustApplication).repository
        viewModel = ViewModelProvider(this, SearchBusinessFragVMFactory(repository))
            .get(SearchBusinessFragVM::class.java)
    }

    fun setToolbar() {
        toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = resources.getString(R.string.search_business)
    }

    fun onClick() {
        toolbar.setNavigationOnClickListener {

            requireActivity().onBackPressed()
        }
    }

    fun getData() {

        viewModel.businessList.observe(
            viewLifecycleOwner,
            {
                arrayList.clear()
                arrayList = it as ArrayList<BusinessDetailModel>
                setAdapter(arrayList)
            })
    }

    fun setAdapter(arrayList: ArrayList<BusinessDetailModel>) {

        globalClass.log(TAG, "setAdapter: ${arrayList.size}")
        val recyclerview = binding.recyclerView
        recyclerview.layoutManager = LinearLayoutManager(context)
        adapter = businessListAdapter(activity, arrayList, this)
        recyclerview.adapter = adapter
    }

    override fun openBusinessDetail(pos: Int, model: BusinessDetailModel) {

        val action =
            SearchBusinessFragDirections.actionSearchBusinessFragToBusinessDetailFrag(model)
        findNavController().navigate(action)
    }

    override fun callBusinessOwner(pos: Int, model: BusinessDetailModel) {

       globalClass.openDialer(activity,model.businessContactNumber)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.searchbusiness_optionmenu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        menu_search = menu.findItem(R.id.menu_search_business)
        searchView = menu_search?.actionView as SearchView
        searchView!!.queryHint = "Enter business name"
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                if (s.isNotEmpty()) {
                    getSearchData(s)
                } else {
                    globalClass.toastshort("Enter business name")
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

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu_search!!.expandActionView()
    }

    fun getSearchData(businessName: String) {

        viewModel.searchBusinessList(businessName).observe(
            viewLifecycleOwner,
            {
                arrayList.clear()
                arrayList = it as ArrayList<BusinessDetailModel>
                if (!arrayList.isEmpty()) {
                    setAdapter(arrayList)
                } else {
                    globalClass.toastshort("No data found")
                }
            })
    }
}