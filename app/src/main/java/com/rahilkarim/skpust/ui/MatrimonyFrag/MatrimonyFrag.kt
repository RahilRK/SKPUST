package com.rahilkarim.skpust.ui.MatrimonyFrag

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rahilkarim.skpust.ui.BusinessDetailFrag.matrimonyUserListAdapter
import com.rahilkarim.skpust.R
import com.rahilkarim.skpust.util.GlobalClass
import com.rahilkarim.skpust.util.Repository
import com.rahilkarim.skpust.util.skpustApplication
import com.rahilkarim.skpust.databinding.FragmentMatrimonyBinding

class MatrimonyFrag : Fragment(),matrimonyUserListAdapter.matrimonyUserListAdapterOnClick {

    var TAG = "MatrimonyFrag"
    private lateinit var activity: Context

    lateinit var globalClass: GlobalClass
    lateinit var binding: FragmentMatrimonyBinding
    lateinit var repository: Repository
    lateinit var viewModel: MatrimonyFragVM

    lateinit var toolbar: androidx.appcompat.widget.Toolbar;

    private var arrayList = arrayListOf<MatrimonyUserDetailModel>()
    lateinit var adapter: matrimonyUserListAdapter

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
        binding = FragmentMatrimonyBinding.inflate(layoutInflater, container, false)

        init()
        setToolbar()
        getData()

        return binding.root
    }

    fun init() {
//        globalClass = GlobalClass.getInstance(activity)
        globalClass = (requireActivity().application as skpustApplication).globalClass
        repository = (requireActivity().application as skpustApplication).repository
        viewModel = ViewModelProvider(this, MatrimonyFragVMFactory(repository))
            .get(MatrimonyFragVM::class.java)
    }

    fun setToolbar() {
        toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = resources.getString(R.string.matches)
    }

    fun getData() {

        viewModel.matrimonyUserList.observe(
            viewLifecycleOwner,
            {
                arrayList.clear()
                arrayList = it as ArrayList<MatrimonyUserDetailModel>
                if (!arrayList.isEmpty()) {
                    setAdapter(arrayList)
                } else {
                    globalClass.toastlong("No data found")
                }
            })
    }

    fun setAdapter(arrayList: ArrayList<MatrimonyUserDetailModel>) {

        val recyclerview = binding.matrimonyRecyclerView
        recyclerview.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false)
        recyclerview.setHasFixedSize(false)
        adapter = matrimonyUserListAdapter(activity, arrayList,this)
        recyclerview.adapter = adapter
    }

    override fun matrimonyUserDetail(pos: Int, model: MatrimonyUserDetailModel) {

        val action = MatrimonyFragDirections.actionMatrimonyFragmentToMatrimonyUserDetailFrag(model.userId)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.matrimony_optionmenu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        menu_search = menu.findItem(R.id.menu_search_matrimonyuser_feed)
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

        viewModel.searchMatrimonyUserFeedList(businessName).observe(
            viewLifecycleOwner,
            {
                arrayList.clear()
                arrayList = it as ArrayList<MatrimonyUserDetailModel>
                if (!arrayList.isEmpty()) {
                    setAdapter(arrayList)
                } else {
                    globalClass.toastshort("No data found")
                }
            })
    }
}