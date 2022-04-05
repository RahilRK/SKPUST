package com.rahilkarim.skpust.ui.PeopleFrag

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AbsListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rahilkarim.skpust.R
import com.rahilkarim.skpust.databinding.FragmentPeopleBinding
import com.rahilkarim.skpust.models.peopleList.PeopleList
import com.rahilkarim.skpust.network.Resource
import com.rahilkarim.skpust.ui.BusinessDetailFrag.peopleListAdapter
import com.rahilkarim.skpust.util.Constant.paginationLimit
import com.rahilkarim.skpust.util.GlobalClass
import com.rahilkarim.skpust.util.Repository
import com.rahilkarim.skpust.util.skpustApplication
import kotlinx.android.synthetic.main.fragment_people.*
import java.util.*
import kotlin.collections.ArrayList

class PeopleFrag : Fragment(R.layout.fragment_people), peopleListAdapter.peopleListAdapterOnClick {

    var TAG = "PeopleFrag"
    private lateinit var activity: Context

    lateinit var globalClass: GlobalClass
    lateinit var binding: FragmentPeopleBinding
    lateinit var repository: Repository
    lateinit var viewModel: PeopleFragVM

//    private var arrayList = arrayListOf<PeopleListModel>()

    var isLoading = false
    var offset = 0
    var totalItem = 0

    private var arrayList = arrayListOf<PeopleList>()
    var adapter: peopleListAdapter? = null;

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
        viewModel = ViewModelProvider(this, PeopleFragVMFactory(repository,globalClass))
            .get(PeopleFragVM::class.java)
    }

    fun setToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = resources.getString(R.string.people)
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
//                globalClass.log(TAG,"load more")
                    showProgressBar()
                    viewModel.getPersonList(paginationLimit,offset)
                }
            }
        }
    }

    fun observeData() {

        viewModel.peopleDetailRes.observe(viewLifecycleOwner) {

            val response = it

            hideProgressBar()
            val status = response.status
            val message = response.message
            if(status) {

                totalItem = response.totalRecord

                if(adapter == null) {
                    arrayList = response.data
                    setAdapter(arrayList)
                }
                else {
                    adapter?.addAll(response.data)
                }
                offset = arrayList.size
                globalClass.log(TAG,"offset: ${offset}")
            }
            else {
                globalClass.log(TAG,message)
                globalClass.toastlong(message)
            }
        }
    }

    private fun setAdapter(arrayList: ArrayList<PeopleList>) {

        val recyclerview = recyclerView
        recyclerview.layoutManager = GridLayoutManager(activity, 2)
        adapter = peopleListAdapter(activity, arrayList,this)
        recyclerview.adapter = adapter
    }

    override fun openPeopleDetail(pos: Int, model: PeopleListModel) {

        val action = PeopleFragDirections.actionPeopleFragmentToPeopleDetailFrag(model.id)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.people_optionmenu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.menu_open_search_people) {
            findNavController().navigate(R.id.action_peopleFragment_to_peopleSearchFrag)
        }
        return super.onOptionsItemSelected(item)
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

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.GONE
        isLoading = false
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }
}