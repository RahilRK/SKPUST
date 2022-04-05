package com.rahilkarim.skpust.ui.PeopleSearchFrag

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rahilkarim.skpust.R
import com.rahilkarim.skpust.databinding.FragmentPeopleBinding
import com.rahilkarim.skpust.models.peopleList.PeopleList
import com.rahilkarim.skpust.ui.BusinessDetailFrag.peopleSearchListAdapter
import com.rahilkarim.skpust.util.Constant.paginationLimit
import com.rahilkarim.skpust.util.GlobalClass
import com.rahilkarim.skpust.util.Repository
import com.rahilkarim.skpust.util.skpustApplication
import kotlinx.android.synthetic.main.fragment_people.*
import kotlinx.android.synthetic.main.fragment_people.paginationProgressBar
import kotlinx.android.synthetic.main.fragment_people.recyclerView
import kotlinx.android.synthetic.main.fragment_people.toolbar
import kotlinx.android.synthetic.main.fragment_people_search.*
import java.util.*
import kotlin.collections.ArrayList

class PeopleSearchFrag : Fragment(R.layout.fragment_people_search) {

    var TAG = "PeopleSearchFrag"
    private lateinit var activity: Context

    lateinit var globalClass: GlobalClass
    lateinit var binding: FragmentPeopleBinding
    lateinit var repository: Repository
    lateinit var viewModel: PeopleSearchFragVM

//    private var arrayList = arrayListOf<PeopleListModel>()
    var adapter: peopleSearchListAdapter? = null;

    var menu_search: MenuItem? = null
    var searchView: SearchView? = null

    var isLoading = false
    var offset = 0
    var totalItem = 0

    var searchKeyword: String = ""
    var doSearch = false

    private var arrayList = arrayListOf<PeopleList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = requireContext()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        setToolbar()
        onClick()
        observeData()
    }

    fun init() {
//        globalClass = GlobalClass.getInstance(activity)
        globalClass = (requireActivity().application as skpustApplication).globalClass
        repository = (requireActivity().application as skpustApplication).repository
        viewModel = ViewModelProvider(this, PeopleSearchFragVMFactory(repository,globalClass))
            .get(PeopleSearchFragVM::class.java)
    }

    fun setToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = resources.getString(R.string.search_people)
    }

    fun onClick() {
        recyclerView.addOnScrollListener(scrollListener)
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
            globalClass.log(TAG,"lastVisibleItem:${lastVisibleItem}")

            if (lastVisibleItem == offset - 1 && !isLoading) {
                if(offset < totalItem) {
                    offset = lastVisibleItem + 1
                    globalClass.log(TAG,"load more")
                    showProgressBar()
                    viewModel.getPeopleSearchList(paginationLimit,offset,searchKeyword)
                }
            }
        }
    }

    fun observeData() {

        viewModel.peopleSearchDetailRes.observe(viewLifecycleOwner) {

            val response = it

            hideProgressBar()
            val status = response.status
            val message = response.message
            if(status) {

                totalItem = response.totalRecord
//                if(totalItem > 0) {
//                    arrayList.clear()
//                }

                if(adapter == null || doSearch) {
                    doSearch = false
                    arrayList = response.data
                    if(arrayList.isNotEmpty()) {
                        setAdapter(arrayList)
                    }
                }
                else {
//                    arrayList = response.data
                    adapter?.addAll(response.data)
//                    if(arrayList.isNotEmpty()) {
//                        adapter?.addAll(arrayList)
//                    }
                }

                offset = arrayList.size
                globalClass.log(TAG,"offset: $offset")
                showHideView()
            }
            else {
                globalClass.log(TAG,message)
                globalClass.toastlong(message)
                showHideView()
            }
        }
    }

    private fun setAdapter(arrayList: ArrayList<PeopleList>) {

        val recyclerview = recyclerView
        recyclerview.layoutManager = GridLayoutManager(activity, 2)
        adapter = peopleSearchListAdapter(activity, arrayList)
        recyclerview.adapter = adapter
    }

//    override fun openPeopleDetail(pos: Int, model: PeopleListModel) {
//
//        val action = PeopleFragDirections.actionPeopleFragmentToPeopleDetailFrag(model.id)
//        findNavController().navigate(action)
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.people_search_optionmenu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        menu_search = menu.findItem(R.id.menu_search_people)
        searchView = menu_search?.actionView as SearchView
        searchView!!.queryHint = "Enter name"
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(s: String): Boolean {

                if (s.isNotEmpty()) {
                    offset = 0
                    searchKeyword = s
                    doSearch = true
                    viewModel.getPeopleSearchList(paginationLimit,offset,searchKeyword)
                }

                return true
            }

            override fun onQueryTextSubmit(s: String): Boolean {

                if (s.isNotEmpty()) {
                    offset = 0
                    searchKeyword = s
                    doSearch = true
                    viewModel.getPeopleSearchList(paginationLimit,offset,searchKeyword)
                } else {
                    globalClass.toastshort("Enter name")
                }
                return true
            }
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        menu_search!!.expandActionView()
    }

/*
    fun getSearchData(businessName: String) {

        viewModel.searchPeopleList(businessName).observe(
            viewLifecycleOwner,
            {
                arrayList.clear()
                arrayList = it as ArrayList<PeopleListModel>
                if (!arrayList.isEmpty()) {
                    setAdapter(arrayList)
                } else {
                    globalClass.toastshort("No data found")
                }
            })
    }
*/

    private fun showHideView() {

        if(arrayList.isNotEmpty()) {

            noDataLayout.visibility = View.GONE
            recyclerViewLayout.visibility = View.VISIBLE
        }
        else {

            recyclerViewLayout.visibility = View.GONE
            noDataLayout.visibility = View.VISIBLE
        }
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.GONE
        isLoading = false
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }
}